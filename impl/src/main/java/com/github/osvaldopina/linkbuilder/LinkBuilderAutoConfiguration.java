package com.github.osvaldopina.linkbuilder;

import com.github.osvaldopina.linkbuilder.methodtemplate.UriTemplateMethodMappings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;

/**
 * Created by osvaldopina on 1/18/16.
 */
@Configuration
@ComponentScan("com.github.osvaldopina.linkbuilder")
@EnableAspectJAutoProxy
public class LinkBuilderAutoConfiguration {
}

