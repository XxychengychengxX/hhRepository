package com.project.hhrepository.mapper;

import com.project.hhrepository.entity.OutStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.hhrepository.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 出库单 Mapper 接口
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Mapper
public interface OutStoreMapper extends BaseMapper<OutStore> {
    //查询出库单总行数的方法
    int outStoreCount(OutStore outStore);

    //分页查询出库单的方法
    List<OutStore> outStorePage(@Param("page") Page page, @Param("outStore") OutStore outStore);
}
