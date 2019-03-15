package com.leimengling.teachingevalutingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by Lei MengLing.
 */
@Configuration
public class CrosConfig {
  private CorsConfiguration buildConfig(){
    CorsConfiguration corsConfiguration= new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    return corsConfiguration;
  }
@Bean
  public CorsFilter corsFilter(){
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**",buildConfig());
    return new CorsFilter(source);
  }
}
