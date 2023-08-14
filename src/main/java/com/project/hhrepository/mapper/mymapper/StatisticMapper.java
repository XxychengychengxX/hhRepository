/**
 * @author Valar Morghulis
 * @Date 2023/8/9
 */
package com.project.hhrepository.mapper.mymapper;

import com.project.hhrepository.entity.myentity.Statistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticMapper {

    //统计各个仓库商品库存数量的方法
    List<Statistics> statisticsStoreInvent();

}
