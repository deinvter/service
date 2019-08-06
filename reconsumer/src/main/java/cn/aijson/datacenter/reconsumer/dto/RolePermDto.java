package cn.aijson.datacenter.reconsumer.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RolePermDto {
    private long roleId;
    private Long[] permIds;
}
