package com.github.osvaldopina.linkbuilder.example.extensions.requestpartsfactorylist;


import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.configuration.CustomLinkBuilderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@Configuration
public class Application extends CustomLinkBuilderConfigurer {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

    @Override
    public RequestPartsFactoryList requestPartsFactoryList() {
        return new CustomHeaderRequestPartsFactoryList();
    }
}