package cn.aijson.datacenter.reconsumer.service.impl;

import cn.aijson.datacenter.reconsumer.dto.UserDto;
import cn.aijson.datacenter.reconsumer.entity.SysUser;
import cn.aijson.datacenter.reconsumer.mapper.SysCustomMapper;
import cn.aijson.datacenter.reconsumer.mapper.SysUserMapper;
import cn.aijson.datacenter.reconsumer.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色组和权限关联表 服务实现类
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Resource
    SysCustomMapper sysCustomMapper;
    @Override
    public UserDto selectUser(Long userId) {
        return sysCustomMapper.queryUser(userId);
    }
    @Override
    public List<Long> queryPermsByUserId(Long userId) {
        return sysCustomMapper.queryPermsByUserId(userId);
    }
}
