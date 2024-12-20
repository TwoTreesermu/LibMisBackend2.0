package com.libmis.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Jwt {
    // 输入userId, userName, 输出Jwt token
    public static String getJwt(String userName, String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userName", userName);
        return JWT.create()
                .withClaim("userInfo", claims)
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*2)) // token两小时有效
                .sign(Algorithm.HMAC256("SpringBootStudy"));
    }

    public static Map<String, Object> verifyToken(String token) {
        Claim jwtMap= JWT.require(Algorithm.HMAC256("SpringBootStudy"))
                .build()
                .verify(token)
                .getClaim("userInfo");// 能够得到所有的载荷
        System.out.println(jwtMap);
        // 用户传递过来的token
        return jwtMap.asMap() ;
        // 如果修改了头部和载荷部分的数据，则验证失败
        // 如果秘钥改了，验证失败
        // token过期，也会失败
    }
}
