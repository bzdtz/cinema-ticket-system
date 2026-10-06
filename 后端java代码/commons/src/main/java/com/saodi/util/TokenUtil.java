package com.saodi.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    /**
     * token过期时间
     */
    private static final long EXPIRE_TIME = 30 * 60 * 1000;
    /**
     * token秘钥
     */
    private static final String SECRET = "saodi";
 
 
    /**
     * 生成签名，30分钟过期
     * @param account 账户
     * @return 生成的token
     */
    public static String sign(String account) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("loginAccount", account)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    /**
     * 检验token是否正确
     * @param token 需要校验的token
     * @return 校验是否成功
     */
    public static boolean verify(String token){

        try {
            //设置签名的加密算法：HMAC256

            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String loginAccount = jwt.getClaim("loginAccount").as(String.class);
            System.out.println("验证拿到的账号："+loginAccount);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public  static String decode(String token){

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String loginAccount = jwt.getClaim("loginAccount").as(String.class);
            return loginAccount;
        } catch (Exception e){
            return null;
        }
    }


}