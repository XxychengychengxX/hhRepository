package com.project.hhrepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.dto.ProductPageListDto;
import com.project.hhrepository.entity.Product;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.page.Page;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IProductService extends IService<Product> {

    /**
     * 根据条件来查询满足条件的product对象
     *
     * @param page               页码信息参数
     * @param productPageListDto 封装前端分页查询请求参数的对象
     * @return Page对象
     */
    Page getProductListPage(Page page, ProductPageListDto productPageListDto);

    /**
     * 根据传入的信息添加商品
     *
     * @param product product对象
     * @param token jwt
     * @return 成功返回true
     */
    Boolean addProduct(Product product, String token);

    /**
     * 修改商品的上下架状态
     *
     * @param product product对象
     * @param token   jwt
     * @return 成功返回true
     */
    Boolean updateProductState(Product product, String token);

    /**
     * 根据商品id集合批量删除id
     * @param productIdList 传入的商品id集合
     * @return 成功返回true,反之返回false
     */
    Boolean deleteProductByIds(List<Integer> productIdList);

    /**
     * 根据商品id和传入的商品对象修改对应商品信息
     *
     * @param product 商品对象
     * @param token   jwt
     * @return 成功返回true, 失败返回false
     */
    Result updateProduct(Product product, String token);


}
