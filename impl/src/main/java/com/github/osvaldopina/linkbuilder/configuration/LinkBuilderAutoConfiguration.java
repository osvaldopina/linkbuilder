package com.github.osvaldopina.linkbuilder.configuration;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.intercept.controller.AnnotatedLinksMethodBeansPostProcessor;
import com.github.osvaldopina.linkbuilder.annotation.intercept.resource.AnnotatedLinksResourceBeansPostProcessor;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas.LinkAnnotationCreatorRegistryImpl;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas.SpringHateoasLinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderCache;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.annotation.reader.impl.AnnotationReaderRegistryImpl;
import com.github.osvaldopina.linkbuilder.annotation.reader.impl.LinkAnnotationReader;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.expression.springhateoas.ExpressionExecutorImpl;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactory;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;
import com.github.osvaldopina.linkbuilder.extension.impl.LinkBuilderExtensionFactoryRegistryImpl;
import com.github.osvaldopina.linkbuilder.fromcall.selfromcurrentcall.SelfFromCurrentCallBeanPostProcessor;
import com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.SpringHateoasHalLinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.hal.annotation.reader.HalLinkAnnotationReader;
import com.github.osvaldopina.linkbuilder.hal.springhateoas.SpringHateoasHalLinkBuilderExtensionFactoryImpl;
import com.github.osvaldopina.linkbuilder.hal.springhateoas.SpringHateoasHalLinkPropertiesLinkCreator;
import com.github.osvaldopina.linkbuilder.impl.springhateoas.LinksBuilderFactoryImpl;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreator;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreators;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.impl.LinkPropertiesLinkCreatorsImpl;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.impl.SpringHateoasLinkPropertiesLinkCreator;
import com.github.osvaldopina.linkbuilder.linkdestination.LinkDestinationRegistry;
import com.github.osvaldopina.linkbuilder.linkdestination.springhateoas.LinkDestinationRegistryImpl;
import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.resoucemethod.springhateoas.ResourceMethodRegistryImpl;
import com.github.osvaldopina.linkbuilder.template.TemplateRegistry;
import com.github.osvaldopina.linkbuilder.template.generation.TemplateGenerator;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolverRegistry;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.core.PathParameterArgumentResolver;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.core.QueryParameterArgumentResolver;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.core.RequestBodyArgumentResolver;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.springhateoas.PageableArgumentResolver;
import com.github.osvaldopina.linkbuilder.template.generation.springhateoas.TemplateGeneratorImpl;
import com.github.osvaldopina.linkbuilder.template.springhateoas.TemplateRegistryImpl;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.annotation.AnnotationVariableValuesDiscover;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.annotation.impl.AnnotationVariableValuesDiscoverImpl;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.MethodCallVariableValuesDiscover;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.impl.MethodCallVariableValuesDiscoverImpl;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscover;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscoverRegistry;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.core.PathParameterVariableValueDiscover;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.core.QueryParameterVariableValueDiscover;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.core.RequestBodyParameterVariableValueDiscover;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.impl.ParameterValueDiscoverRegistryImpl;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.springhateoas.PageableVariableValueDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotationUriGenerator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.impl.AnnotationUriGeneratorImpl;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.impl.MethodCallUriGeneratorImpl;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.impl.StringHateoasIntrospectionUtilsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@ConditionalOnWebApplication
@Order
public class LinkBuilderAutoConfiguration {

	@Autowired(required = false)
	private LinkBuilderConfigurer customLinkBuilderConfigurer;

	private DefaultLinkBuilderConfigurer defaultLinkBuilderConfigurer = new DefaultLinkBuilderConfigurer();

	@Bean
	@Autowired
	public static BeanPostProcessor annotatedLinksMethodBeansPostProcessor(
			AnnotationReaderRegistry annotationReaderRegistry,
			AnnotationUriGenerator annotationUriGenerator,
			LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry,
			MethodCallUriGenerator methodCallUriGenerator,
			IntrospectionUtils introspectionUtils) {
		return new AnnotatedLinksMethodBeansPostProcessor(
				linkAnnotationCreatorRegistry,
				introspectionUtils,
				annotationReaderRegistry
		);
	}

	@Bean
	@Autowired
	public static BeanPostProcessor annotatedLinksResourceBeansPostProcesso(
			AnnotationReaderRegistry annotationReaderRegistry,
			AnnotationUriGenerator annotationUriGenerator,
			LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry,
			MethodCallUriGenerator methodCallUriGenerator,
			IntrospectionUtils introspectionUtils) {
		return new AnnotatedLinksResourceBeansPostProcessor(
				annotationReaderRegistry,
				linkAnnotationCreatorRegistry,
				introspectionUtils
		);
	}

