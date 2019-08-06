package cn.aijson.datacenter.reconsumer.dto;

import cn.aijson.datacenter.reconsumer.entity.SysPerm;
import cn.aijson.datacenter.reconsumer.entity.SysRole;
import cn.aijson.datacenter.reconsumer.entity.SysUgroup;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDto {
    private Long id;
    private String name;
    private String password;
    //用户组
    private SysUgroup sysUgroup;
    //角色
    List<SysRole> roles;
    //权限
    List<SysPerm> perms;
}
