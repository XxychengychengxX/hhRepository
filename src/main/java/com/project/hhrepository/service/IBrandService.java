package com.project.hhrepository.service;

import com.project.hhrepository.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IBrandService extends IService<Brand> {

    /**
     * 获取所有brand的信息
     * @return  装有所有brand信息的List集合
     */
    List<Brand> getBrandList();

}
