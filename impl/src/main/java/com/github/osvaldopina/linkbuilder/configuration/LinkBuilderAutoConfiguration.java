package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;
import com.github.osvaldopina.linkbuilder.argumentresolver.basic.PathVariableAnnotationArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.basic.QueryVariableAnnotationArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.basic.RequestBodyAnnotationArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.custom.pageable.PageableArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.custom.pageable.PageableClassIsPresent;
import com.github.osvaldopina.linkbuilder.direct.DirectLinkTargetBeanPostProcessor;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCall;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallBeanPostProcessor;
import com.github.osvaldopina.linkbuilder.impl.LinksBuilderFactoryImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.LinkGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.TemplateGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.UriTemplateMethodMappings;
import com.github.osvaldopina.linkbuilder.methodtemplate.impl.UriTemplateMethodMappingsImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.impl.IntrospectionUtilsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LinkBuilderAutoConfiguration {

    @Autowired(required = false)
    private LinkBuilderConfigurer customLinkBuilderConfigurer;

    private DefaultLinkBuilderConfigurer defaultLinkBuilderConfigurer = new DefaultLinkBuilderConfigurer();

    @Bean
    public LinksBuilderFactory linksBuilderFactory() {
        return new LinksBuilderFactoryImpl();
    }

    @Bean
    public UriTemplateMethodMappings uriTemplateMethodMappings() {
        return new UriTemplateMethodMappingsImpl();
    }

    @Bean
    public TemplateGenerator templateGenerator() {
        if (customLinkBuilderConfigurer != null) {
            TemplateGenerator templateGenerator = customLinkBuilderConfigurer.templateGenerator();
            if (templateGenerator != null) {
                return templateGenerator;
            }
        }
        return defaultLinkBuilderConfigurer.templateGenerator();
    }

    @Bean
    public RequestPartsFactoryList requestPartsFactoryList() {
        if (customLinkBuilderConfigurer != null) {
            RequestPartsFactoryList requestPartsFactoryList = customLinkBuilderConfigurer.requestPartsFactoryList();
            if (requestPartsFactoryList != null) {
                return requestPartsFactoryList;
            }
        }
        return defaultLinkBuilderConfigurer.requestPartsFactoryList();
    }

    @Bean
    public ExpressionExecutor spelExecutor() {
        if (customLinkBuilderConfigurer != null) {
            ExpressionExecutor expressionExecutor = customLinkBuilderConfigurer.spelExecutor();
            if (expressionExecutor != null) {
                return expressionExecutor;
            }
        }
        return defaultLinkBuilderConfigurer.spelExecutor();
    }

    @Bean
    public BaseUriDiscover baseUriDiscover() {
        if (customLinkBuilderConfigurer != null) {
            BaseUriDiscover baseUriDiscover = customLinkBuilderConfigurer.baseUriDiscover();
            if (baseUriDiscover != null) {
                return baseUriDiscover;
            }
        }
        return defaultLinkBuilderConfigurer.baseUriDiscover();
    }

    @Bean
    public LinkGenerator linkGenerator() {
        if (customLinkBuilderConfigurer != null) {
            LinkGenerator linkGenerator = customLinkBuilderConfigurer.linkGenerator();
            if (linkGenerator != null) {
                return linkGenerator;
            }
        }
        return defaultLinkBuilderConfigurer.linkGenerator();
    }

    @Bean
    public CurrentCall currentCall() {
        return new CurrentCall();
    }


    @Bean
    public IntrospectionUtils introspectionUtils() {
        return new IntrospectionUtilsImpl();
    }


    @Bean
    @Autowired
    public QueryVariableAnnotationArgumentResolver requestParamAnnotationArgumentResolver(IntrospectionUtils introspectionUtils) {
        return new QueryVariableAnnotationArgumentResolver(introspectionUtils);
    }

    @Bean
    @Autowired
    public RequestBodyAnnotationArgumentResolver requestBodyAnnotationArgumentResolver(IntrospectionUtils introspectionUtils) {
        return new RequestBodyAnnotationArgumentResolver(introspectionUtils);
    }

    @Bean
    @Autowired
    public PathVariableAnnotationArgumentResolver pathVariableAnnotationArgumentResolver(IntrospectionUtils introspectionUtils) {
        return new PathVariableAnnotationArgumentResolver(introspectionUtils);
    }


    @Bean
    public ArgumentResolvers argumentResolvers() {
        return new ArgumentResolvers();
    }

    @Bean
    @Conditional(PageableClassIsPresent.class)
    public PageableArgumentResolver pageableArgumentResolver() {
        return new PageableArgumentResolver();
    }

    @Bean
    public static BeanPostProcessor directLinkTargetBeanPostProcessor() {
        return new DirectLinkTargetBeanPostProcessor();
    }

    @Bean
    public static BeanPostProcessor currentCallBeanPostProcessor() {
        return new CurrentCallBeanPostProcessor();
    }


}

