package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 规格单位表
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@TableName("unit")
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "unit_id", type = IdType.AUTO)
    private Integer unitId;

    @TableField("unit_name")
    private String unitName;

    @TableField("unit_desc")
    private String unitDesc;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
    }

    @Override
    public String toString() {
        return "Unit{" +
            "unitId=" + unitId +
            ", unitName=" + unitName +
            ", unitDesc=" + unitDesc +
        "}";
    }
}
