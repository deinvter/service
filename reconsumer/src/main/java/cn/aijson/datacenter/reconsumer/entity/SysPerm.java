package cn.aijson.datacenter.reconsumer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
@TableName("sys_perm")
public class SysPerm extends Model<SysPerm> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 权限值，shiro的权限控制表达式
     */
    private String pval;
    /**
     * 父权限值
     */
    private String parent;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型：1.菜单 2.按钮 3.接口 4.特殊
     */
    private Integer type;
    /**
     * 是否叶子节点
     */
    private Boolean leaf;
    /**
     * 创建时间
     */
    private LocalDateTime created;
    /**
     * 修改时间
     */
    private LocalDateTime updated;
    @TableField(exist = false)
    private List<SysPerm> children=new ArrayList<>();


    public List<SysPerm> getChildren() {
        return children;
    }
    public void setChildren(List<SysPerm> children){this.children=children;}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPval() {
        return pval;
    }

    public void setPval(String pval) {
        this.pval = pval;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
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
        return "SysPerm{" +
                "id=" + id +
                ", pval='" + pval + '\'' +
                ", parent='" + parent + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", leaf=" + leaf +
                ", created=" + created +
                ", updated=" + updated +
                ", children=" + children +
                '}';
    }
}
