package cn.aijson.datacenter.reconsumer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 用户组和角色关联表
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
@TableName("sys_ugroup_role")
public class SysUgroupRole extends Model<SysUgroupRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户组id
     */
    private Long ugroupId;
    /**
     * 角色id
     */
    private Long roleId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUgroupId() {
        return ugroupId;
    }

    public void setUgroupId(Long ugroupId) {
        this.ugroupId = ugroupId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysUgroupRole{" +
        ", id=" + id +
        ", ugroupId=" + ugroupId +
        ", roleId=" + roleId +
        "}";
    }
}
