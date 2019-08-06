package cn.aijson.datacenter.reconsumer.dto;

import cn.aijson.datacenter.reconsumer.entity.SysPerm;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RoleDto {
    private Long id;
    /**
     * 角色名
     */
    private String roleName;
    //权限
    List<SysPerm> perms;
}
