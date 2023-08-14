package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@TableName("product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    @TableField("store_id")
    private Integer storeId;

    @TableField("brand_id")
    private Integer brandId;

    @TableField("product_name")
    private String productName;

    @TableField("product_num")
    private String productNum;

    @TableField("product_invent")
    private Integer productInvent;

    @TableField("type_id")
    private Integer typeId;

    @TableField("supply_id")
    private Integer supplyId;

    @TableField("place_id")
    private Integer placeId;

    @TableField("unit_id")
    private Integer unitId;

    @TableField("introduce")
    private String introduce;

    /**
     * 0 下架 1 上架
     */
    @TableField("up_down_state")
    private String upDownState;

    @TableField("in_price")
    private BigDecimal inPrice;

    @TableField("sale_price")
    private BigDecimal salePrice;

    @TableField("mem_price")
    private BigDecimal memPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField("create_by")
    private Integer createBy;

    @TableField("update_by")
    private Integer updateBy;

    @TableField("imgs")
    private String imgs;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@JSONField(format = "yyyy-MM-dd")
    @TableField("product_date")
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date productDate;

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@JSONField(format = "yyyy-MM-dd")
    @TableField("supp_date")
    private Date suppDate;

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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public Integer getProductInvent() {
        return productInvent;
    }

    public void setProductInvent(Integer productInvent) {
        this.productInvent = productInvent;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getUpDownState() {
        return upDownState;
    }

    public void setUpDownState(String upDownState) {
        this.upDownState = upDownState;
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getMemPrice() {
        return memPrice;
    }

    public void setMemPrice(BigDecimal memPrice) {
        this.memPrice = memPrice;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public Date getSuppDate() {
        return suppDate;
    }

    public void setSuppDate(Date suppDate) {
        this.suppDate = suppDate;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", storeId=" + storeId + ", brandId=" + brandId + ", " +
                "productName=" + productName + ", productNum=" + productNum + ", productInvent=" + productInvent + "," +
                " typeId=" + typeId + ", supplyId=" + supplyId + ", placeId=" + placeId + ", unitId=" + unitId + ", " +
                "introduce=" + introduce + ", upDownState=" + upDownState + ", inPrice=" + inPrice + ", salePrice=" + salePrice + ", memPrice=" + memPrice + ", createTime=" + createTime + ", updateTime=" + updateTime + ", createBy=" + createBy + ", updateBy=" + updateBy + ", imgs=" + imgs + ", productDate=" + productDate + ", suppDate=" + suppDate + "}";
    }
}
