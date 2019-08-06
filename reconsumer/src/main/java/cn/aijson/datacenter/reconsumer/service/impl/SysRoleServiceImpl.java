package cn.aijson.datacenter.reconsumer.service.impl;

import cn.aijson.datacenter.reconsumer.dto.RoleDto;
import cn.aijson.datacenter.reconsumer.entity.SysRole;
import cn.aijson.datacenter.reconsumer.mapper.SysCustomMapper;
import cn.aijson.datacenter.reconsumer.mapper.SysRoleMapper;
import cn.aijson.datacenter.reconsumer.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    private SysCustomMapper sysCustomMapper;
    @Override
    public RoleDto selectRoleById(long id) {
        return sysCustomMapper.selectRoleById(id);
    }
}
