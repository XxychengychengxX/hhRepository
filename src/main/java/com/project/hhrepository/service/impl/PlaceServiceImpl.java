package com.project.hhrepository.service.impl;

import com.project.hhrepository.entity.Place;
import com.project.hhrepository.mapper.PlaceMapper;
import com.project.hhrepository.service.IPlaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 产地 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
@CacheConfig(cacheNames = "com.project.hhrepository.service.impl.PlaceServiceImpl")
public class PlaceServiceImpl extends ServiceImpl<PlaceMapper, Place> implements IPlaceService {
    /**
     * 获取所有place的信息
     *
     * @return 装有所有place信息的list集合
     */
    @Override
    @Cacheable(key = "'all:place'")
    public List<Place> getPlaceList() {
        return query().eq("is_delete", 0).list();
    }
}
