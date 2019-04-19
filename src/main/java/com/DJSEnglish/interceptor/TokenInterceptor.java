package com.DJSEnglish.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.DJSEnglish.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TokenInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    // 设置不拦截的路径
    private static final Set<String> IGNORE_URL = new HashSet<String>();

    static {
        String URL[] = new String[]{
                "login.do", "register.do", "test.do", "need_login.do", "forget_reset_password.do"
                , "get_msgcode.do", "check_msg.do"
        };
        for (String s : URL) {
            IGNORE_URL.add(s);
        }
    }


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        boolean flag = false;
        String URI = request.getRequestURI();
        String path = URI.substring(URI.lastIndexOf("/") + 1);
        if(IGNORE_URL.contains(path))
        {
            flag = true;
        }
        if (!flag) {
            String token = request.getHeader("token");
            if (token == null) {
                // 跳转返回未登录
                request.getRequestDispatcher("/user/need_login.do").forward(request, response);
                logger.info("未登录");
            } else {
                int id = 0;
                try {
                    Map<String, Claim> map = JWTUtil.verifyToken(token);
                    id = map.get("id").asInt();
                    request.setAttribute("id", id);
                    return true;
                } catch (Exception e) {
                    request.getRequestDispatcher("/user/need_login.do").forward(request, response);
                    logger.info("登录过期");
                }
            }
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
