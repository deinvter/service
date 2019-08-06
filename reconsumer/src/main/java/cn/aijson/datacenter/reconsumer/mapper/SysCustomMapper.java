package cn.aijson.datacenter.reconsumer.mapper;

import cn.aijson.datacenter.reconsumer.dto.RoleDto;
import cn.aijson.datacenter.reconsumer.dto.UgroupDto;
import cn.aijson.datacenter.reconsumer.dto.UserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SysCustomMapper {
    UserDto queryUser(@Param("userId") long userId);
    List<UserDto> queryAllUser();

    List<Long> queryPermsByUserId(@Param("userId") long userId);

    UgroupDto selectUgroupById(long id);

    RoleDto selectRoleById(long id);
}
