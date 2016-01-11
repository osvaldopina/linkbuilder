package org.halhelper.linkbuilder;

import org.springframework.hateoas.Link;

import java.util.List;

/**
 * Created by deinf.osvaldo on 22/12/2015.
 */
public interface LinkBuilder {

    LinkBuilder withSelfRel();

    LinkBuilder withRel(String rel);

    LinkBuilder fromCurrentCall();

    LinkBuilder asTemplate();

    LinkBuilder paramAsTemplate(int paramNumber);

    LinkBuilder paramAsTemplate(String templateParamName);

    LinkBuilder allParamsAsTemplate();

    <T> T fromControllerCall(Class<T> controllerClass);

    LinkBuilder link();

    Link build();

    List<Link> buildAll();
}
