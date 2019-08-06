package cn.aijson.datacenter.reconsumer.controller;


import cn.aijson.datacenter.reconsumer.annotation.PermInfo;
import cn.aijson.datacenter.reconsumer.entity.SysPerm;
import cn.aijson.datacenter.reconsumer.enums.PermType;
import cn.aijson.datacenter.reconsumer.service.ISysPermService;
import cn.aijson.datacenter.reconsumer.utils.Json;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author cn.aijson.mart
 * @since 2019-07-04
 */
@PermInfo(pval = "sys:perm",value = "权限模块")
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/sysPerm")
public class SysPermController {
    @Autowired
    private ISysPermService sysPermService;

    @PermInfo(value = "添加权限")
    @RequiresPermissions("sys:perm:add")
    @PostMapping("/add")
    @ResponseBody
    public Json addPerm(@Valid @RequestBody SysPerm sysPerm, BindingResult bindingResult){
        String oper="add perm";
        log.info("oper:{} body:{} ",oper,sysPerm.toString());
        if(bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().toString();
            log.info(msg);
            return Json.fail(msg);
        }
        return Json.result(sysPermService.insert(sysPerm));
    }

    @PermInfo(value = "编辑权限")
    @RequiresPermissions("sys:perm:update")
    @PostMapping("/update")
    @ResponseBody
    public Json updatePerm(@Valid @RequestBody SysPerm sysPerm, BindingResult bindingResult){
        String oper="update perm";
        log.info("oper:{} body:{} ",oper,sysPerm.toString());
        if(bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().toString();
            log.info(msg);
            return Json.fail(msg);
        }
        Long id = sysPerm.getId();
        if(id==null){
            return Json.fail("id不能为空");
        }
        QueryWrapper<SysPerm> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return Json.result(sysPermService.update(sysPerm,queryWrapper));
    }


    @PermInfo(value = "删除权限")
    @RequiresPermissions("sys:perm:delete")
    @PostMapping("/delete")
    @ResponseBody
    public Json deletePerm(@RequestParam("id")long id){
        String oper="delete perm";
        log.info("oper:{} permId:{} ",oper,id);
        return Json.result(sysPermService.deleteById(id));
    }

    @PermInfo(value = "权限列表")
    @RequiresPermissions("sys:perm:list")
    @GetMapping("/list")
    @ResponseBody
    public Json permList(@RequestParam(value = "current",required =false,defaultValue = "1")String current,
                         @RequestParam(value = "size",required = false,defaultValue = "10")String size,
                         @RequestParam(value = "type",required = false)Integer type){
        String oper="perm list";
        log.info("oper:{}  ",oper);
        Page page = new Page(Long.valueOf(current),Long.valueOf(size));
        QueryWrapper<SysPerm> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(type!=null){
            queryWrapper.eq("type",type);
        }
        return Json.succ(sysPermService.selectPage(page,queryWrapper));
    }
    @Autowired
    private ApplicationContext context;

    @GetMapping("/meta/api")
    @ResponseBody
    public Json listApiPermMetadata() {
        String oper = "list api permission metadata";
        log.info(oper);
        final String basicPackage = ClassUtils.getPackageName(this.getClass());
        Map<String, Object> map = context.getBeansWithAnnotation(Controller.class);
        Collection<Object> beans = map.values();
        List<SysPerm> apiList = beans.stream().filter(b -> StringUtils.equals(basicPackage, ClassUtils.getPackageName(b.getClass())))
                .map(bean -> {
                    Class<?> clz = bean.getClass();
                    SysPerm moduleApiPerm = getModulePerm(clz);
                    List<SysPerm> methodApiPerm = getApiPerm(clz, moduleApiPerm.getPval());
                    moduleApiPerm.setChildren(methodApiPerm);
                    return moduleApiPerm;
                }).collect(Collectors.toList());
        System.out.println(apiList.toString());
        return Json.succ(apiList);
    }

    /**
     * 获取控制器上的方法上的注释，生成后台接口权限的信息
     *
     * @param clz
     * @return
     */
    private List<SysPerm> getApiPerm(Class<?> clz, final String parentPval) {
        //获取clz类上有RequiresPermissions注解的所有方法
        List<Method> apiMethods = MethodUtils.getMethodsListWithAnnotation(clz.getSuperclass(), RequiresPermissions.class);
        return apiMethods.stream().map(method -> {
            //pname首选
            //获取method方法上的PermInfo注解的元数据
            PermInfo piAnno = AnnotationUtils.getAnnotation(method, PermInfo.class);
            String pnamePrimary = piAnno!=null?piAnno.value():null;
            //pname备选
            String pnameSub = method.getName();
            //pval值
            //获取method方法上的RequiresPermissions注解的元数据
            RequiresPermissions rpAnno = AnnotationUtils.getAnnotation(method, RequiresPermissions.class);
            SysPerm perm = new SysPerm();
            if (StringUtils.isNotBlank(pnamePrimary)){
                perm.setName(pnamePrimary);
            }else{
                perm.setName(pnameSub);
            }
            perm.setParent(parentPval);
            perm.setType(PermType.API);
            perm.setPval(rpAnno.value()[0]);
            return perm;
        }).collect(Collectors.toList());
    }

    /**
     * 获取控制器上的注释，生成后台接口模块权限的信息
     *
     * @param clz
     * @return
     */
    public SysPerm getModulePerm(Class<?> clz) {
        SysPerm perm = new SysPerm();
        //首选值
        PermInfo piAnno = AnnotationUtils.getAnnotation(clz, PermInfo.class);
        if (piAnno == null) {
            //由于使用了RequiresPermissions注解的类在运行时会使用动态代理，即clz在运行时是一个动态代理，所以需要getSuperClass获取实际的类型
            piAnno = AnnotationUtils.getAnnotation(clz.getSuperclass(), PermInfo.class);
        }
        String pnamePrimary = null;
        String pvalPrimary = null;
        String pvalPrimary2 = null;
        if (piAnno != null && piAnno.value() != null) {
            pnamePrimary = piAnno.value();
            pvalPrimary = piAnno.pval();
        }

        //备选值1
        RequiresPermissions rpAnno = AnnotationUtils.getAnnotation(clz, RequiresPermissions.class);
        if (rpAnno == null) {
            rpAnno = AnnotationUtils.getAnnotation(clz.getSuperclass(), RequiresPermissions.class);
        }
        if (rpAnno != null) {
            pvalPrimary2 = rpAnno.value()[0];
        }

//        //备选值2
//        String pnameSub = ClassUtils.getShortName(clz);
//        RequestMapping rmAnno = AnnotationUtils.getAnnotation(clz, RequestMapping.class);
//        if (rmAnno == null) {
//            rmAnno = AnnotationUtils.getAnnotation(clz.getSuperclass(), RequestMapping.class);
//        }
//        String pvalSub = rmAnno.value()[0];
        //赋值
        if (StringUtils.isNotBlank(pnamePrimary)) {
            perm.setName(pnamePrimary);
            perm.setType(PermType.API);
        }
//        else {
//            perm.setName(pnameSub);
//
//        }
        if (StringUtils.isNotBlank(pvalPrimary)) {
            perm.setPval(pvalPrimary);
        }else if(StringUtils.isNotBlank(pvalPrimary2)){
            perm.setPval(pvalPrimary2);
        }
//        else {
//            perm.setPval("a:"+pvalSub.substring(1).replace("/",":"));
//        }

        return perm;
    }


}

