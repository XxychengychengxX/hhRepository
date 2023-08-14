package com.project.hhrepository.service;

import com.project.hhrepository.entity.BuyList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.myentity.Purchase;
import com.project.hhrepository.page.Page;

/**
 * <p>
 * 采购单 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IBuyListService extends IService<BuyList> {

    /**
     * 添加采购单
     *
     * @param buyList 购买订单列表
     * @param token   jwt
     * @return result对象
     */
    Result addBuyList(BuyList buyList, String token);

    /**
     * 查询所有buyList
     *
     * @param page     page对象
     * @param purchase 购买
     * @return 装有所有满足条件的purchase对象
     */
    Page getPurchasePageList(Page page, Purchase purchase);

    /**
     * 根据传入的buyId来删除buyList表单数据
     * @param buyId buyId
     * @return 成功返回true
     */
    Boolean deleteBuyListById(Integer buyId);

    /**
     * 根据传入的buy_id修改对应的buylist表项
     * @param buyList buyList对象,包含buy_Id
     * @return 成功返回true反之false
     */
    Boolean updateBuyListById(BuyList buyList);
}
