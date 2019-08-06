package cn.aijson.datacenter.gateway.controller;

import cn.aijson.datacenter.gateway.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@Slf4j
@RestController
public class TestController  {
    @Autowired
    private JWTUtils jwtUtils;
    @GetMapping("/get")
    @ResponseBody
    public String get(String token){

        String userId= null;
        try {
            userId = jwtUtils.checkToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(userId!=null&&!"".equals(userId)){
            log.info("进入filter");
            String newToken= null;
            try {
                newToken = jwtUtils.getToken(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return newToken;
        }
        return null;
    }
}
