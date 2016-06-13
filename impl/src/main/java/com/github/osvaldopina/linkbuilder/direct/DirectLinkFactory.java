package com.github.osvaldopina.linkbuilder.direct;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.annotation.LinkParam;
import com.github.osvaldopina.linkbuilder.methodtemplate.UriTemplateMethodMappings;
import com.github.osvaldopina.linkbuilder.spel.SpelExecutor;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;

public class DirectLinkFactory {

   public  void addLink(com.github.osvaldopina.linkbuilder.annotation.Link linkAnnotation, ResourceSupport methodResult,
                        Object[] methodParams, ApplicationContext applicationContext, Collection<Link> links) {


       if (haveToGenerateLink(linkAnnotation, methodResult, methodParams, applicationContext)) {
           UriTemplate linkTemplate = getTemplateForTarget(linkAnnotation.destination(),
                   linkAnnotation.target(), applicationContext);

            for(LinkParam linkParam: linkAnnotation.params()) {
                linkTemplate.set(linkParam.name(), getParameterValue(linkParam.value(), methodResult,
                        methodParams, applicationContext));
            }

        //   links.add(new org.springframework.hateoas.Link(linkAnnotation.templated() ?  linkTemplatexpandPartial() : template.expand(), link.relation()));

       }

   }

    private Object getParameterValue(String expression, ResourceSupport methodResult, Object[] methodParams, ApplicationContext applicationContext) {
        SpelExecutor spelExecutor = applicationContext.getBean(SpelExecutor.class);
        return spelExecutor.getValue(expression, methodResult, methodParams);
    }

    private UriTemplate getTemplateForTarget(Class<?> destination, String target, ApplicationContext applicationContext) {

        UriTemplateMethodMappings uriTemplateMethodMappingsImpl =
                applicationContext.getBean(UriTemplateMethodMappings.class);

        return uriTemplateMethodMappingsImpl.createNewTemplateForLinkTarget(destination, target);

    }

    private boolean haveToGenerateLink(com.github.osvaldopina.linkbuilder.annotation.Link linkAnnotation, ResourceSupport methodResult,
                                      Object[] methodParams, ApplicationContext applicationContext) {

        if (linkAnnotation.when() != null) {
            SpelExecutor spelExecutor = applicationContext.getBean(SpelExecutor.class);

           return spelExecutor.isTrue(linkAnnotation.when(), methodResult, methodParams);
        }
        else {
            return true;
        }
    }

    private boolean haveToSetLinkParam(LinkParam linkParam, ResourceSupport methodResult,
                                       Object[] methodParams, ApplicationContext applicationContext) {
        if (linkParam.when() != null) {
            SpelExecutor spelExecutor = applicationContext.getBean(SpelExecutor.class);

            return  spelExecutor.isTrue(linkParam.when(), methodResult, methodParams);
        }
        else {
            return true;
        }
    }
}
