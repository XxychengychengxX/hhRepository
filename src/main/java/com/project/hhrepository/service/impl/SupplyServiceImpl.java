package com.project.hhrepository.service.impl;

import com.project.hhrepository.entity.Supply;
import com.project.hhrepository.mapper.SupplyMapper;
import com.project.hhrepository.service.ISupplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 供货商 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
@CacheConfig(cacheNames = "com.project.hhrepository.service.impl.SupplyServiceImpl")
public class SupplyServiceImpl extends ServiceImpl<SupplyMapper, Supply> implements ISupplyService {

    /**
     * 获取所有供应商信息
     *
     * @return 所有供应商信息的list集合
     */
    @Override
    @Cacheable(key = "'all:supply'")
    public List<Supply> getSupplyList() {

        return query().eq("is_delete", 0).list();
    }
}
