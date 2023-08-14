package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.dto.ProductPageListDto;
import com.project.hhrepository.entity.Product;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.mapper.*;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IProductService;
import com.project.hhrepository.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
@Transactional
@CacheConfig(cacheNames = "com.project.hhrepository.service.impl.ProductServiceImpl")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    //mapper注入
    @Resource
    BrandMapper brandMapper;
    @Resource
    ProductTypeMapper productTypeMapper;
    @Resource
    SupplyMapper supplyMapper;
    @Resource
    PlaceMapper placeMapper;

    @Value("${file.access-path}")
    String fileAccessPath;

    @Resource
    TokenUtils tokenUtils;

    /**
     * 根据商品id和传入的商品对象修改对应商品信息
     *
     * @param product 商品对象
     * @param token   jwt
     * @return 成功返回true, 失败返回false
     */
    @Override
    public Result updateProduct(Product product, String token) {
        int userId = tokenUtils.getCurrentUser(token).getUserId();
        product.setUpdateBy(userId);
        product.setUpdateTime(LocalDateTime.now());
        String productNum = product.getProductNum();
        Product product1 = query().eq("product_num", productNum).one();
        //如果表中存在对应的商品编号并且查出来的商品id不是欲修改的id
        if (product1 != null && Objects.equals(product1.getProductId(), product.getProductId())) {
            String imgs = product.getImgs();
            if (!imgs.startsWith("/img/upload"))
                product.setImgs(fileAccessPath + imgs);
            boolean b = updateById(product);
            if (b)
                return Result.ok("对应商品信息修改成功");
            else
                return Result.err(Result.CODE_ERR_BUSINESS, "商品信息修改失败,请检查服务器后重试");

        }
        return Result.err(Result.CODE_ERR_BUSINESS, "修改后的商品编号号已经存在,请修改后重试");
    }

    /**
     * 根据商品id集合批量删除id
     *
     * @param productIdList 传入的商品id集合
     * @return 成功返回true, 反之返回false
     */
    @Override
    public Boolean deleteProductByIds(List<Integer> productIdList) {
        return removeBatchByIds(productIdList);
    }

    /**
     * 修改商品的上下架状态
     *
     * @param product product对象
     * @param token   jwt
     * @return 成功返回true
     */
    @Override
    public Boolean updateProductState(Product product, String token) {
        int userId = tokenUtils.getCurrentUser(token).getUserId();
        product.setUpdateBy(userId);
        product.setUpdateTime(LocalDateTime.now());
        return updateById(product);
    }

    /**
     * 根据传入的信息添加商品
     *
     * @param product product对象
     * @param token   jwt
     * @return 成功返回true
     */
    @Override
    public Boolean addProduct(Product product, String token) {

        int userId = tokenUtils.getCurrentUser(token).getUserId();

        String productName = product.getProductName();
        String productNum = product.getProductNum();
        product.setImgs(fileAccessPath + "/" + product.getImgs());
        //设置一系列更新信息
        product.setCreateBy(userId);
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateBy(userId);
        product.setUpdateTime(LocalDateTime.now());
        //别忘记添加上下架状态
        product.setUpDownState("0");



        QueryChainWrapper<Product> query = query();

        Product one = query.eq("product_num", productNum).or().eq("product_name", productName).one();
        if (one != null)
            return false;
        else {
            return save(product);
        }
    }

    /**
     * 根据条件来查询满足条件的product对象
     *
     * @param page               页码信息参数
     * @param productPageListDto 封装前端分页查询请求参数的对象
     * @return Page对象
     */
    @Override
    public Page getProductListPage(Page page, ProductPageListDto productPageListDto) {
    /*    //如果不为空
        String brandName = productPageListDto.getBrandName();//品牌名
        String productName = productPageListDto.getProductName();//商品名
        String typeName = productPageListDto.getTypeName();//类型名
        String supplyName = productPageListDto.getSupplyName();//供应商名
        String placeName = productPageListDto.getPlaceName();//地点名
        String upDownState = productPageListDto.getUpDownState();//上下架状态
        String isOverDate = productPageListDto.getIsOverDate();//是否过期

        //wrapper设置
        QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        QueryWrapper<ProductType> productTypeQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Supply> supplyQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Place> placeQueryWrapper = new QueryWrapper<>();


        productQueryWrapper.like(productName != null && !productName.equals(""), "product_name", productName);
        productQueryWrapper.like(upDownState != null && !upDownState.equals(""), "updown_state", upDownState);
        productQueryWrapper.lt(isOverDate.equals("1"), "supp_date",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        productQueryWrapper.gt(isOverDate.equals("0"), "supp_date",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        brandQueryWrapper.like(brandName != null && !brandName.equals(""), "brand_name", brandName);

        productTypeQueryWrapper.like(typeName != null && !typeName.equals(""), "type_name", typeName);
        supplyQueryWrapper.like(supplyName != null && !supplyName.equals(""), "supply_name", supplyName);
        placeQueryWrapper.like(supplyName != null && !supplyName.equals(""), "place_name", placeName);


        List<Integer> brandId = brandMapper.selectList(brandQueryWrapper).stream().map(Brand::getBrandId).toList();
        List<Integer> productTypeId =
                productTypeMapper.selectList(productTypeQueryWrapper).stream().map(ProductType::getTypeId).toList();
        List<Integer> supplyIds =
                supplyMapper.selectList(supplyQueryWrapper).stream().map(Supply::getSupplyId).toList();
        List<Integer> placeIds = placeMapper.selectList(placeQueryWrapper).stream().map(Place::getPlaceId).toList();

        productQueryWrapper.

        //要求查找没有被删除的（删除状态为0）

        //传入前端传来的user信息来换行
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Product> userInfoPage =
                new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page.getPageNum(), page.getPageSize());
       /*
        page方法是查询出所有满足条件的对象并且封装了
        查询结果，分页信息等对象到userInfoPage中
        userInfoPage类型是com.baomidou.mybatisplus.extension.plugins.pagination.Page
        如下：
        private static final long serialVersionUID = 8545996863226528798L;
        protected List<T> records; ------查询结果
        protected long total;  -----总数
        protected long size;  ------当前包含的数目
        protected long current; --------当前页
        protected List<OrderItem> orders;
        protected boolean optimizeCountSql;
        protected boolean searchCount;
        protected boolean optimizeJoinOfCountSql;
        protected Long maxLimit;
        protected String countId;
        /*
        page(userInfoPage, userInfoWrapper);

        //分页查询出来的满足条件的List集合


        //设置参数信息（响应给前端）
        page.setResultList(records);
        page.setTotalNum((int) userInfoPage.getTotal());

        return page;*/
        try {
            Integer productListCount = baseMapper.getProductListCount(productPageListDto);
            List<ProductPageListDto> productList = baseMapper.getProductList(page, productPageListDto);
            if (productListCount != null && !productList.isEmpty()) {
                page.setTotalNum(productListCount);
                page.setResultList(productList);
            }
                return page;

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }


        return null;
    }
}
