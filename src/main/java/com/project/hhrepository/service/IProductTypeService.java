package com.project.hhrepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.entity.ProductType;
import com.project.hhrepository.entity.Result;

import java.util.List;

/**
 * <p>
 * 商品分类表 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IProductTypeService extends IService<ProductType> {

    /**
     * 查询所有商品分类的业务方法
     *
     * @return 所有商品分类的List集合
     */
    List<ProductType> getProductTypeListTree();

    /**
     * 根据商品类型编码来获取商品
     *
     * @param typeCode 商品类型编码
     * @param typeName 商品名称
     *
     * @return 成功返回true, 反之返回false
     */
    Boolean verifyProductTypeByNameOrCode(String typeCode, String typeName);

    /**
     * 根据商品类型参数添加商品分类信息
     *
     * @param productType 商品分类参数
     * @return 成功返回true
     */
    Result addProductType(ProductType productType);

    /**
     * 根据商品分类id删除所有自身id和父类id为typeId的分类值
     * @param typeId 商品分类id
     * @return 成功返回true 反之false
     */
    Boolean deleteProductTypeById(Integer typeId);

    /**
     * 更新商品分类信息
     *
     * @param productType 商品分类类别
     * @return 成功返回true, 失败返回false
     */
    Result updateProductType(ProductType productType);
}
