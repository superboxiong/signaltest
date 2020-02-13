package com.tydic.signaltest.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author:         jjq
 * @CreateDate:     2020/2/12 19:34
 * @Version:        1.0
 * @Description:    注册拦截器
 */
public class RegisterInterceptor implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor())
                //拦截的路径
                .addPathPatterns()
                //不拦截的路径
                .excludePathPatterns();
    }
}
