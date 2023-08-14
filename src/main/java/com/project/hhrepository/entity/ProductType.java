package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品分类表
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@TableName("product_type")
public class ProductType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "type_id", type = IdType.AUTO)
    private Integer typeId;

    @TableField("parent_id")
    private Integer parentId;

    @TableField("type_code")
    private String typeCode;

    @TableField("type_name")
    private String typeName;

    @TableField("type_desc")
    private String typeDesc;

    /*---------------------追加的属性--------------------------*/
    //存放当前分类下的所有子级分类
    @TableField(exist = false)
    private List<ProductType> childProductCategory;

    public List<ProductType> getChildProductCategory() {
        return childProductCategory;
    }

    public void setChildProductCategory(List<ProductType> childProductCategory) {
        this.childProductCategory = childProductCategory;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    @Override
    public String toString() {
        return "ProductType{" + "typeId=" + typeId + ", parentId=" + parentId + ", typeCode=" + typeCode + ", " +
                "typeName=" + typeName + ", typeDesc=" + typeDesc + "}";
    }
}
