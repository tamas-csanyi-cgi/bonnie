package com.cgi.bonnie.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = {"com.cgi.bonnie.bonnierest"})
@EnableWebSecurity
public class BonnieRestConfiguration implements WebMvcConfigurer {

    @Value("${bonnie.authentication.cors.allowed.origins}")
    private String corsAllowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("**** 2 ******" + Arrays.asList(corsAllowedOrigins.split(",")));
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins(corsAllowedOrigins.split(","))
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("authorization", "content-type", "x-auth-token")
                .exposedHeaders( "x-auth-token");
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Configuration
    @Order(101)
    public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);
            http.antMatcher("/h2").anonymous();
        }

    }

}
