package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.entity.OutStore;
import com.project.hhrepository.entity.Product;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.mapper.OutStoreMapper;
import com.project.hhrepository.mapper.ProductMapper;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IOutStoreService;
import com.project.hhrepository.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 出库单 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
@Transactional
public class OutStoreServiceImpl extends ServiceImpl<OutStoreMapper, OutStore> implements IOutStoreService {

    @Resource
    TokenUtils tokenUtils;

    @Resource
    ProductMapper productMapper;

    /**
     * 确认出库的服务方法
     *
     * @param outStore outStore方法
     * @param token jwt
     * @return Result对象
     */
    @Override
    public Result confirmOutStore(OutStore outStore, String token) {
        outStore.setIsOut(String.valueOf(1));
        boolean b = updateById(outStore);

        if (b) {
            int userId = tokenUtils.getCurrentUser(token).getUserId();
            Product product = productMapper.selectById(outStore.getProductId());
            product.setProductInvent(product.getProductInvent() - outStore.getOutNum());

            product.setUpdateTime(LocalDateTime.now());
            product.setUpdateBy(userId);

            int i = productMapper.updateById(product);
            if (i > 0)
                return Result.ok("出库单状态修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "出库单状态修改失败,请检查服务器后重试");
    }

    /**
     * 根据传入的信息分页查找outStore数据
     *
     * @param page     分页信息
     * @param outStore 要查找的信息
     * @return page对象
     */
    @Override
    public Page getOutStoreListPage(Page page, OutStore outStore) {
        int count = baseMapper.outStoreCount(outStore);

        List<OutStore> outStores = baseMapper.outStorePage(page, outStore);

        page.setTotalNum(count);
        page.setResultList(outStores);

        return page;
    }

    /**
     * 添加出库信息
     *
     * @param outStore 出库信息对象
     * @param token    jwt
     * @return 成功返回true, 反之返回false
     */
    @Override
    public Boolean addOutStore(OutStore outStore, String token) {

        outStore.setOutPrice(outStore.getSalePrice());

        outStore.setIsOut("0");

        return save(outStore);
    }
}
