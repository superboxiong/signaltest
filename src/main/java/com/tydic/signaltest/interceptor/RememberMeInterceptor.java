package com.tydic.signaltest.interceptor;

import com.tydic.signaltest.mapper.RememberMeMapper;
import com.tydic.signaltest.model.RememberUser;
import com.tydic.signaltest.model.SystemUser;
import com.tydic.signaltest.service.LoginService;
import com.tydic.signaltest.utils.MD5;
import com.tydic.signaltest.utils.MessageInfo;
import com.tydic.signaltest.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.Inet4Address;
import java.net.InetAddress;

@Component
public class RememberMeInterceptor implements HandlerInterceptor {


    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        String phone = request.getParameter("phone");
        String flag = request.getParameter("flag");
        System.out.println(flag);
        if("true".equals(flag)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null){
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    if (name.equals(phone)) {
                        //调用mapper，查看数据库是否有记录，如果有，查看是否过期
                        String value = cookie.getValue();
                        RememberUser rememberMe = loginService.getRememberMe(value);
                        if (rememberMe != null) {
                            long created = rememberMe.getCreated();
                            long expires = rememberMe.getExpires();
                            long nowTime = System.currentTimeMillis() / 1000;
                            if ((created + expires) > nowTime) {//判断验证信息是否过期
                                //request.getRequestDispatcher("/").forward(request, response);
                                System.out.println("---1");
                                return false;
                            } else {
                                System.out.println("---2");
                                return true;
                            }
                        }
                        System.out.println("---3");
                        return true;
                    }
                    System.out.println("---4");
                    return true;
                }
             }
            System.out.println("---5");
            return true;
        }
        System.out.println("---6");
        return true;

    }





}
