package cn.aijson.datacenter.reconsumer.dto;

import cn.aijson.datacenter.reconsumer.entity.SysPerm;
import cn.aijson.datacenter.reconsumer.entity.SysRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UgroupDto {
    private long id;
    /**
     * 用户组名
     */
    private String ugroupName;
    //角色
    List<SysRole> roles;
    //权限
    List<SysPerm> perms;
}
