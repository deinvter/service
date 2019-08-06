package cn.aijson.datacenter.reconsumer.controller;


import cn.aijson.datacenter.reconsumer.annotation.PermInfo;
import cn.aijson.datacenter.reconsumer.dto.UgroupRoleDto;
import cn.aijson.datacenter.reconsumer.entity.SysUgroup;
import cn.aijson.datacenter.reconsumer.entity.SysUgroupRole;
import cn.aijson.datacenter.reconsumer.entity.SysUserGroup;
import cn.aijson.datacenter.reconsumer.service.ISysUgroupRoleService;
import cn.aijson.datacenter.reconsumer.service.ISysUgroupService;
import cn.aijson.datacenter.reconsumer.service.ISysUserGroupService;
import cn.aijson.datacenter.reconsumer.utils.Json;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户组表 前端控制器
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
@PermInfo(pval = "sys:ugroup",value = "用户组模块")
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/sysUgroup")
public class SysUgroupController {
    @Autowired
    private ISysUgroupService sysUgroupService;
    @Autowired
    private ISysUgroupRoleService sysUgroupRoleService;
    @Autowired
    private ISysUserGroupService sysUserGroupService;

    @PermInfo(value = "添加用户组")
    @RequiresPermissions("sys:ugroup:add")
    @PostMapping("/add")
    @ResponseBody
    public Json addUgroup(@Valid @RequestBody SysUgroup sysUgroup, BindingResult bindingResult){
        String oper="add ugroup";
        log.info("oper:{}  body:{}",oper,sysUgroup.toString());
        if(bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().toString();
            log.info(msg);
            return Json.fail(msg);
        }
        return Json.result(sysUgroupService.insert(sysUgroup));
    }

    @PermInfo(value = "编辑用户组")
    @RequiresPermissions("sys:ugroup:update")
    @PostMapping("/update")
    @ResponseBody
    public Json updateUgroup(@Valid @RequestBody SysUgroup sysUgroup, BindingResult bindingResult){
        String oper="update ugroup";
        log.info("oper:{}  body:{}",oper,sysUgroup.toString());
        if(bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().toString();
            log.info(msg);
            return Json.fail(msg);
        }
        Long id = sysUgroup.getId();
        if(id==null){
            return Json.fail("id不能为空");
        }
        QueryWrapper<SysUgroup> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return Json.result(sysUgroupService.update(sysUgroup,queryWrapper));
    }


    @Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
    @PermInfo(value = "删除用户组")
    @RequiresPermissions("sys:ugroup:delete")
    @GetMapping("/delete")
    @ResponseBody
    public Json deleteUgroup(@RequestParam("id")long id){
        String oper="delete ugroup";
        log.info("oper:{}  ugroupId:{}",oper,id);
        //删除用户组
       boolean groupFlag= sysUgroupService.deleteById(id);
        boolean flag=false;
       if(groupFlag){
           //删除用户与用户组关联信息
           log.info("user unrelate ugroup ");
           QueryWrapper<SysUserGroup> userGroupQueryWrapper=new QueryWrapper<>();
           userGroupQueryWrapper.eq("ugroup_id",id);
           boolean userFlag= sysUserGroupService.delete(userGroupQueryWrapper);
           //删除用户组与角色关联信息
           log.info("ugroup  unrelate role");
           QueryWrapper<SysUgroupRole> ugroupRoleQueryWrapper=new QueryWrapper<>();
           ugroupRoleQueryWrapper.eq("ugroup_id",id);
           boolean roleFlag= sysUgroupRoleService.delete(ugroupRoleQueryWrapper);
           flag=userFlag&&roleFlag;
       }
        return Json.result(flag);
    }

    @PermInfo(value = "用户组列表")
    @RequiresPermissions("sys:ugroup:list")
    @GetMapping("/list")
    @ResponseBody
    public Json ugroupList(@RequestParam(value = "current",required =false,defaultValue = "1")String current,
                           @RequestParam(value = "size",required = false,defaultValue = "10")String size){
        log.info("ugroup list");
        Page page = new Page(Long.valueOf(current),Long.valueOf(size));
        QueryWrapper<SysUgroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Json.succ(sysUgroupService.selectPage(page,queryWrapper));
    }

    @PermInfo(value = "用户组详情")
    @RequiresPermissions("sys:ugroup:info")
    @GetMapping("/info")
    @ResponseBody
    public Json ugroupInfo(@RequestParam("id")long id){
        String oper="ugroup info";
        log.info("oper:{}  ugroupId:{}",oper,id);
       return Json.succ(sysUgroupService.selectUgroupById(id));
    }

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
    @PermInfo(value = "关联用户组和角色")
    @RequiresPermissions("sys:ugroup:relate")
    @PostMapping("/relateRole")
    @ResponseBody
    public Json ugroupRelateRole(@Valid @RequestBody UgroupRoleDto ugroupRoleDto, BindingResult bindingResult){
        String oper=" ugroup  relate role";
        log.info("oper:{}  ugroupId:{}",oper,ugroupRoleDto.toString());
        if(bindingResult.hasErrors()){
            log.info(bindingResult.getFieldError().toString());
            return Json.fail(bindingResult.getFieldError().toString());
        }
        Long ugroupId= ugroupRoleDto.getUgroupId();
        Long[] roleIds = ugroupRoleDto.getRoleIds();

        //查找是否已经存在关联
        QueryWrapper<SysUgroupRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("ugroup_id",ugroupId);
        List<SysUgroupRole> list =sysUgroupRoleService.selectList(queryWrapper);
        SysUgroupRole sysUgroupRole=new SysUgroupRole();
        if(list.size()!=0){
            //用户组与角色一对多关系，先删除再添加
            boolean flag =sysUgroupRoleService.delete(queryWrapper);
            if(roleIds==null){
                //取消关联
                log.info("ugroup  unrelate role");
                return Json.result(flag);
            }
        }

        for (long roleId:roleIds){
            sysUgroupRole.setRoleId(roleId);
            sysUgroupRole.setUgroupId(ugroupId);
            sysUgroupRoleService.insert(sysUgroupRole);
        }
        return Json.succ();

    }
//    @PermInfo(value = "取消关联用户组和角色")
//    @RequiresPermissions("sys:ugroup:unrelate")
//    @PostMapping("/unrelateRole")
//    @ResponseBody
//    public Json userUnrelateUgroup(@Valid @RequestBody UgroupRoleDto ugroupRoleDto, BindingResult bindingResult) {
//        String oper=" ugroup  unrelate role";
//        log.info("oper:{}  ugroupId:{}",oper,ugroupRoleDto.toString());
//        if (bindingResult.hasErrors()) {
//            log.info(bindingResult.getFieldError().toString());
//            return Json.fail(bindingResult.getFieldError().toString());
//        }
//        Long ugroupId = ugroupRoleDto.getUgroupId();
//        Long[] roleIds = ugroupRoleDto.getRoleIds();
//
//        //查找是否已经存在关联
//        QueryWrapper<SysUgroupRole> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("ugroup_id", ugroupId);
//        List<SysUgroupRole> list = sysUgroupRoleService.selectList(queryWrapper);
//        SysUgroupRole sysUgroupRole = new SysUgroupRole();
//        if (list.size() != 0) {
//            //用户组与角色一对多关系，先删除再添加
//            return Json.result(sysUgroupRoleService.delete(queryWrapper));
//        }
//        return Json.fail("取消关联失败");
//    }
}

