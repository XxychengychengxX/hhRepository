package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.entity.*;
import com.project.hhrepository.entity.myentity.Statistics;
import com.project.hhrepository.mapper.*;
import com.project.hhrepository.mapper.mymapper.StatisticMapper;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IStoreService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
@CacheConfig(cacheNames = "com.project.hhrepository.service.impl.StoreServiceImpl")
@Transactional
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements IStoreService {

    //注入最终的Statistic查询对象
    @Resource
    StatisticMapper statisticMapper;

    @Resource
    ProductMapper productMapper;
    @Resource
    BuyListMapper buyListMapper;
    @Resource
    InStoreMapper inStoreMapper;

    @Resource
    OutStoreMapper outStoreMapper;


    /**
     * 获取所有store信息
     *
     * @return 封装了所有store的list集合
     */
    @Override
    @Cacheable(key = "'all:store'")
    public List<Store> getStoreList() {
        return list();
    }

    /**
     * 添加store
     *
     * @param store store对象
     * @return 成功返回true
     */
    @CacheEvict(key = "'all:store'")
    @Override
    public boolean addStore(Store store) {
        return save(store);
    }

    /**
     * 根据传入的store对象更新store表
     *
     * @param store store对象
     * @return 成功返回true
     */
    @CacheEvict(key = "'all:store'")
    @Override
    public boolean updateStore(Store store) {

        return updateById(store);
    }

    /**
     * 根据仓库id删除仓库
     *
     * @param storeId 仓库id
     * @return result对象
     */
    @Override
    @CacheEvict(key = "'all:store'")
    public Result deleteStore(Integer storeId) {
        QueryWrapper<InStore> inStoreQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        QueryWrapper<OutStore> outStoreQueryWrapper = new QueryWrapper<>();
        QueryWrapper<BuyList> buyListQueryWrapper = new QueryWrapper<>();

        inStoreQueryWrapper.eq("store_id", storeId);
        productQueryWrapper.eq("store_id", storeId);
        outStoreQueryWrapper.eq("store_id", storeId);
        buyListQueryWrapper.eq("store_id", storeId);

        try {
            inStoreMapper.delete(inStoreQueryWrapper);
            outStoreMapper.delete(outStoreQueryWrapper);
            buyListMapper.delete(buyListQueryWrapper);
            productMapper.delete(productQueryWrapper);
        } catch (Exception e) {
            return Result.err(Result.CODE_ERR_BUSINESS,"仓库删除失败,请检查其余关联并重试");
        }

        int i = baseMapper.deleteById(storeId);
        if (i>0)
            return Result.ok("仓库删除成功");
        return Result.err(Result.CODE_ERR_BUSINESS,"仓库删除失败,请检查服务器后重试");

    }

    @Override
    public List<Statistics> getStoreInvents() {

        return statisticMapper.statisticsStoreInvent();
    }

    /**
     * 根据仓库名称查询是否重复
     *
     * @param storeNum 仓库名称
     * @return 重复返回true, 反之就是false
     */
    @Override
    public Boolean checkStoreNum(String storeNum) {
        Store store = query()
                .eq("store_num", storeNum)
                .one();
        return store != null;
    }

    /**
     * 查询仓库列表并分页
     *
     * @param page  page分页条件
     * @param store store对象
     * @return 包含有查询结果的page对象 /store/store-page-list? storeName=& storeAddress=& concat=& phone=& pageSize=5& pageNum=1&
     * totalNum=0&_t=1691898155535
     */
    @Override
    public Page getStorePageList(Page page, Store store) {
        String storeName = store.getStoreName();
        String storeAddress = store.getStoreAddress();
        String concat = store.getConcat();
        String phone = store.getPhone();

        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Store> storePage =
                new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();

        storePage.setCurrent(page.getPageNum());
        storePage.setMaxLimit(Long.valueOf(page.getPageSize()));

        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Store> resultPage = query()
                .like(storeName != null && !storeName.equals(""), "store_name", storeName)
                .like(storeAddress != null && !storeAddress.equals(""), "store_address", storeAddress)
                .like(concat != null && !concat.equals(""), "concat", concat)
                .like(phone != null && !phone.equals(""), "phone", phone)
                .page(storePage);

        page.setResultList(resultPage.getRecords());
        page.setTotalNum((int) resultPage.getTotal());
        return page;
    }
}
