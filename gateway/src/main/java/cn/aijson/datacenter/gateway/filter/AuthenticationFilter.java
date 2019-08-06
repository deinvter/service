package cn.aijson.datacenter.gateway.filter;

import cn.aijson.datacenter.gateway.utils.JWTUtils;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class AuthenticationFilter   implements GlobalFilter, Ordered  {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //从请求头中获取token
        String url= exchange.getRequest().getURI().getPath();
        //过滤掉不需要验证的路径
        if(url.substring(url.lastIndexOf("/")).equals("/login")){
            return chain.filter(exchange);
        }
        System.out.println(url);
        log.info("获取token");
        String token=exchange.getRequest().getHeaders().getFirst("Authorization");
        String userId= null;
        try {
            //检查是否有其他用户登录
            if(!JWTUtils.tokenIsExpired(token)){
                log.info("检查是否有其他用户登录");
                userId=JWTUtils.getUserIdByToken(token);
                String redisToken= stringRedisTemplate.opsForValue().get(userId+"_token");
                if(!token.equals(redisToken)){
                    System.out.println("登出");
                    log.info("另一用户登录");
                    exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
                    return exchange.getResponse().setComplete();
                }
            }
            //检查token是否合法
            userId = jwtUtils.checkToken(token);
            if(userId!=null&&!"".equals(userId)){
                log.info("进入filter");
                String newToken=jwtUtils.getToken(userId);
                HttpHeaders httpHeaders= exchange.getResponse().getHeaders();
                httpHeaders.add("Authorization",newToken);
                return chain.filter(exchange);
            }
        }catch (TokenExpiredException e) {
            log.info("token is expired");
            exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
            return exchange.getResponse().setComplete();
        }catch (NullPointerException e){
            log.info("token is null");
            exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
            return exchange.getResponse().setComplete();
        }catch (Exception e){
            log.info(e.toString());
            exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
            return exchange.getResponse().setComplete();
        }
        log.info("token is null or illegal");
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
    @Override
    public int getOrder() {
        return -100;
    }

}
