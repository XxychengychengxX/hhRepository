package com.project.hhrepository.controller;

import com.project.hhrepository.dto.ProductPageListDto;
import com.project.hhrepository.entity.*;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.*;
import com.project.hhrepository.utils.WarehouseConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:9988"})
@RequestMapping("/product")
@Slf4j
public class ProductController {

    //商品服务（本机服务）
    @Resource
    IProductService productService;
    //商店服务
    @Resource
    IStoreService storeService;
    //品牌服务
    @Resource
    IBrandService brandService;
    //供应商服务
    @Resource
    ISupplyService supplyService;
    //购买列表服务
    @Resource
    IBuyListService buyListService;

    //产品服务
    @Resource
    IProductTypeService productTypeService;
    @Resource
    IPlaceService placeService;
    @Resource
    IUnitService unitService;

    //接受添加商品时图片的上传位置的图片
    @Value("${file.upload-path}")
    private String uploadPath;



    /**
     * 获取所有store信息的接口
     *
     * @return Result对象
     */
    @GetMapping("/store-list")
    public Result getStoreList() {
        List<Store> list = storeService.getStoreList();
        if (list.isEmpty())
            return Result.err(Result.CODE_ERR_BUSINESS, "查询仓库列表失败,请重试!");
        return Result.ok(list);
    }

    /**
     * 获取所有brand信息的接口
     *
     * @return Result对象
     */
    @GetMapping("/brand-list")
    public Result getBrandList() {
        List<Brand> list = brandService.getBrandList();
        if (list.isEmpty())
            return Result.err(Result.CODE_ERR_BUSINESS, "查询仓库列表失败,请重试!");
        return Result.ok(list);
    }

    @GetMapping("/product-page-list")
    public Result productListPage(Page page, ProductPageListDto productPageListDto) {
        page = productService.getProductListPage(page, productPageListDto);
        if (page != null)
            return Result.ok(page);
        return Result.err(Result.CODE_ERR_BUSINESS, "查询商品列表出错,请稍后再试");
    }

    @GetMapping("/category-tree")
    public Result getProductTypeList() {
        List<ProductType> productTypeList = productTypeService.getProductTypeListTree();
        return Result.ok(productTypeList);
    }

    @GetMapping("/supply-list")
    public Result getSupplyList() {
        List<Supply> supplyList = supplyService.getSupplyList();
        return Result.ok(supplyList);
    }

    @GetMapping("/place-list")
    public Result getPlaceList() {

        List<Place> placeList = placeService.getPlaceList();
        return Result.ok(placeList);
    }

    @GetMapping("/unit-list")
    public Result getUnitList() {
        List<Unit> list = unitService.list();
        return Result.ok(list);
    }

    /**
     * 添加商品时上传图片的接口
     *
     * @param file 封装了请求参数名叫file上传的图片
     * @return Result对象
     */
    @PostMapping("/img-upload")
    @CrossOrigin
    public Result uploadImg(@RequestParam("file") MultipartFile file) {
        /*
         * 拿到图片上传的目录路径的file对象 ---这里是 classpath:/static/img/upload
         * 因为图片上传的目录是类路径(resource下的/classes下的路径，就是带有前缀classpath
         * 所以这里不能直接new一个file对象，；
         * 而是使用ResourceUtils的方法getFile来解析类路径并拿到类路径的file对象
         *
         * 使用file.transferTo(上传文件最终保存到的磁盘文件的file对象--包括文件及后缀名)
         * */
        try {
            File uploadDir = new File(uploadPath);
            //拿到上传的图片名称
            String fileName = file.getOriginalFilename();
            //组合成最终的文件(在绝对路径下的文件)
            String uploadAbsolutDirFile = uploadDir + "\\" + fileName;

            //调用transfer方法保存到磁盘
            file.transferTo(new File(uploadAbsolutDirFile));

            return Result.ok("图片上传成功");
        } catch (IOException e) {
            //记录错误日志
            log.error(e.getMessage());
            return Result.err(Result.CODE_ERR_BUSINESS, "图片上传失败");
        }

    }


    @PostMapping("/product-add")
    public Result addProduct(@RequestBody Product product,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        Boolean aBoolean = productService.addProduct(product, token);
        if (aBoolean)
            return Result.ok("产品添加成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "商品添加失败,请检查服务器后重试");
    }

    /**
     * 修改商品的上下架状态
     *
     * @param product product对象
     * @param token   jwt
     * @return Result对象
     */
    @PutMapping("/state-change")
    public Result updateProductState(@RequestBody Product product,
                                     @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Boolean aBoolean = productService.updateProductState(product, token);

        if (aBoolean)
            return Result.ok("上下架状态更新成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "上下架状态更新失败,经检查服务器后重试");

    }


    @DeleteMapping("/product-list-delete")
    public Result deleteProductList(@RequestBody List<Integer> productIdList) {
        Boolean aBoolean = productService.deleteProductByIds(productIdList);
        if (aBoolean)
            return Result.ok("商品删除成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "商品删除失败,经检查服务器后重试");
    }

    @DeleteMapping("/product-delete/{productId}")
    public Result deleteProductById(@PathVariable Integer productId) {
        Boolean aBoolean = productService.deleteProductByIds(List.of(productId));
        if (aBoolean)
            return Result.ok("商品删除成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "商品删除失败,经检查服务器后重试");
    }

    @PutMapping("/product-update")
    public Result updateProduct(@RequestBody Product product,
                                @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        return productService.updateProduct(product, token);
    }
}
