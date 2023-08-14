package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@TableName("auth_info")
public class AuthInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "auth_id", type = IdType.AUTO)
    private Integer authId;

    /**
     * 父id为空或为0，表示一级权限
     */
    @TableField("parent_id")
    private Integer parentId;

    @TableField("auth_name")
    private String authName;

    @TableField("auth_desc")
    private String authDesc;

    @TableField("auth_grade")
    private Integer authGrade;

    /**
     * 1 模块 、2  列表、 3  按钮
     */
    @TableField("auth_type")
    private String authType;

    @TableField("auth_url")
    private String authUrl;

    @TableField("auth_code")
    private String authCode;

    @TableField("auth_order")
    private Integer authOrder;

    /**
     * 1 启用 、0 禁用
     */
    @TableField("auth_state")
    private String authState;

    @TableField("create_by")
    private Integer createBy;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField("update_by")
    private Integer updateBy;

    //追加的List<AuthInfo>集合属性 -- 用于存储当前权限(菜单)的子级权限(菜单)
    @TableField(exist = false)
    private List<AuthInfo> childAuthInfo;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public List<AuthInfo> getChildAuth() {
        return childAuthInfo;
    }

    public void setChildAuth(List<AuthInfo> childAuthInfo) {
        this.childAuthInfo = childAuthInfo;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthDesc() {
        return authDesc;
    }

    public void setAuthDesc(String authDesc) {
        this.authDesc = authDesc;
    }

    public Integer getAuthGrade() {
        return authGrade;
    }

    public void setAuthGrade(Integer authGrade) {
        this.authGrade = authGrade;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Integer getAuthOrder() {
        return authOrder;
    }

    public void setAuthOrder(Integer authOrder) {
        this.authOrder = authOrder;
    }

    public String getAuthState() {
        return authState;
    }

    public void setAuthState(String authState) {
        this.authState = authState;
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

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AuthInfo{" + "authId=" + authId + ", parentId=" + parentId + ", authName=" + authName + ", authDesc=" + authDesc + ", authGrade=" + authGrade + ", authType=" + authType + ", authUrl=" + authUrl + ", authCode=" + authCode + ", authOrder=" + authOrder + ", authState=" + authState + ", createBy=" + createBy + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + "}";
    }
}
