package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;
import com.github.osvaldopina.linkbuilder.argumentresolver.basic.PathVariableAnnotationArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.basic.QueryVariableAnnotationArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.basic.RequestBodyAnnotationArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.custom.pageable.PageableArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.custom.pageable.PageableClassIsPresent;
import com.github.osvaldopina.linkbuilder.direct.impl.DirectLinkTargetBeanPostProcessor;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl.CurrentCall;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl.CurrentCallBeanPostProcessor;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl.CurrentCallLocatorImpl;
import com.github.osvaldopina.linkbuilder.linkcreator.LinkCreator;
import com.github.osvaldopina.linkbuilder.linkcreator.LinkCreators;
import com.github.osvaldopina.linkbuilder.linkcreator.impl.LinkCreatorsImpl;
import com.github.osvaldopina.linkbuilder.impl.springhateoas.LinksBuilderFactoryImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.*;
import com.github.osvaldopina.linkbuilder.methodtemplate.linkcreator.impl.SpringHateoasLinkCreator;
import com.github.osvaldopina.linkbuilder.methodtemplate.templategenerator.MethodTemplateGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.impl.SpringLinkAnnotatedMethodUriGeneratorImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.impl.UriTemplateMethodMappingsImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.linkcreator.AnnotatedLinkCreator;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.AnnotatedMethodUriGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.AnnotatedMethodUriGenerators;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.impl.AnnotatedMethodUriGeneratorsImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.impl.StringHateoasIntrospectionUtilsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

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
    public MethodTemplateGenerator templateGenerator() {
        if (customLinkBuilderConfigurer != null) {
            MethodTemplateGenerator methodTemplateGenerator = customLinkBuilderConfigurer.templateGenerator();
            if (methodTemplateGenerator != null) {
                return methodTemplateGenerator;
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
    public MethodCallUriGenerator linkGenerator() {
        if (customLinkBuilderConfigurer != null) {
            MethodCallUriGenerator methodCallUriGenerator = customLinkBuilderConfigurer.linkGenerator();
            if (methodCallUriGenerator != null) {
                return methodCallUriGenerator;
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
        return new StringHateoasIntrospectionUtilsImpl();
    }

    @Bean
    public CurrentCallLocator currentCallLocator() {
        return new CurrentCallLocatorImpl();
    }

    @Bean
    public LinkCreator linkCreator() {
        return new com.github.osvaldopina.linkbuilder.linkcreator.impl.SpringHateoasLinkCreator();
    }

    @Bean
    @Autowired
    public LinkCreators linkCreators(List<LinkCreator> linkCreators) {
        return new LinkCreatorsImpl(linkCreators);
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
    @Autowired
    public AnnotatedLinkCreator annotatedLinkCreator(ExpressionExecutor expressionExecutor) {
        return new SpringHateoasLinkCreator(expressionExecutor);
    }

    @Bean
    @Autowired
    public AnnotatedMethodUriGenerators annotatedMethodUriGenerators(
            List<AnnotatedMethodUriGenerator> annotatedMethodUriGenerators) {
        return new AnnotatedMethodUriGeneratorsImpl(annotatedMethodUriGenerators);
    }

    @Bean
    public SpringLinkAnnotatedMethodUriGeneratorImpl springLinkAnnotatedMethodUriGenerator() {
        return new SpringLinkAnnotatedMethodUriGeneratorImpl();
    }

    @Bean
    public AnnotatedMethodUriGenerator annotatedMethodUriGenerator() {
        return new SpringLinkAnnotatedMethodUriGeneratorImpl();
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

