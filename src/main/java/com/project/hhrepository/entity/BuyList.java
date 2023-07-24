package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 采购单
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@TableName("buy_list")
public class BuyList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "buy_id", type = IdType.AUTO)
    private Integer buyId;

    @TableField("product_id")
    private Integer productId;

    @TableField("store_id")
    private Integer storeId;

    @TableField("buy_num")
    private Integer buyNum;

    @TableField("fact_buy_num")
    private Integer factBuyNum;

    @TableField("buy_time")
    private LocalDateTime buyTime;

    @TableField("supply_id")
    private Integer supplyId;

    @TableField("place_id")
    private Integer placeId;

    @TableField("buy_user")
    private String buyUser;

    @TableField("phone")
    private String phone;

    /**
     * 0 否 1 是
     */
    @TableField("is_in")
    private String isIn;

    public Integer getBuyId() {
        return buyId;
    }

    public void setBuyId(Integer buyId) {
        this.buyId = buyId;
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
    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }
    public Integer getFactBuyNum() {
        return factBuyNum;
    }

    public void setFactBuyNum(Integer factBuyNum) {
        this.factBuyNum = factBuyNum;
    }
    public LocalDateTime getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(LocalDateTime buyTime) {
        this.buyTime = buyTime;
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
    public String getBuyUser() {
        return buyUser;
    }

    public void setBuyUser(String buyUser) {
        this.buyUser = buyUser;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getIsIn() {
        return isIn;
    }

    public void setIsIn(String isIn) {
        this.isIn = isIn;
    }

    @Override
    public String toString() {
        return "BuyList{" +
            "buyId=" + buyId +
            ", productId=" + productId +
            ", storeId=" + storeId +
            ", buyNum=" + buyNum +
            ", factBuyNum=" + factBuyNum +
            ", buyTime=" + buyTime +
            ", supplyId=" + supplyId +
            ", placeId=" + placeId +
            ", buyUser=" + buyUser +
            ", phone=" + phone +
            ", isIn=" + isIn +
        "}";
    }
}
