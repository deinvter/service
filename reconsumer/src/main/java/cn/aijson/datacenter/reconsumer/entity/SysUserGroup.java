package cn.aijson.datacenter.reconsumer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 用户和用户组关联表
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
@TableName("sys_user_group")
public class SysUserGroup extends Model<SysUserGroup> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户组id
     */
    private Long ugroupId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUgroupId() {
        return ugroupId;
    }

    public void setUgroupId(Long ugroupId) {
        this.ugroupId = ugroupId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysUserGroup{" +
        ", id=" + id +
        ", userId=" + userId +
        ", ugroupId=" + ugroupId +
        "}";
    }
}
