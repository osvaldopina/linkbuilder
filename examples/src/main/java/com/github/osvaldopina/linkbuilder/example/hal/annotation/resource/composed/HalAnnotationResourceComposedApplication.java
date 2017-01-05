package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HalAnnotationResourceComposedApplication extends WebSecurityConfigurerAdapter {


    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(HalAnnotationResourceComposedApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic().realmName("test");
    }

}