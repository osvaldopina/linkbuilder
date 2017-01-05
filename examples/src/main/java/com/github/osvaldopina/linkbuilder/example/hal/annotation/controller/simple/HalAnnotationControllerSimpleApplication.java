package com.github.osvaldopina.linkbuilder.example.hal.annotation.controller.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@Configuration
public class HalAnnotationControllerSimpleApplication extends WebSecurityConfigurerAdapter {


    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(HalAnnotationControllerSimpleApplication.class, args);
    }






}