package com.github.osvaldopina.linkbuilder.example.hal;


import com.github.osvaldopina.linkbuilder.configuration.CustomLinkBuilderConfigurer;
import com.github.osvaldopina.linkbuilder.methodtemplate.templategenerator.MethodTemplateGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@Configuration
@Import(WebSecurityConfig.class)
public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

}
