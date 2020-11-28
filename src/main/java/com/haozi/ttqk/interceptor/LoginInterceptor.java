package com.haozi.ttqk.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.haozi.ttqk.service.UserService;
import com.haozi.ttqk.util.ResponseUtil;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yang on 2018/7/23.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;


    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        String token=request.getHeader("token");
        String userId=request.getHeader("userId");

        LOGGER.info("uri = {},userId={}, token = {}", uri, userId, token);

        if(!StringUtils.isEmpty(uri) && uri.indexOf("login")!=-1){
            return true;
        }
        Integer userIdInteger=null;
        if(!StringUtils.isEmpty(userId)){
            try {
                userIdInteger= Integer.parseInt(userId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
//        if(StringUtils.isEmpty(userId) || userIdInteger==null){
//            response.setHeader("Content-type", "text/html;charset=UTF-8");  //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
//            response.setCharacterEncoding("UTF-8");
//            response.getWriter().write(JSONObject.toJSONString(ResponseUtil.fail("登录已过期，请重新登录")));
//            return false;
//        }
//        Integer result=userService.checkToken(userIdInteger,token);
//        if(result==null || result!=1){
//            response.setHeader("Content-type", "text/html;charset=UTF-8");  //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
//            response.setCharacterEncoding("UTF-8");
//            response.getWriter().write(JSONObject.toJSONString(ResponseUtil.fail("登录已过期，请重新登录")));
//            return false;
//        }
        return true;

        }
}
