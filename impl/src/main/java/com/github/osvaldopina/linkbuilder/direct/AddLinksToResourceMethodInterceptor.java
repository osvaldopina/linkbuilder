package com.github.osvaldopina.linkbuilder.direct;

import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.Link;
import com.github.osvaldopina.linkbuilder.annotation.Links;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCall;
import com.github.osvaldopina.linkbuilder.methodtemplate.LinkGenerator;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class AddLinksToResourceMethodInterceptor implements AfterReturningAdvice {

    private ApplicationContext applicationContext;

    public AddLinksToResourceMethodInterceptor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        if (returnValue != null) {
            LinkGenerator linkGenerator = applicationContext.getBean(LinkGenerator.class);

            createSelfLink(method, linkGenerator, (ResourceSupport) returnValue);

            createAllAnnotatedLinks(method, args, linkGenerator, (ResourceSupport) returnValue);
        }
    }

    private void createAllAnnotatedLinks(Method method, Object[] args, LinkGenerator linkGenerator, ResourceSupport resourceSupport) {
        Links linksAnnotation = method.getAnnotation(Links.class);
        for (Link link : linksAnnotation.value()) {
            resourceSupport.add(linkGenerator.generate(link, resourceSupport, args));
        }
    }

    private void createSelfLink(Method method, LinkGenerator linkGenerator, ResourceSupport resourceSupport) {
        if (method.getAnnotation(EnableSelfFromCurrentCall.class) != null) {
            CurrentCall currentCall = applicationContext.getBean(CurrentCall.class);

            resourceSupport.add(linkGenerator.generate(currentCall.getMethodCall(), false, "self"));
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
