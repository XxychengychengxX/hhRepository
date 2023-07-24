package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 产地
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@TableName("place")
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "place_id", type = IdType.AUTO)
    private Integer placeId;

    @TableField("place_name")
    private String placeName;

    @TableField("place_num")
    private String placeNum;

    @TableField("introduce")
    private String introduce;

    /**
     * 0:可用  1:不可用
     */
    @TableField("is_delete")
    @TableLogic
    private String isDelete;

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
    public String getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(String placeNum) {
        this.placeNum = placeNum;
    }
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Place{" +
            "placeId=" + placeId +
            ", placeName=" + placeName +
            ", placeNum=" + placeNum +
            ", introduce=" + introduce +
            ", isDelete=" + isDelete +
        "}";
    }
}
