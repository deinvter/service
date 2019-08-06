package cn.aijson.datacenter.reconsumer.service.impl;

import cn.aijson.datacenter.reconsumer.dto.UgroupDto;
import cn.aijson.datacenter.reconsumer.entity.SysUgroup;
import cn.aijson.datacenter.reconsumer.mapper.SysCustomMapper;
import cn.aijson.datacenter.reconsumer.mapper.SysUgroupMapper;
import cn.aijson.datacenter.reconsumer.service.ISysUgroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户组表 服务实现类
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
@Service
public class SysUgroupServiceImpl extends ServiceImpl<SysUgroupMapper, SysUgroup> implements ISysUgroupService {
    @Resource
    private SysCustomMapper sysCustomMapper;
    @Override
    public UgroupDto selectUgroupById(long id) {
        return sysCustomMapper.selectUgroupById(id);
    }
}
