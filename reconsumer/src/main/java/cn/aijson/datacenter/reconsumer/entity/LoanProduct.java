package cn.aijson.datacenter.reconsumer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-05-18
 */
@TableName("loan_product")
public class LoanProduct extends Model<LoanProduct> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 产品名称
     */
    @NotBlank(message = "产品名称不为空")
    private String platformName;
    /**
     * 产品logo
     */
    private String platformLogo;
    /**
     * 是否显示 0:显示，1：不显示
     */
    private Boolean showOnHomePage;
    /**
     * 是否为推荐产品 0：推荐，1：不推荐
     */
    private Boolean isRecommendProduct;
    /**
     * 产品详情
     */
    private String description;
    /**
     * 上架时间
     */
    private LocalDateTime showTime;
    /**
     * 下架时间
     */
    private LocalDateTime disappearTime;
    private String showTimeString;
    private String disappearTimeString;
    /**
     * 产品url
     */
    private String platformUrl;
    /**
     * 显示顺序
     */
    private Integer sortOrder;
    /**
     * 日利率 千分制
     */
    private BigDecimal rate;
    /**
     * 最低借款金额
     */
    private BigDecimal minAmount;
    /**
     * 最高借款金额
     */
    private BigDecimal maxAmount;
    /**
     * 最低期限（月）
     */
    private BigDecimal minTime;
    /**
     * 最高期限（月）
     */
    private BigDecimal maxTime;
    private BigDecimal clicks;
    private Long showTypeId;
    private Long disappearTypeId;
    @NotBlank(message = "创建人不能为空")
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
    /**
     * 审核方式
     */
    private String monitorMode;
    /**
     * 放款时间
     */
    private String lendingTime;
    /**
     * 放款周期
     */
    private String auditCycle;
    /**
     * 还款方式
     */
    private String paymentMethod;
    /**
     * 申请人数
     */
    private Integer applyNumber;

    /**
     * 自定义方法，与标签建立关系
     * @return
     */
    public void relateLabel(ProductLabel label)
    {
        label.setProductId(this.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformLogo() {
        return platformLogo;
    }

    public void setPlatformLogo(String platformLogo) {
        this.platformLogo = platformLogo;
    }

    public Boolean getShowOnHomePage() {
        return showOnHomePage;
    }

    public void setShowOnHomePage(Boolean showOnHomePage) {
        this.showOnHomePage = showOnHomePage;
    }

    public Boolean getRecommendProduct() {
        return isRecommendProduct;
    }

    public void setRecommendProduct(Boolean isRecommendProduct) {
        this.isRecommendProduct = isRecommendProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public LocalDateTime getDisappearTime() {
        return disappearTime;
    }

    public void setDisappearTime(LocalDateTime disappearTime) {
        this.disappearTime = disappearTime;
    }

    public String getShowTimeString() {
        return showTimeString;
    }

    public void setShowTimeString(String showTimeString) {
        this.showTimeString = showTimeString;
    }

    public String getDisappearTimeString() {
        return disappearTimeString;
    }

    public void setDisappearTimeString(String disappearTimeString) {
        this.disappearTimeString = disappearTimeString;
    }

    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public BigDecimal getMinTime() {
        return minTime;
    }

    public void setMinTime(BigDecimal minTime) {
        this.minTime = minTime;
    }

    public BigDecimal getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(BigDecimal maxTime) {
        this.maxTime = maxTime;
    }

    public BigDecimal getClicks() {
        return clicks;
    }

    public void setClicks(BigDecimal clicks) {
        this.clicks = clicks;
    }

    public Long getShowTypeId() {
        return showTypeId;
    }

    public void setShowTypeId(Long showTypeId) {
        this.showTypeId = showTypeId;
    }

    public Long getDisappearTypeId() {
        return disappearTypeId;
    }

    public void setDisappearTypeId(Long disappearTypeId) {
        this.disappearTypeId = disappearTypeId;
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

    public String getMonitorMode() {
        return monitorMode;
    }

    public void setMonitorMode(String monitorMode) {
        this.monitorMode = monitorMode;
    }

    public String getLendingTime() {
        return lendingTime;
    }

    public void setLendingTime(String lendingTime) {
        this.lendingTime = lendingTime;
    }

    public String getAuditCycle() {
        return auditCycle;
    }

    public void setAuditCycle(String auditCycle) {
        this.auditCycle = auditCycle;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(Integer applyNumber) {
        this.applyNumber = applyNumber;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LoanProduct{" +
        ", id=" + id +
        ", platformName=" + platformName +
        ", platformLogo=" + platformLogo +
        ", showOnHomePage=" + showOnHomePage +
        ", isRecommendProduct=" + isRecommendProduct +
        ", description=" + description +
        ", showTime=" + showTime +
        ", disappearTime=" + disappearTime +
        ", showTimeString=" + showTimeString +
        ", disappearTimeString=" + disappearTimeString +
        ", platformUrl=" + platformUrl +
        ", sortOrder=" + sortOrder +
        ", rate=" + rate +
        ", minAmount=" + minAmount +
        ", maxAmount=" + maxAmount +
        ", minTime=" + minTime +
        ", maxTime=" + maxTime +
        ", clicks=" + clicks +
        ", showTypeId=" + showTypeId +
        ", disappearTypeId=" + disappearTypeId +
        ", createdBy=" + createdBy +
        ", createdDate=" + createdDate +
        ", lastModifiedBy=" + lastModifiedBy +
        ", lastModifiedDate=" + lastModifiedDate +
        ", monitorMode=" + monitorMode +
        ", lendingTime=" + lendingTime +
        ", auditCycle=" + auditCycle +
        ", paymentMethod=" + paymentMethod +
        ", applyNumber=" + applyNumber +
        "}";
    }
}
