package cn.aijson.datacenter.reconsumer.service;

import cn.aijson.datacenter.reconsumer.dto.UgroupDto;
import cn.aijson.datacenter.reconsumer.entity.SysUgroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户组表 服务类
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
public interface ISysUgroupService extends IService<SysUgroup> {

    UgroupDto selectUgroupById(long id);
}
