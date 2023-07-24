package com.project.hhrepository.service.impl;

import com.project.hhrepository.entity.Product;
import com.project.hhrepository.mapper.ProductMapper;
import com.project.hhrepository.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
