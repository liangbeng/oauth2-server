package org.wzp.oauth2.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.wzp.oauth2.util.Result;
import org.wzp.oauth2.enumeration.ResultCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义token异常信息
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        if (request.getRequestURI().indexOf("loginOut") != -1) {
            response.getWriter().write(JSONObject.toJSONString(Result.ok()));
        } else {
            response.getWriter().write(JSONObject.toJSONString(Result.error(ResultCodeEnum.UNAUTHORIZED)));
        }
    }
}
