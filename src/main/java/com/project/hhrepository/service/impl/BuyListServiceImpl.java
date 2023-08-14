package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.entity.BuyList;
import com.project.hhrepository.entity.Product;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.myentity.Purchase;
import com.project.hhrepository.mapper.BuyListMapper;
import com.project.hhrepository.mapper.ProductMapper;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IBuyListService;
import com.project.hhrepository.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 采购单 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
public class BuyListServiceImpl extends ServiceImpl<BuyListMapper, BuyList> implements IBuyListService {

    @Resource
    ProductMapper productMapper;

    @Resource
    TokenUtils tokenUtils;

    /**
     * 添加采购单并实时更改
     *
     * @param buyList 购买订单列表
     * @param token   jwt
     * @return 成功返回true
     */
    @Override
    public Result addBuyList(BuyList buyList, String token) {

        int userId = tokenUtils.getCurrentUser(token).getUserId();

        //补充字段   --给factbuynum字段赋值为预采购数量
        Integer buyNum = buyList.getBuyNum();
        /*//查找出来再搞一次
        Integer productId = buyList.getProductId();
        Product product = productMapper.selectById(productId);

        Integer productInvent = product.getProductInvent();
        int i = productInvent - buyNum;
        if (i < 0)
            i = 0;

        product.setProductInvent(i);
        product.setUpdateTime(LocalDateTime.now());
        product.setUpdateBy(userId);


        buyList.setFactBuyNum(productInvent - i);*/
        buyList.setFactBuyNum(buyNum);
        buyList.setBuyTime(LocalDateTime.now());
        //采购完成但是未入库
        buyList.setIsIn("0");

        /*int i1 = productMapper.updateById(product);*/

        boolean save = save(buyList);

        if (save)
            return Result.ok("采购对应商品成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "采购对应商品失败,请检查服务器后重试!");


    }

    /**
     * 根据传入的buy_id修改对应的buylist表项
     *
     * @param buyList buyList对象,包含buy_Id
     * @return 成功返回true反之false
     */
    @Override
    public Boolean updateBuyListById(BuyList buyList) {

        return updateById(buyList);
    }

    /**
     * 根据传入的buyId来删除buyList表单数据
     *
     * @param buyId buyId
     * @return 成功返回true
     */
    @Override
    public Boolean deleteBuyListById(Integer buyId) {
        return removeById(buyId);
    }

    /**
     * 查询所有buyList
     *
     * @param page     page对象
     * @param purchase 购买
     * @return 装有所有满足条件的purchase对象
     */
    @Override
    public Page getPurchasePageList(Page page, Purchase purchase) {
        int count = baseMapper.selectPurchaseCount(purchase);

        List<Purchase> purchases = baseMapper.selectPurchasePage(page, purchase);

        page.setResultList(purchases);

        page.setTotalNum(count);

        return page;
    }
}
