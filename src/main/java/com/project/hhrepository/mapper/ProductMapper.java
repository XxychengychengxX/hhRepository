package com.project.hhrepository.mapper;

import com.project.hhrepository.dto.ProductPageListDto;
import com.project.hhrepository.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.hhrepository.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    List<ProductPageListDto> getProductList(@Param("page") Page page,
                                            @Param("product") ProductPageListDto productPageListDto);

    Integer getProductListCount(@Param("product") ProductPageListDto productPageListDto);
}
