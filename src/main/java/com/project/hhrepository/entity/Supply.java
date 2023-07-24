package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 供货商
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@TableName("supply")
public class Supply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "supply_id", type = IdType.AUTO)
    private Integer supplyId;

    @TableField("supply_num")
    private String supplyNum;

    @TableField("supply_name")
    private String supplyName;

    @TableField("supply_introduce")
    private String supplyIntroduce;

    @TableField("concat")
    private String concat;

    @TableField("phone")
    private String phone;

    @TableField("address")
    private String address;

    /**
     * 0:可用  1:不可用
     */
    @TableField("is_delete")
    @TableLogic
    private String isDelete;

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }
    public String getSupplyNum() {
        return supplyNum;
    }

    public void setSupplyNum(String supplyNum) {
        this.supplyNum = supplyNum;
    }
    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }
    public String getSupplyIntroduce() {
        return supplyIntroduce;
    }

    public void setSupplyIntroduce(String supplyIntroduce) {
        this.supplyIntroduce = supplyIntroduce;
    }
    public String getConcat() {
        return concat;
    }

    public void setConcat(String concat) {
        this.concat = concat;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Supply{" +
            "supplyId=" + supplyId +
            ", supplyNum=" + supplyNum +
            ", supplyName=" + supplyName +
            ", supplyIntroduce=" + supplyIntroduce +
            ", concat=" + concat +
            ", phone=" + phone +
            ", address=" + address +
            ", isDelete=" + isDelete +
        "}";
    }
}
