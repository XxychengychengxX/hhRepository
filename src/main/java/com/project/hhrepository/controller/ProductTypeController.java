package com.project.hhrepository.controller;

import com.project.hhrepository.entity.ProductType;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.service.IProductTypeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品分类表 前端控制器
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:9988"})
@RequestMapping("/productCategory")
public class ProductTypeController {

    @Resource
    IProductTypeService productTypeService;

    @RequestMapping("/product-type-tree")
    private Result productCategoryTree() {
        //执行业务
        List<ProductType> productTypeListTree = productTypeService.getProductTypeListTree();
        if (!productTypeListTree.isEmpty())
            return Result.ok(productTypeListTree);
        else
            return Result.err(Result.CODE_ERR_BUSINESS, "查询商品分类树失败,请检查服务器后重试");
    }


    @GetMapping("/verify-type-code")
    private Result verifyProductTypeByCode(@RequestParam String typeCode) {
        Boolean aBoolean = productTypeService.verifyProductTypeByNameOrCode(typeCode, null);
        //这里无论存不存在,都返回Result.ok,只是里面存的是true或者false
        return Result.ok(!aBoolean);
    }


    @PostMapping("/type-add")
    public Result addProductType(@RequestBody ProductType productType) {
        return productTypeService.addProductType(productType);
    }

    @DeleteMapping("/type-delete/{typeId}")
    public Result deleteProductTypeById(@PathVariable Integer typeId) {
        Boolean aBoolean = productTypeService.deleteProductTypeById(typeId);
        if (aBoolean)
            return Result.ok("分类信息删除成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "分类信息删除失败,请检查服务器后重试!");
    }


    @PutMapping("/type-update")
    public Result updateProductType(@RequestBody ProductType productType) {
        return productTypeService.updateProductType(productType);
    }
}
