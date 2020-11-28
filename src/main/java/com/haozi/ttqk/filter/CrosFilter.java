package com.haozi.ttqk.filter;


import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chengchen
 * @title: CrosFilter
 * @projectName zhihuishu-online-service
 * @description: TODO
 * @date 2019-06-04 20:47
 */
@Slf4j
public class CrosFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 如果是跨域的话,xhr请求头会带一个origin
        String origin = request.getHeader("origin");
        if (StringUtils.isNotEmpty(origin)){
            response.addHeader("Access-Control-Allow-Origin", origin);
        }else {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        log.info("CrosFilter  {}" ,origin);
       /* if (StringUtils.isEmpty(origin) || "null".equals(origin)) {
            origin = "";
        }*/
        response.addHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Allow-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        // response.addHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        response.addHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token, content-type,Content-Type,X-Requested-With");
        //response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Access-Control-Allow-Headers");
        log.info("getRequestURI  {}" ,request.getRequestURI());

        chain.doFilter(request, response);
    }
}

