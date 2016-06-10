package com.github.osvaldopina.linkbuilder.direct;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.annotation.Link;
import com.github.osvaldopina.linkbuilder.annotation.LinkParam;
import com.github.osvaldopina.linkbuilder.methodtemplate.UriTemplateMethodMappings;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AddLinksToResourceMethodInterceptor implements AfterReturningAdvice {

    private ApplicationContext applicationContext;

    public AddLinksToResourceMethodInterceptor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        Link link = method.getAnnotation(Link.class);

        ResourceSupport resourceSupport = (ResourceSupport) returnValue;

        List<org.springframework.hateoas.Link> links = new ArrayList<org.springframework.hateoas.Link>();

        if (link != null) {
            Class<?> destination = link.destination();
            String targetLink = link.target();

            UriTemplateMethodMappings uriTemplateMethodMappingsImpl =
                    applicationContext.getBean(UriTemplateMethodMappings.class);

            UriTemplate template = uriTemplateMethodMappingsImpl.createNewTemplateForLinkTarget(destination, targetLink);

            for(LinkParam linkParam: link.params()) {
                template.set(linkParam.name(), linkParam.value());
            }

            links.add(new org.springframework.hateoas.Link(link.templated() ? template.expandPartial() : template.expand(), link.relation()));
            resourceSupport.add(links);

        }
    }

    private HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
        return servletRequest;
    }

}
