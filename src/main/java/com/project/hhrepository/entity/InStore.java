package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
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
    //-----------------追加的属性--------------------
    @TableField(exist = false)
    private String productName;//商品名称
    @TableField(exist = false)
    private String startTime;//起始时间
    @TableField(exist = false)
    private String endTime;//结束时间
    @TableField(exist = false)
    private String storeName;//仓库名称
    @TableField(exist = false)
    private String userCode;//创建入库单的用户的名称
    @TableField(exist = false)
    private BigDecimal inPrice;//商品入库价格

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
    }

    @Override
    public String toString() {
        return "InStore{" + "insId=" + insId + ", storeId=" + storeId + ", productId=" + productId + ", inNum=" + inNum + ", createBy=" + createBy + ", createTime=" + createTime + ", isIn=" + isIn + "}";
    }
}
