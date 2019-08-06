package cn.aijson.datacenter.reconsumer.controller;

import cn.aijson.datacenter.reconsumer.entity.SysUser;
import cn.aijson.datacenter.reconsumer.service.ISysUserService;
import cn.aijson.datacenter.reconsumer.utils.JWTUtils;
import cn.aijson.datacenter.reconsumer.utils.Json;
import cn.aijson.datacenter.reconsumer.utils.Md5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
@CrossOrigin
@Slf4j
public class LoginController {
    @Autowired
    ISysUserService sysUserService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @GetMapping("/login")
    @ResponseBody
    public Json login(@Param("name") String name, @Param("password") String password){
        String oper="login oper";
        log.info("{} body:{}",oper,name);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        SysUser user= (SysUser) sysUserService.selectObj(queryWrapper);
        if(name.equals(user.getName())||Md5.md5Encode(password).equals(user.getPassword())){
            return Json.fail("用户或者密码错误");
        }
        String userId=user.getId().toString();
        String token= JWTUtils.buildToken(userId);

        stringRedisTemplate.opsForValue().set(token,userId);
        stringRedisTemplate.opsForValue().set(userId+"_token",token);

        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        return Json.succ(map);
    }
}
