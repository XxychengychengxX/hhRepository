package com.project.hhrepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.Store;
import com.project.hhrepository.entity.myentity.Statistics;
import com.project.hhrepository.page.Page;

import java.util.List;

/**
 * <p>
 * 仓库表 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IStoreService extends IService<Store> {

    /**
     * 获取所有store信息
     *
     * @return 封装了所有store的list集合
     */
    List<Store> getStoreList();

    List<Statistics> getStoreInvents();

    /**
     * 查询仓库列表并分页
     * @param page page分页条件
     * @param store store对象
     * @return 包含有查询结果的page对象
     */
    Page getStorePageList(Page page, Store store);

    /**
     * 根据仓库名称查询是否重复
     * @param storeNum 仓库名称
     * @return 重复返回true,反之就是false
     */
    Boolean checkStoreNum(String storeNum);

    /**
     * 根据传入的store对象更新store表
     * @param store store对象
     * @return  成功返回true
     */
    boolean updateStore(Store store);

    /**
     * 添加store
     * @param store store对象
     * @return 成功返回true
     */
    boolean addStore(Store store);

    /**
     * 根据仓库id删除仓库
     * @param storeId 仓库id
     * @return  result对象
     */
    Result deleteStore(Integer storeId);
}
