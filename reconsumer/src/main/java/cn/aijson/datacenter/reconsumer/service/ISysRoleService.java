package cn.aijson.datacenter.reconsumer.service;

import cn.aijson.datacenter.reconsumer.dto.RoleDto;
import cn.aijson.datacenter.reconsumer.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
public interface ISysRoleService extends IService<SysRole> {

    RoleDto selectRoleById(long id);
}
