package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 品牌
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@TableName("brand")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "brand_id", type = IdType.AUTO)
    private Integer brandId;

    @TableField("brand_name")
    private String brandName;

    @TableField("brand_leter")
    private String brandLeter;

    @TableField("brand_desc")
    private String brandDesc;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public String getBrandLeter() {
        return brandLeter;
    }

    public void setBrandLeter(String brandLeter) {
        this.brandLeter = brandLeter;
    }
    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    @Override
    public String toString() {
        return "Brand{" +
            "brandId=" + brandId +
            ", brandName=" + brandName +
            ", brandLeter=" + brandLeter +
            ", brandDesc=" + brandDesc +
        "}";
    }
}
