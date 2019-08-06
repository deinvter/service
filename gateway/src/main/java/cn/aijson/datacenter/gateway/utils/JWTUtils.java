package cn.aijson.datacenter.gateway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


//    正常Token：Token未过期，且未达到建议更换时间。
//    濒死Token：Token未过期，已达到建议更换时间。
//    正常过期Token：Token已过期，但存在于缓存中。
//    非正常过期Token：Token已过期，不存在于缓存中。
@Slf4j
@Component
public class JWTUtils {
    //密钥
    private static final String secret="jwtTokenSecret";
    //30分钟过期
    private static final int expireTime=30;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 生成token
     * @param userId
     * @return token
     */
    public static String buildToken(String userId){

        Date nowDate = new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MINUTE,expireTime);
        Date expireDate=calendar.getTime();
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token= JWT.create().withHeader(map)//Header
                         .withIssuedAt(nowDate)
                         .withExpiresAt(expireDate)
                         .withClaim("userId",userId)//Payload
                         //.withClaim("username",username).withClaim("password",password)
                         .sign(algorithm);//Signature
        return token;
    }
    /**
     * 判断是否更换token
     * 根据时间决定是否更换token
     * TRUE：更换
     * @param token
     * @return
     */
    public static boolean isChangeToken(String token) throws TokenExpiredException{
        DecodedJWT jwt=null;
        boolean changeFlag;
            Algorithm algorithm = Algorithm.HMAC256(secret);
           JWTVerifier verifier = JWT.require(algorithm).build(); //Reusable verifier instance
           jwt = verifier.verify(token);
           Date expireDate= jwt.getExpiresAt();
           Calendar calendar=Calendar.getInstance();
           calendar.add(Calendar.MINUTE,5);
           Date changeDate=calendar.getTime();
           changeFlag=expireDate.before(changeDate);

        return changeFlag;
    }

    /**
     * 判断token是否合法
     * @param token
     * @return
     */
    public  String checkToken(String token) throws NullPointerException{

        boolean expiredFlag=tokenIsExpired(token);
        String userId=null;

            if(expiredFlag){
                userId=stringRedisTemplate.opsForValue().get(token);
            }else {
                userId = getUserIdByToken(token);
            }

        if(userId!=null&&!"".equals(userId)){
            return userId;
        }
        return "";
    }
    /**
     * 判断token是否过期
     * TRUE：过期
     * @param token
     * @return
     */
    public static boolean tokenIsExpired(String token) throws TokenExpiredException,NullPointerException{
        DecodedJWT jwt=null;
        boolean expiredFlag=true;

            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build(); //Reusable verifier instance
            jwt = verifier.verify(token);
            Date expireDate= jwt.getExpiresAt();
            expiredFlag=expireDate.before(new Date());
//
//        catch (TokenExpiredException e){
//            log.info(e.toString());
//            return true;
//        }catch (Exception e){
//            log.info(e.toString());
//            return true;
//        }
        return expiredFlag;
    }
    /**
     * 生成新token时，初始化token
     * @param userId
     * @param newToken
     */
    public  void initNewToken(String userId, String newToken){

        String oldToken= stringRedisTemplate.opsForValue().get(userId+"_token");
        if(oldToken!=null&&!"".equals(oldToken)){
            stringRedisTemplate.opsForValue().set(oldToken,userId,5,TimeUnit.MINUTES);
        }
        stringRedisTemplate.opsForValue().set(newToken,userId);
        stringRedisTemplate.opsForValue().set(userId+"_token",newToken);
    }
    /**
     * 获取可用的token
     * @param userId
     * @return
     */
    public  String getToken(String userId)  {
        String token =stringRedisTemplate.opsForValue().get(userId+"_token");

        boolean changeFlag=isChangeToken(token);
        if(!changeFlag){
            return token;
        }
        String newToken=buildToken(userId);
        initNewToken(userId,newToken);
        return newToken;
    }

    /**
     * 获取用户信息
     * @param token
     * @return userId
     */
    public static String getUserIdByToken(String token) throws TokenExpiredException,NullPointerException{
        String userId=null;

        DecodedJWT jwt=null;

            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build(); //Reusable verifier instance
            jwt = verifier.verify(token);
             userId=jwt.getClaim("userId").asString();

//        } catch (Exception exception){
//            //Invalid signature/claims
//            log.info(exception.toString());
//            return "";
//        }
        return userId;
    }




    public static void main(String[] args) {
        String token =buildToken("1234");
        System.out.println(token);

        try {
            System.out.println(getUserIdByToken(token));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
