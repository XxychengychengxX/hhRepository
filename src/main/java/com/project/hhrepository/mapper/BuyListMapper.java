package com.project.hhrepository.mapper;

import com.project.hhrepository.entity.BuyList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.hhrepository.entity.myentity.Purchase;
import com.project.hhrepository.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 采购单 Mapper 接口
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Mapper
public interface BuyListMapper extends BaseMapper<BuyList> {
    //查询采购单总行数的方法
    public int selectPurchaseCount(Purchase purchase);

    //分页查询采购单的方法
    public List<Purchase> selectPurchasePage(@Param("page") Page page, @Param("purchase") Purchase purchase);
}
