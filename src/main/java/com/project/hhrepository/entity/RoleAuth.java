package com.project.hhrepository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@TableName("role_auth")
public class RoleAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_auth_id", type = IdType.AUTO)
    private Integer roleAuthId;

    @TableField("role_id")
    private Integer roleId;

    @TableField("auth_id")
    private Integer authId;

    public Integer getRoleAuthId() {
        return roleAuthId;
    }

    public void setRoleAuthId(Integer roleAuthId) {
        this.roleAuthId = roleAuthId;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    @Override
    public String toString() {
        return "RoleAuth{" +
            "roleAuthId=" + roleAuthId +
            ", roleId=" + roleId +
            ", authId=" + authId +
        "}";
    }
}
