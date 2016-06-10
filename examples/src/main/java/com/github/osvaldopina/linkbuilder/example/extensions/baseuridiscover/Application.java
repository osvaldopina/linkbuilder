package com.github.osvaldopina.linkbuilder.example.extensions.baseuridiscover;


import com.github.osvaldopina.linkbuilder.configuration.CustomLinkBuilderConfigurer;
import com.github.osvaldopina.linkbuilder.example.WebSecurityConfig;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@Configuration
public class Application extends CustomLinkBuilderConfigurer {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }


    @Override
    public BaseUriDiscover baseUriDiscover() {
        return new FixedUrlBaseUriDiscover();
    }




}