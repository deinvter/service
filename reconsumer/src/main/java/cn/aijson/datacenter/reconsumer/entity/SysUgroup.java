package cn.aijson.datacenter.reconsumer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户组表
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
@TableName("sys_ugroup")
public class SysUgroup extends Model<SysUgroup> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户组名
     */
    private String ugroupName;
    /**
     * 创建时间
     */
    private LocalDateTime created;
    /**
     * 修改时间
     */
    private LocalDateTime updated;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUgroupName() {
        return ugroupName;
    }

    public void setUgroupName(String ugroupName) {
        this.ugroupName = ugroupName;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysUgroup{" +
        ", id=" + id +
        ", ugroupName=" + ugroupName +
        ", created=" + created +
        ", updated=" + updated +
        "}";
    }
}
