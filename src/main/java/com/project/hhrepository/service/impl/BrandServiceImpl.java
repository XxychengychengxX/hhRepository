package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.entity.Brand;
import com.project.hhrepository.mapper.BrandMapper;
import com.project.hhrepository.service.IBrandService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 品牌 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
@CacheConfig(cacheNames = "com.project.hhrepository.service.impl.BrandServiceImpl")
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    /**
     * 获取所有brand的信息
     *
     * @return 装有所有brand信息的List集合
     */
    @Override
    @Cacheable(key = "'all:brand'")
    public List<Brand> getBrandList() {
        return list();
    }
}
