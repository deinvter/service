package cn.aijson.datacenter.reconsumer.shiro;

import cn.aijson.datacenter.reconsumer.entity.SysPerm;
import cn.aijson.datacenter.reconsumer.entity.SysUser;
import cn.aijson.datacenter.reconsumer.jwt.JWTToken;
import cn.aijson.datacenter.reconsumer.service.ISysPermService;
import cn.aijson.datacenter.reconsumer.service.ISysUserService;
import cn.aijson.datacenter.reconsumer.utils.JWTUtils;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class PermRealm extends AuthorizingRealm {
    Logger logger= LoggerFactory.getLogger(PermRealm.class);
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    ISysUserService sysUserService;
    @Autowired
    ISysPermService sysPermService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    //鉴权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String userId = JWTUtils.getUserIdByToken(principalCollection.toString());
        List<Long> permIds=sysUserService.queryPermsByUserId(Long.valueOf(userId));
        List<SysPerm> perms =new ArrayList<>();
        for(Long permId:permIds){
           SysPerm perm= sysPermService.selectById(permId);
           perms.add(perm);
        }

        info.setStringPermissions(perms.stream().map(SysPerm::getPval).collect(Collectors.toSet()));

        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token=(String)authenticationToken.getCredentials();
         String userId= null;
        try {
            //检查是否有其他用户登录
            if(!JWTUtils.tokenIsExpired(token)){
                logger.info("检查是否有其他用户登录");
                userId= JWTUtils.getUserIdByToken(token);
                String redisToken= stringRedisTemplate.opsForValue().get(userId+"_token");
                if(!token.equals(redisToken)){
                    logger.info("另一用户登录");
                    throw new AuthenticationException("账号被登录");
                }

            }
        }catch (TokenExpiredException e) {
            logger.info("token is expired");
            throw new AuthenticationException("token已过期");
        }catch (NullPointerException e){
            logger.info("token is null");
            throw new AuthenticationException("token为空");
        }catch (Exception e){
            logger.info(e.toString());
            throw new AuthenticationException("token验证失败");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");

    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        String userId = JWTUtils.getUserIdByToken(principals.toString());
        SysUser user= sysUserService.selectById(Long.valueOf(userId));
        String name=user.getName();
        return name.equals("admin")|| super.isPermitted(principals, permission);
    }
}
