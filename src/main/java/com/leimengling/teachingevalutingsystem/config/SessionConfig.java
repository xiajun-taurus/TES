package com.leimengling.teachingevalutingsystem.config;

import com.leimengling.teachingevalutingsystem.config.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Created by Lei MengLing.
 */
@Configuration
public class SessionConfig implements WebMvcConfigurer {

  @Autowired
  LoginInterceptor loginInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login")
        .excludePathPatterns("/validate")
        .excludePathPatterns("/find-password")
        .excludePathPatterns("/password-protect")
        .excludePathPatterns("/getPassWord")
        .excludePathPatterns("/css/**")
        .excludePathPatterns("/assets/**")
        .excludePathPatterns("/fonts/**").excludePathPatterns("/img/**")
        .excludePathPatterns("/js/**");
  }
}
