package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.entity.ProductType;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.mapper.ProductTypeMapper;
import com.project.hhrepository.service.IProductTypeService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
@CacheConfig(cacheNames = "com.project.hhrepository.service.impl.ProductTypeServiceImpl")
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    /**
     * 根据商品类型编码来获取商品
     *
     * @param typeCode 商品类型编码
     * @param typeName 商品名称
     * @return 成功返回true, 反之返回false
     */
    @CacheEvict(key = "'all:productTypeTree'")
    @Override
    public Boolean verifyProductTypeByNameOrCode(String typeCode, String typeName) {
        ProductType productType =
                query().eq(typeCode != null && !typeCode.equals(""), "type_code", typeCode).or().eq(typeName != null && !typeName.equals(""), "type_name", typeName).one();
        return productType != null;
    }

    /**
     * 根据商品类型参数添加商品分类信息
     *
     * @param productType 商品分类参数
     * @return 成功返回true
     */
    @Override
    @CacheEvict(key = "'all:productTypeTree'")
    public Result addProductType(ProductType productType) {
        Boolean aBoolean = verifyProductTypeByNameOrCode(productType.getTypeCode(), productType.getTypeName());
        if (aBoolean)
            return Result.err(Result.CODE_ERR_BUSINESS, "商品名称已存在,添加失败");
        boolean save = save(productType);
        if (save)
            return Result.ok("商品分类添加成功");
        else
            return Result.err(Result.CODE_ERR_BUSINESS, "商品分类添加失败,请重试");
    }

    /**
     * 更新商品分类信息
     *
     * @param productType 商品分类类别
     * @return 成功返回true, 失败返回false
     */
    @CacheEvict(key = "'all:productTypeTree'")
    @Override
    public Result updateProductType(ProductType productType) {
        Boolean aBoolean = verifyProductTypeByNameOrCode(null, productType.getTypeName());
        if (aBoolean)
            return Result.err(Result.CODE_ERR_BUSINESS, "商品名称已存在,请重试");
        boolean b = updateById(productType);
        if (!b)
            return Result.err(Result.CODE_ERR_BUSINESS, "商品分类更新失败,请检查服务器后重试!");
        return Result.ok("商品分类信息修改成功");
        //如果修改后的商品
    }

    /**
     * 根据商品分类id删除所有自身id和父类id为typeId的分类值
     *
     * @param typeId 商品分类id
     * @return 成功返回true 反之false
     */
    @Override
    @CacheEvict(key = "'all:productTypeTree'")
    public Boolean deleteProductTypeById(Integer typeId) {
        QueryWrapper<ProductType> productTypeQueryWrapper = new QueryWrapper<>();
        productTypeQueryWrapper.eq("type_id", typeId).or().eq("parent_id", typeId);
        return remove(productTypeQueryWrapper);
    }

    /**
     * 查询所有商品分类的业务方法
     *
     * @return 所有商品分类的List集合
     */
    @Override
    @Cacheable(key = "'all:productTypeTree'")
    public List<ProductType> getProductTypeListTree() {
        List<ProductType> list = baseMapper.selectList(null);
        return allProductTypeTree(list, 0);
    }

    private List<ProductType> allProductTypeTree(List<ProductType> list, Integer pid) {

        ArrayList<ProductType> firstLevelTypeList = new ArrayList<>();
        //拿到所有一级分类
        list.forEach(productType -> {
            if (productType.getParentId().equals(pid))
                firstLevelTypeList.add(productType);
        });

        //查询所有一级分类下的二级分类，或者更高级（依次递归）
        firstLevelTypeList.forEach(productType -> {
            List<ProductType> secondLevelType = allProductTypeTree(list, productType.getTypeId());
            productType.setChildProductCategory(secondLevelType);
        });
        return firstLevelTypeList;
    }

}
