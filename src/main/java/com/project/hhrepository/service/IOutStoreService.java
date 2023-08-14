package com.project.hhrepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.entity.OutStore;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.page.Page;

/**
 * <p>
 * 出库单 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IOutStoreService extends IService<OutStore> {

    /**
     * 添加出库信息
     *
     * @param outStore 出库信息对象
     * @param token    jwt
     * @return 成功返回true, 反之返回false
     */
    Boolean addOutStore(OutStore outStore, String token);

    /**
     * 根据传入的信息分页查找outStore数据
     *
     * @param page     分页信息
     * @param outStore 要查找的信息
     * @return page对象
     */
    Page getOutStoreListPage(Page page, OutStore outStore);

    /**
     * 确认出库的服务方法
     *
     * @param outStore outStore方法
     * @param token jwt
     * @return Result对象
     */
    Result confirmOutStore(OutStore outStore, String token);
}
