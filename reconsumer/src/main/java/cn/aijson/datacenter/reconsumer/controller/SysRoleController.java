package cn.aijson.datacenter.reconsumer.controller;


import cn.aijson.datacenter.reconsumer.annotation.PermInfo;
import cn.aijson.datacenter.reconsumer.dto.RolePermDto;
import cn.aijson.datacenter.reconsumer.entity.SysRole;
import cn.aijson.datacenter.reconsumer.entity.SysRolePerm;
import cn.aijson.datacenter.reconsumer.entity.SysUgroupRole;
import cn.aijson.datacenter.reconsumer.service.ISysRolePermService;
import cn.aijson.datacenter.reconsumer.service.ISysRoleService;
import cn.aijson.datacenter.reconsumer.service.ISysUgroupRoleService;
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
 * 角色表 前端控制器
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
@Slf4j
@PermInfo(pval = "sys:role",value = "角色模块")
@RestController
@CrossOrigin
@RequestMapping("/sysRole")
public class SysRoleController {
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysRolePermService sysRolePermService;
    @Autowired
    private ISysUgroupRoleService sysUgroupRoleService;

    @PermInfo(value = "添加角色")
    @RequiresPermissions("sys:role:add")
    @PostMapping("/add")
    @ResponseBody
    public Json addRole(@Valid @RequestBody SysRole sysRole, BindingResult bindingResult){
        String oper="add role";
        log.info("oper:{} body:{} ",oper,sysRole.toString());
        if(bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().toString();
            log.info(msg);
            return Json.fail(msg);
        }
        return Json.result(sysRoleService.insert(sysRole));
    }

    @PermInfo(value = "编辑角色")
    @RequiresPermissions("sys:role:update")
    @PostMapping("/update")
    @ResponseBody
    public Json updateRole(@Valid @RequestBody SysRole sysRole, BindingResult bindingResult){
        String oper="update role";
        log.info("oper:{} body:{} ",oper,sysRole.toString());
        if(bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().toString();
            log.info(msg);
            return Json.fail(msg);
        }
        Long id = sysRole.getId();
        if(id==null){
            return Json.fail("id不能为空");
        }
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return Json.result(sysRoleService.update(sysRole,queryWrapper));
    }

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
    @PermInfo(value = "删除角色")
    @RequiresPermissions("sys:role:delete")
    @GetMapping("/delete")
    @ResponseBody
    public Json deleteRole(@RequestParam("id")long id){
        String oper="delete role";
        log.info("oper:{} roleId:{} ",oper,id);
        boolean roleFlag= sysRoleService.deleteById(id);
        boolean flag = false;
        if(roleFlag){
            //删除角色和权限关联信息
            log.info("role unrelete perm");
            QueryWrapper<SysRolePerm> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",id);
            boolean permFlag= sysRolePermService.delete(queryWrapper);
            //删除用户组与角色关联信息
            log.info("ugroup  unrelate role");
            QueryWrapper<SysUgroupRole> ugroupRoleQueryWrapper=new QueryWrapper<>();
            ugroupRoleQueryWrapper.eq("role_id",id);
            boolean groupFlag= sysUgroupRoleService.delete(ugroupRoleQueryWrapper);
            flag=permFlag&&groupFlag;
        }
        return Json.result(flag);
    }

    @PermInfo(value = "角色列表")
    @RequiresPermissions("sys:role:list")
    @GetMapping("/list")
    @ResponseBody
    public Json roleList(@RequestParam(value = "current",required =false,defaultValue = "1")String current,
                         @RequestParam(value = "size",required = false,defaultValue = "10")String size){
        Page page = new Page(Long.valueOf(current),Long.valueOf(size));
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Json.succ(sysRoleService.selectPage(page,queryWrapper));
    }

    @PermInfo(value = "角色详情")
    @RequiresPermissions("sys:role:info")
    @GetMapping("/info")
    @ResponseBody
    public Json roleInfo(@RequestParam("id")long id){
        String oper="role info";
        log.info("oper:{} ",oper);
        return Json.succ(sysRoleService.selectRoleById(id));
    }
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
    @PermInfo(value = "关联角色和权限")
    @RequiresPermissions("sys:role:relate")
    @PostMapping("/relatePerm")
    @ResponseBody
    public Json roleRelatePerm(@Valid @RequestBody RolePermDto rolePermDto, BindingResult bindingResult){
        String oper="role relete perm";
        log.info("oper:{} body:{} ",oper,rolePermDto);
        if(bindingResult.hasErrors()){
            log.info(bindingResult.getFieldError().toString());
            return Json.fail(bindingResult.getFieldError().toString());
        }
        Long roleId=rolePermDto.getRoleId();
        Long[] permIds = rolePermDto.getPermIds();

        //查找是否已经存在关联
        QueryWrapper<SysRolePerm> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<SysRolePerm> list =sysRolePermService.selectList(queryWrapper);
        SysRolePerm sysRolePerm=new SysRolePerm();
        if(list.size()!=0){
            //用户组与角色一对多关系，先删除再添加
            boolean flag= sysRolePermService.delete(queryWrapper);
            if(permIds==null){
                //取消关联
                log.info("role unrelete perm");
                return Json.result(flag);
            }
        }
        for (long permId:permIds){
            sysRolePerm.setPermId(permId);
            sysRolePerm.setRoleId(roleId);
            sysRolePermService.insert(sysRolePerm);
        }
        return Json.succ();

    }
//    @PermInfo(value = "取消关联角色和权限")
//    @RequiresPermissions("sys:role:unrelate")
//    @PostMapping("/unrelatePerm")
//    @ResponseBody
//    public Json roleUnrelatePerm(@Valid @RequestBody RolePermDto rolePermDto, BindingResult bindingResult) {
//        String oper="role unrelete perm";
//        log.info("oper:{} body:{} ",oper,rolePermDto);
//        if (bindingResult.hasErrors()) {
//            log.info(bindingResult.getFieldError().toString());
//            return Json.fail(bindingResult.getFieldError().toString());
//        }
//        Long roleId=rolePermDto.getRoleId();
//        Long[] permIds = rolePermDto.getPermIds();
//
//        //查找是否已经存在关联
//        QueryWrapper<SysRolePerm> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("role_id",roleId);
//        List<SysRolePerm> list =sysRolePermService.selectList(queryWrapper);
//        SysRolePerm sysRolePerm=new SysRolePerm();
//        if(list.size()!=0){
//            //用户组与角色一对多关系，先删除再添加
//            sysRolePermService.delete(queryWrapper);
//        }
//        return Json.fail("取消关联失败");
//    }
}

