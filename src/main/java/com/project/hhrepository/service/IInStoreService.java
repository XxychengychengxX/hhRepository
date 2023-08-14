package com.project.hhrepository.service;

import com.project.hhrepository.entity.InStore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.page.Page;

/**
 * <p>
 * 入库单 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */

public interface IInStoreService extends IService<InStore> {
    /**
     * 获取store表的所有list
     *
     * @return 封装了store表所有的
     */
    Page getInstorePageList(Page page, InStore inStore);


    /**
     * 添加真正的入库信息
     *
     * @param inStore 入库信息的封装对象
     * @param token   jwt
     * @param buyId buyList的id
     * @return Result对象
     */
    Result addInStoreRecord(InStore inStore, String token, Integer buyId);

    /**
     * 设置入库状态为已入库的业务方法
     *
     * @param inStore instore对象
     * @param token jwt
     * @return Result对象.
     */
    Result confirmInStore(InStore inStore, String token);


}
