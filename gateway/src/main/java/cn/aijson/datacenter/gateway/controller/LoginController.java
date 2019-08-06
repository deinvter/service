package cn.aijson.datacenter.gateway.controller;

import cn.aijson.datacenter.gateway.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class LoginController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/login")
    @ResponseBody
    public String login(String userId){
       String token= JWTUtils.buildToken(userId);
        //一旦登录就将登录信息保存到redis中
           stringRedisTemplate.opsForValue().set(token,userId);
           stringRedisTemplate.opsForValue().set(userId+"_token",token);

        return token;
    }
}
