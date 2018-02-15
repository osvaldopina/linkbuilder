package com.github.osvaldopina.linkbuilder.integrationtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class TestApplication {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestApplication.class, args);
    }
}
