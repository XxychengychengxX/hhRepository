package com.project.hhrepository.service;

import com.project.hhrepository.entity.Place;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产地 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IPlaceService extends IService<Place> {

    /**
     * 获取所有place的信息
     * @return 装有所有place信息的list集合
     */
    List<Place> getPlaceList();
}
