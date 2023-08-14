package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 出库单
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@TableName("out_store")
public class OutStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "outs_id", type = IdType.AUTO)
    private Integer outsId;
    @TableField("product_id")
    private Integer productId;
    @TableField("store_id")
    private Integer storeId;
    @TableField("tally_id")
    private Integer tallyId;
    @TableField("out_price")
    private BigDecimal outPrice;
    @TableField("out_num")
    private Integer outNum;
    @TableField("create_by")
    private Integer createBy;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 0 否 1 是
     */
    @TableField("is_out")
    private String isOut;
    //------------------追加的属性-------------------------
    @TableField(exist = false)
    private String productName;//商品名称

    @TableField(exist = false)
    /*添加的属性*/
    public BigDecimal salePrice;

    @TableField(exist = false)
    private String startTime;//起始时间

    @TableField(exist = false)
    private String endTime;//结束时间

    @TableField(exist = false)
    private String storeName;//仓库名称

    @TableField(exist = false)
    private String userCode;//创建出库单的用户的名称

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
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

    public Integer getOutsId() {
        return outsId;
    }

    public void setOutsId(Integer outsId) {
        this.outsId = outsId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getTallyId() {
        return tallyId;
    }

    public void setTallyId(Integer tallyId) {
        this.tallyId = tallyId;
    }

    public BigDecimal getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(BigDecimal outPrice) {
        this.outPrice = outPrice;
    }

    public Integer getOutNum() {
        return outNum;
    }

    public void setOutNum(Integer outNum) {
        this.outNum = outNum;
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

    public String getIsOut() {
        return isOut;
    }

    public void setIsOut(String isOut) {
        this.isOut = isOut;
    }

    @Override
    public String toString() {
        return "OutStore{" + "outsId=" + outsId + ", productId=" + productId + ", storeId=" + storeId + ", tallyId=" + tallyId + ", outPrice=" + outPrice + ", outNum=" + outNum + ", createBy=" + createBy + ", createTime=" + createTime + ", isOut=" + isOut + "}";
    }
}
