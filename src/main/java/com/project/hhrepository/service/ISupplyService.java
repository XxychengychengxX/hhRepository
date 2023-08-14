package com.project.hhrepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.entity.Supply;

import java.util.List;

/**
 * <p>
 * 供货商 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface ISupplyService extends IService<Supply> {

    /**
     * 获取所有供应商信息
     *
     * @return 所有供应商信息的list集合
     */
    List<Supply> getSupplyList();
}
