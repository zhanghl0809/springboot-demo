package com.example.demo.common.interceptor;


import com.example.demo.common.annotations.UseToken;
import com.example.demo.common.annotations.PassToken;
import com.example.demo.common.enums.ResultEnum;
import com.example.demo.common.enums.TokenEnum;
import com.example.demo.common.exception.IpException;
import com.example.demo.common.exception.SysException;
import com.example.demo.common.exception.TokenException;
import com.example.demo.domin.TbUser;
import com.example.demo.request.dto.UserAddDto;
import com.example.demo.service.UserService;
import com.example.demo.utils.IPUtils;
import com.example.demo.utils.RedisUtil;
import com.example.demo.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SysInterceptor implements HandlerInterceptor {

    private final static String ACCESS_TOKEN = "access_token";

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("In SysInterceptor...");
//        this.ipCheck(request);
        return this.tokenCheck(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("Out SysInterceptor...");
    }


    private void ipCheck(HttpServletRequest request) {
        // ip拦截
        String blackIpKey = "black_list:ip";
        String ip = IPUtils.getRealIP(request);
        String ipList = redisUtil.get(blackIpKey).toString();
        List<String> blackIpList = Arrays.asList(ipList.split(","));

        if (blackIpList.contains(ip)) {
            throw new IpException(ResultEnum.IP_LIMIT, "黑名单，请勿恶意请求");
        } else {
            String key = "limit_control:ip" + ip;
            Long incr = redisUtil.setIncr(key);
            if (incr >= 5L) {
                redisUtil.deleteKey(blackIpKey);

                List<String> newIpList = new ArrayList<>(blackIpList);
                newIpList.add(ip);
                String blackIpListStr = String.join(",", newIpList);
                redisUtil.set(blackIpKey, blackIpListStr);
                throw new IpException(ResultEnum.IP_LIMIT, "ip限制请勿恶意请求");
            }
        }
    }

    private boolean tokenCheck(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = request.getHeader("access_token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UseToken.class)) {
            UseToken userUseToken = method.getAnnotation(UseToken.class);
            if (userUseToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new TokenException(TokenEnum.TOKEN_NULL, "无token");
                }
                // 判断token是否有效，获取token中的userId
                String result = TokenUtil.verifyToken(token);
                if (TokenEnum.TOKEN_INVALID.getCode().equals(result)) {
                    throw new TokenException(TokenEnum.TOKEN_INVALID, "前后不一致或过期");
                }
                String userId = TokenUtil.getTokenId(token);
                long longUserId = Long.parseLong(userId);
                TbUser user = userService.selectByUserId(longUserId);
                if (Objects.isNull(user)) {
                    throw new TokenException(TokenEnum.TOKEN_INVALID, "用户信息错误");
                }
                log.info("old-token:{}",token);
                String refreshedToken = TokenUtil.refreshToken(token);
                log.info("refreshed-Token:{}",refreshedToken);
                // 有效令牌，重新刷新token，再传回客户端
                response.setHeader(ACCESS_TOKEN, refreshedToken);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        boolean a =  true;
        boolean b =  false;
//        !(auditTypeBl && shopPropertyBl)
        System.out.println(!(a&&b));
    }

}


