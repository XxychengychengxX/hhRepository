package com.project.hhrepository.mapper;

import com.project.hhrepository.entity.InStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.hhrepository.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 入库单 Mapper 接口
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Mapper
public interface InStoreMapper extends BaseMapper<InStore> {
    //查询入库单总行数的方法
    public int selectInStoreCount(InStore inStore);

    //分页查询入库单的方法
    public List<InStore> selectInStorePage(@Param("page") Page page, @Param("inStore") InStore inStore);
}
