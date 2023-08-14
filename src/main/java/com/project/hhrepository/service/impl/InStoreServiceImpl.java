package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.entity.BuyList;
import com.project.hhrepository.entity.InStore;
import com.project.hhrepository.entity.Product;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.mapper.BuyListMapper;
import com.project.hhrepository.mapper.InStoreMapper;
import com.project.hhrepository.mapper.ProductMapper;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IInStoreService;
import com.project.hhrepository.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 入库单 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
public class InStoreServiceImpl extends ServiceImpl<InStoreMapper, InStore> implements IInStoreService {

    @Resource
    BuyListMapper buyListMapper;

    @Resource
    ProductMapper productMapper;

    @Resource
    TokenUtils tokenUtils;

    /**
     * 设置入库状态为已入库的业务方法
     *
     * @param inStore instore对象
     * @param token jwt
     * @return Result对象.
     */
    @Override
    public Result confirmInStore(InStore inStore, String token) {
        inStore.setIsIn(String.valueOf(1));
        boolean b = updateById(inStore);

        if (b) {
            int userId = tokenUtils.getCurrentUser(token).getUserId();

            Product product = productMapper.selectById(inStore.getProductId());
            product.setProductInvent(product.getProductInvent() + inStore.getInNum());
            product.setUpdateTime(LocalDateTime.now());
            product.setUpdateBy(userId);

            int i = productMapper.updateById(product);

            if (i > 0)
                return Result.ok("入库单状态修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库单状态修改失败,请检查服务器后重试");

    }

    /**
     * 获取store表的所有list
     *
     * @return 封装了store表所有的
     */
    @Override
    public Page getInstorePageList(Page page, InStore inStore) {
        int count = baseMapper.selectInStoreCount(inStore);

        List<InStore> inStores = baseMapper.selectInStorePage(page, inStore);

        page.setTotalNum(count);
        page.setResultList(inStores);
        return page;

    }

    /**
     * 添加真正的入库信息
     *
     * @param inStore 入库信息的封装对象
     * @param token   jwt
     * @param buyId   buyList的id
     * @return Result对象
     */
    @Override
    public Result addInStoreRecord(InStore inStore, String token, Integer buyId) {
        int userId = tokenUtils.getCurrentUser(token).getUserId();
        //一系列的设置
        inStore.setCreateBy(userId);
        inStore.setCreateTime(LocalDateTime.now());
        BuyList buyList = new BuyList();
        buyList.setBuyId(buyId);
        buyList.setIsIn(String.valueOf(1));
        int i = buyListMapper.updateById(buyList);
        if (i > 0) {
            boolean save = save(inStore);
            if (save)
                return Result.ok("入库信息添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库信息添加失败");
    }
}
