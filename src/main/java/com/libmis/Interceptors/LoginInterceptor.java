package com.libmis.Interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public LoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 令牌验证
        // 调用请求头得到令牌
        String token = request.getHeader("Authorization");
            ValueOperations<String, String> opertions = stringRedisTemplate.opsForValue();
            String redisToken = opertions.get("token");
            if (redisToken == null || !redisToken.equals(token)) {
                response.setStatus(401);
                System.out.println("******f*a*l*s*e****");
                // 失败，拦截
                return false;
            }else{
                // 成功，放行
                return true;
            }

    }
}
