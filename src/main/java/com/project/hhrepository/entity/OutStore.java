package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private LocalDateTime createTime;

    /**
     * 0 否 1 是
     */
    @TableField("is_out")
    private String isOut;

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
        return "OutStore{" +
            "outsId=" + outsId +
            ", productId=" + productId +
            ", storeId=" + storeId +
            ", tallyId=" + tallyId +
            ", outPrice=" + outPrice +
            ", outNum=" + outNum +
            ", createBy=" + createBy +
            ", createTime=" + createTime +
            ", isOut=" + isOut +
        "}";
    }
}
