package cn.aijson.datacenter.reconsumer.controller;


import cn.aijson.datacenter.reconsumer.annotation.PermInfo;
import cn.aijson.datacenter.reconsumer.entity.SysUser;
import cn.aijson.datacenter.reconsumer.entity.SysUserGroup;
import cn.aijson.datacenter.reconsumer.service.ISysUserGroupService;
import cn.aijson.datacenter.reconsumer.service.ISysUserService;
import cn.aijson.datacenter.reconsumer.utils.Json;
import cn.aijson.datacenter.reconsumer.utils.Md5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 角色组和权限关联表 前端控制器
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
@PermInfo(pval = "sys:user",value = "用户模块")
@RestController
@CrossOrigin
@RequestMapping("/sysUser")
@Slf4j
public class SysUserController {
    @Autowired
    ISysUserService sysUserService;
    @Autowired
    ISysUserGroupService sysUserGroupService;

    @PermInfo(value = "创建用户")
    @RequiresPermissions("sys:user:add")
    @PostMapping("/add")
    @ResponseBody
    public Json addUser(@Valid @RequestBody SysUser user, BindingResult bindingResult){
        String oper=" add user";
        log.info("oper:{}  body:{}",oper,user.toString());
        if(bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().toString();
            log.info(msg);
            return Json.fail(msg);
        }
        String password = Md5.md5Encode(user.getPassword()) ;
        user.setPassword(password);
        return Json.result(sysUserService.insert(user));
    }

    @PermInfo(value = "编辑用户")
    @RequiresPermissions("sys:user:update")
    @PostMapping("/update")
    @ResponseBody
    public Json updateUser(@Valid@RequestBody SysUser user, BindingResult bindingResult){
        String oper=" update user";
        log.info("oper:{}  body:{}",oper,user.toString());
        if(bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().toString();
            log.info(msg);
            return Json.fail(msg);
        }
        String password = Md5.md5Encode(user.getPassword()) ;
        user.setPassword(password);
        return Json.result(sysUserService.updateById(user));
    }

    @PermInfo(value = "删除用户")
    @RequiresPermissions("sys:user:delete")
    @GetMapping("/delete")
    @ResponseBody
    public Json deleteUser(@RequestParam("id")Long id){

        log.info("delete user:{}",id);

        return Json.result(sysUserService.deleteById(id));
    }

    @PermInfo(value = "用户列表")
    @RequiresPermissions("sys:user:list")
    @GetMapping("/list")
    @ResponseBody
    public Json selectAllUser(@RequestParam(value = "current",required =false,defaultValue = "1")String current,
                              @RequestParam(value = "size",required = false,defaultValue = "10")String size){
        log.info("user list");
       Page page = new Page(Long.valueOf(current),Long.valueOf(size));
       QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
       queryWrapper.orderByDesc("id");
        return Json.succ(sysUserService.selectPage(page,queryWrapper));
    }
    @PermInfo(value = "用户详情")
    @RequiresPermissions("sys:user:info")
    @GetMapping("/info")
    @ResponseBody
    public Json selectUser(@RequestParam(value = "id")long id){
        String oper="user info";
        log.info("oper:{}  id:{}",oper,id);
        return Json.succ(sysUserService.selectUser(id));
    }


    @PermInfo(value = "关联用户和用户组")
    @RequiresPermissions("sys:user:relate")
    @PostMapping("/relateUgroup")
    @ResponseBody
    public Json userRelateUgroup(@Valid @RequestBody SysUserGroup sysUserGroup, BindingResult bindingResult){
        String oper="user relate ugroup";
        log.info("oper:{}  body:{}",oper,sysUserGroup.toString());
        if(bindingResult.hasErrors()){
            log.info(bindingResult.getFieldError().toString());
            return Json.fail(bindingResult.getFieldError().toString());
        }
        Long ugroupId=sysUserGroup.getUgroupId();
        Long userId=sysUserGroup.getUserId();
        QueryWrapper<SysUserGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        //保证用户与用户组一对一关系
        if(sysUserGroupService.selectOne(queryWrapper)!=null){
            //取消关联
            if(ugroupId==null){
                log.info("user unrelate ugroup");
                return Json.result(sysUserGroupService.delete(queryWrapper)) ;
            }
            return Json.fail("一个用户只能属于一个用户组");
        }
        return  Json.result(sysUserGroupService.insert(sysUserGroup));
    }
//    @PermInfo(value = "取消关联用户和用户组")
//    @RequiresPermissions("sys:user:unrelate")
//    @PostMapping("/unrelateUgroup")
//    @ResponseBody
//    public Json userUnrelateUgroup(@Valid @RequestBody SysUserGroup sysUserGroup, BindingResult bindingResult){
//        String oper="user unrelate ugroup";
//        log.info("oper:{}  body:{}",oper,sysUserGroup.toString());
//        if(bindingResult.hasErrors()){
//            log.info(bindingResult.getFieldError().toString());
//            return Json.fail(bindingResult.getFieldError().toString());
//        }
//        Long userId = sysUserGroup.getUserId();
//        QueryWrapper<SysUserGroup> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id",userId);
//        return  Json.result(sysUserGroupService.delete(queryWrapper));
//    }

}

