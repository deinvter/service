package cn.aijson.datacenter.reconsumer.service;

import cn.aijson.datacenter.reconsumer.dto.UserDto;
import cn.aijson.datacenter.reconsumer.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色组和权限关联表 服务类
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
public interface ISysUserService extends IService<SysUser> {

    UserDto selectUser(Long userId);
     List<Long> queryPermsByUserId(Long userId);
}
