package com.tydic.signaltest.interceptor;

import com.tydic.signaltest.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:         jjq
 * @CreateDate:     2020/2/12 19:33
 * @Version:        1.0
 * @Description:    拦截请求，判断登录的phone和password是否符合格式
 */
public class CustomInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ModelAndView modelAndView=new ModelAndView();
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        if(StringUtils.isBlank(phone)|| CommonUtils.checkPhone(phone)){
            modelAndView.addObject("msg","手机号有空格或不正确");
            return false;
        }
        if(StringUtils.isBlank(password)||CommonUtils.checkPassword(password)){
            modelAndView.addObject("msg","密码有空格或格式不正确");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
