/**
 * @author Valar Morghulis
 * @Date 2023/7/29
 */
package com.project.hhrepository.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 用于配置mybatis自动填充
 */
//@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("create_time", LocalDateTime.now(), metaObject);
        this.setFieldValByName("update_time", LocalDateTime.now(), metaObject);
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("update_time", LocalDateTime.now(), metaObject);
    }
}
