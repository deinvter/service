package cn.aijson.datacenter.reconsumer.enums;

public enum ErrorCode {

   OK(200,"success"),
    ERROR(401,"notfound");
    private int code;
    private  String desc;
    ErrorCode(int code,String desc) {
        this.code=code;
        this.desc=desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
