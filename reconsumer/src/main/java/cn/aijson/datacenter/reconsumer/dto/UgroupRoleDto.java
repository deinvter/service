package cn.aijson.datacenter.reconsumer.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UgroupRoleDto {
    private long ugroupId;
    private Long[] roleIds;
}
