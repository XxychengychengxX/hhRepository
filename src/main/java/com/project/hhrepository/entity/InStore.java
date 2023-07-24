package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 入库单
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@TableName("in_store")
public class InStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ins_id", type = IdType.AUTO)
    private Integer insId;

    @TableField("store_id")
    private Integer storeId;

    @TableField("product_id")
    private Integer productId;

    @TableField("in_num")
    private Integer inNum;

    @TableField("create_by")
    private Integer createBy;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 0 否 1 是
     */
    @TableField("is_in")
    private String isIn;

    public Integer getInsId() {
        return insId;
    }

    public void setInsId(Integer insId) {
        this.insId = insId;
    }
    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Integer getInNum() {
        return inNum;
    }

    public void setInNum(Integer inNum) {
        this.inNum = inNum;
    }
    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public String getIsIn() {
        return isIn;
    }

    public void setIsIn(String isIn) {
        this.isIn = isIn;
    }

    @Override
    public String toString() {
        return "InStore{" +
            "insId=" + insId +
            ", storeId=" + storeId +
            ", productId=" + productId +
            ", inNum=" + inNum +
            ", createBy=" + createBy +
            ", createTime=" + createTime +
            ", isIn=" + isIn +
        "}";
    }
}
