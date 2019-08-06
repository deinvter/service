package cn.aijson.datacenter.reconsumer.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-05-19
 */
@TableName("product_label")
public class ProductLabel extends Model<ProductLabel> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 标签id
     */
    private Long labelId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProductLabel{" +
        ", id=" + id +
        ", productId=" + productId +
        ", labelId=" + labelId +
        "}";
    }
    public ProductLabel() {

    }

    public ProductLabel(Long productId, Long labelId) {
        this.productId = productId;
        this.labelId = labelId;
    }

    public ProductLabel(Long id, Long productId, Long labelId) {
        this.id = id;
        this.productId = productId;
        this.labelId = labelId;
    }
}
