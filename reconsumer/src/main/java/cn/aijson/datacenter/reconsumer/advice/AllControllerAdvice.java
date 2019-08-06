package cn.aijson.datacenter.reconsumer.advice;

import cn.aijson.datacenter.reconsumer.constant.ResultConstant;
import cn.aijson.datacenter.reconsumer.utils.Json;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AllControllerAdvice {
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Json unAuthorizationException(){
        return Json.fail(ResultConstant.USER_NO_PERMITION);
    }
}
