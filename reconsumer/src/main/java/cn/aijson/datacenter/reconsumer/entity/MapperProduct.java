package cn.aijson.datacenter.reconsumer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class MapperProduct {

    private Long id;
    /**
     * 产品名称
     */

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
    private List<Long> labelSet;

    private List<DictionaryItem> labels;
    @Valid
    private DictionaryItem disappearType;
    @Valid
    private DictionaryItem showType;


}
