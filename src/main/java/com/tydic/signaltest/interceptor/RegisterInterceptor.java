package com.tydic.signaltest.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author:         jjq
 * @CreateDate:     2020/2/12 19:34
 * @Version:        1.0
 * @Description:    注册拦截器
 */
@Configuration
public class RegisterInterceptor implements WebMvcConfigurer {


    @Bean
    public HandlerInterceptor getRememberMeInterceptor(){
        return new RememberMeInterceptor();
    }

    @Bean
    public HandlerInterceptor getFormatInterceptor(){
        return new FormatInterceptor();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")

                .allowedHeaders("*")

                .allowedMethods("*")

                .allowedOrigins("*")

                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        registry.addInterceptor(getRememberMeInterceptor())
                .addPathPatterns("/**/userLogin");

        registry.addInterceptor(getFormatInterceptor())
                //拦截的路径
                .addPathPatterns("/**/userLogin");

    }
}
