package cn.aijson.datacenter.reconsumer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 * 贷超访问日志表
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-05-20
 */
@TableName("pv_log")
public class PvLog extends Model<PvLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 渠道code
     */
    private String channelCode;
    /**
     * 访问产品id
     */
    private Integer loanProductId;
    /**
     * 访问路径
     */
    private String pathName;
    /**
     * 访问标签名称
     */
    private String listLabelName;
    /**
     * 浏览器指纹id
     */
    private String webviewId;
    /**
     * 客户端ipjson
     */
    private String ip;
    /**
     * gps
     */
    private String gps;
    /**
     * json 数据
     */
    private String jsonData;
    /**
     * 访问时间
     */
    private LocalDateTime createdDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getLoanProductId() {
        return loanProductId;
    }

    public void setLoanProductId(Integer loanProductId) {
        this.loanProductId = loanProductId;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getListLabelName() {
        return listLabelName;
    }

    public void setListLabelName(String listLabelName) {
        this.listLabelName = listLabelName;
    }

    public String getWebviewId() {
        return webviewId;
    }

    public void setWebviewId(String webviewId) {
        this.webviewId = webviewId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PvLog{" +
                ", id=" + id +
                ", channelCode=" + channelCode +
                ", loanProductId=" + loanProductId +
                ", pathName=" + pathName +
                ", listLabelName=" + listLabelName +
                ", webviewId=" + webviewId +
                ", ip=" + ip +
                ", gps=" + gps +
                ", jsonData=" + jsonData +
                ", createdDate=" + createdDate +
                "}";
    }
}