	@Bean
	@Autowired
	public static BeanPostProcessor currentCallBeanPostProcessor(MethodCallUriGenerator methodCallUriGenerator,
																 IntrospectionUtils introspectionUtils) {
		return new SelfFromCurrentCallBeanPostProcessor(methodCallUriGenerator, introspectionUtils);
	}


	@Bean
	@Autowired
	 LinksBuilderFactory linksBuilderFactory(
			LinkPropertiesLinkCreators linkPropertiesLinkCreators,
			LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry) {
		return new LinksBuilderFactoryImpl(linkPropertiesLinkCreators, linkBuilderExtensionFactoryRegistry);
	}

	@Bean
	@Autowired
	public LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry(List<LinkBuilderExtensionFactory> linkBuilderExtensionFactories) {
		return new LinkBuilderExtensionFactoryRegistryImpl(linkBuilderExtensionFactories);
	}

	@Bean
	public LinkBuilderExtensionFactory springHateoasHalLinkBuilderExtensionFactory() {
		return new SpringHateoasHalLinkBuilderExtensionFactoryImpl();
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
		return new ExpressionExecutorImpl();
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
	@Autowired
	public LinkDestinationRegistry destinationRegistry(ResourceMethodRegistry resourceMethodRegistry,
													   IntrospectionUtils introspectionUtils) {
		return new LinkDestinationRegistryImpl(resourceMethodRegistry, introspectionUtils);
	}

	@Bean
	@Autowired
	public ResourceMethodRegistry methodRegistry(@Lazy RequestMappingHandlerMapping handlerMapping,
												 IntrospectionUtils introspectionUtils) {
		return new ResourceMethodRegistryImpl(handlerMapping, introspectionUtils);
	}


	@Bean
	@Autowired
	public MethodCallVariableValuesDiscover methodCallVariableValuesDiscover(
			TemplateRegistry templateRegistry,
			ParameterVariableValueDiscoverRegistry parameterVariableValueDiscoverRegistry) {
		return new MethodCallVariableValuesDiscoverImpl(templateRegistry, parameterVariableValueDiscoverRegistry);
	}

	@Bean
	@Autowired
	public ParameterVariableValueDiscoverRegistry parameterVariableValueDiscoverRegistry(
			List<ParameterVariableValueDiscover> parameterVariableValueDiscovers) {
		return new ParameterValueDiscoverRegistryImpl(parameterVariableValueDiscovers);
	}

	@Bean
	@Autowired
	public TemplateRegistry templateRegistry(ResourceMethodRegistry resourceMethodRegistry, TemplateGenerator templateGenerator) {
		return new TemplateRegistryImpl(resourceMethodRegistry, templateGenerator);
	}

	@Bean
	@Autowired
	public TemplateGenerator templateGenerator(ArgumentResolverRegistry argumentResolverRegistry) {
		return new TemplateGeneratorImpl(argumentResolverRegistry);
	}

	@Bean
	@Autowired
	public PathParameterArgumentResolver pathParameterArgumentResolver(IntrospectionUtils introspectionUtils) {
		return new PathParameterArgumentResolver(introspectionUtils);
	}

	@Bean
	@Autowired
	public QueryParameterArgumentResolver queryParameterArgumentResolver(IntrospectionUtils introspectionUtils) {
		return new QueryParameterArgumentResolver(introspectionUtils);
	}

	@Bean
	@Autowired
	public RequestBodyArgumentResolver requestBodyArgumentResolver(IntrospectionUtils introspectionUtils) {
		return new RequestBodyArgumentResolver(introspectionUtils);
	}

	@Bean
	public PageableArgumentResolver pageableArgumentResolver() {
		return new PageableArgumentResolver();
	}

	@Bean
	@Autowired
	public PathParameterVariableValueDiscover pathParameterVariableValueDiscover(IntrospectionUtils introspectionUtils) {
		return new PathParameterVariableValueDiscover(introspectionUtils);
	}

	@Bean
	@Autowired
	public QueryParameterVariableValueDiscover queryParameterVariableValueDiscover(IntrospectionUtils introspectionUtils) {
		return new QueryParameterVariableValueDiscover(introspectionUtils);
	}

	@Bean
	@Autowired
	RequestBodyParameterVariableValueDiscover requestBodyParameterVariableValueDiscover(IntrospectionUtils introspectionUtils) {
		return new RequestBodyParameterVariableValueDiscover(introspectionUtils);
	}

	@Bean
	PageableVariableValueDiscover pageableVariableValueDiscover() {
		return new PageableVariableValueDiscover();
	}

	@Bean
	@Autowired
	public ArgumentResolverRegistry argumentResolverRegistry(List<ArgumentResolver> argumentResolvers) {
		return new ArgumentResolverRegistry(argumentResolvers);
	}

	@Bean
	@Autowired
	public MethodCallUriGenerator linkUriGenerator(TemplateRegistry templateRegistry,
												   BaseUriDiscover baseUriDiscover,
												   MethodCallVariableValuesDiscover methodCallVariableValuesDiscover) {
		return new MethodCallUriGeneratorImpl(templateRegistry, baseUriDiscover, methodCallVariableValuesDiscover);
	}

	@Bean
	public IntrospectionUtils introspectionUtils() {
		return new StringHateoasIntrospectionUtilsImpl();
	}

	@Bean
	@Autowired
	public LinkPropertiesLinkCreator linkCreator(MethodCallUriGenerator methodCallUriGenerator,
												 ExpressionExecutor expressionExecutor,
												 IntrospectionUtils introspectionUtils) {
		return new SpringHateoasLinkPropertiesLinkCreator(methodCallUriGenerator, expressionExecutor,
				introspectionUtils);
	}

	@Bean
	@Autowired
	public LinkPropertiesLinkCreators linkCreators(List<LinkPropertiesLinkCreator> linkPropertiesLinkCreators) {
		return new LinkPropertiesLinkCreatorsImpl(linkPropertiesLinkCreators);
	}

	@Bean
	@Autowired
	public SpringHateoasHalLinkPropertiesLinkCreator springHateoasLinkPropertiesLinkCreator(
			MethodCallUriGenerator methodCallUriGenerator,
			ExpressionExecutor expressionExecutor,
			IntrospectionUtils introspectionUtils) {
		return new SpringHateoasHalLinkPropertiesLinkCreator(
				methodCallUriGenerator,
				expressionExecutor,
				introspectionUtils
		);
	}

	@Bean
	@Autowired
	public LinkAnnotationCreator annotatedLinkCreator(BaseUriDiscover baseUriDiscover,
													  AnnotationUriGenerator annotationUriGenerator,
													  IntrospectionUtils introspectionUtils,
													  MethodCallUriGenerator methodCallUriGenerator,
													  LinkAnnotationReader linkAnnotationReader) {
		return new SpringHateoasLinkAnnotationCreator(baseUriDiscover, annotationUriGenerator,
				introspectionUtils, methodCallUriGenerator,
				linkAnnotationReader);
	}

	@Bean
	@Autowired
	public AnnotationVariableValuesDiscover annotationVariableValuesDiscover(ExpressionExecutor expressionExecutor) {
		return new AnnotationVariableValuesDiscoverImpl(expressionExecutor);
	}

	@Bean
	@Autowired
	public AnnotationReaderRegistry annotationReaderRegistry(List<AnnotationReader> annotationReaders) {
		return new AnnotationReaderRegistryImpl(annotationReaders);
	}

	@Bean
	@Autowired
	LinkAnnotationCreatorRegistry linkAnnotationPropertiesLinkCreators(
			List<LinkAnnotationCreator> linkAnnotationCreators) {
		return new LinkAnnotationCreatorRegistryImpl(linkAnnotationCreators);
	}

	@Bean
	@Autowired
	public LinkAnnotationReader linkAnnotationReader(IntrospectionUtils introspectionUtils) {
		return new LinkAnnotationReader(introspectionUtils);
	}

	@Bean
	@Autowired
	public AnnotationUriGenerator annotationUriGenerator(LinkDestinationRegistry linkDestinationRegistry,
														 TemplateRegistry templateRegistry,
														 AnnotationVariableValuesDiscover annotationVariableValuesDiscover,
														 BaseUriDiscover baseUriDiscover) {
		return new AnnotationUriGeneratorImpl(linkDestinationRegistry, templateRegistry,
				annotationVariableValuesDiscover,baseUriDiscover);
	}


	@Bean
	@Autowired
	public SpringHateoasHalLinkAnnotationCreator springHateoasHalLinkAnnotationPropertiesLinkCreator(
			BaseUriDiscover baseUriDiscover,
			AnnotationUriGenerator annotationUriGenerator,
			IntrospectionUtils introspectionUtils,
			MethodCallUriGenerator methodCallUriGenerator,
			HalLinkAnnotationReader halLinkAnnotationReader,
			ObjectMapper objectMapper) {
		return new SpringHateoasHalLinkAnnotationCreator(annotationUriGenerator,
				introspectionUtils, methodCallUriGenerator,new AnnotationReaderCache(halLinkAnnotationReader), objectMapper);
	}

	@Bean
	public AnnotationReader halLinkAnnotationReader() {
		return new HalLinkAnnotationReader();
	}
}

