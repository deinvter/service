package cn.aijson.datacenter.reconsumer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-05-18
 */
@TableName("dictionary_item")
public class DictionaryItem extends Model<DictionaryItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不能为空")
    private Long id;
    private Long dictionaryId;
    private Integer parentItemValue;
    @NotNull(message = "name不能为空")
    private String name;
    @NotNull(message = "value不能为空")
    private String value;
    private String description;
    private Boolean active;
    private Integer sortOrder;
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Integer getParentItemValue() {
        return parentItemValue;
    }

    public void setParentItemValue(Integer parentItemValue) {
        this.parentItemValue = parentItemValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DictionaryItem{" +
        ", id=" + id +
        ", dictionaryId=" + dictionaryId +
        ", parentItemValue=" + parentItemValue +
        ", name=" + name +
        ", value=" + value +
        ", description=" + description +
        ", active=" + active +
        ", sortOrder=" + sortOrder +
        ", createdBy=" + createdBy +
        ", createdDate=" + createdDate +
        ", lastModifiedBy=" + lastModifiedBy +
        ", lastModifiedDate=" + lastModifiedDate +
        "}";
    }
}
