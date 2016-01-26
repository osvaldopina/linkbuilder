package com.github.osvaldopina.linkbuilder;

import org.springframework.hateoas.Link;

import java.util.List;

/**
 * Created by deinf.osvaldo on 22/12/2015.
 */
public interface LinkBuilder {

    /**
     * Sets the link rel to "self".
     *
     * @return LinkBuilder Link builder.
     */
    LinkBuilder withSelfRel();

    /**
     * Sets the link rel attribute.
     *
     * @param rel link rel attribute
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder withRel(String rel);

    /**
     * Sets the link href from current call parameters. Methods annotated with <code>@EnableSelfFromCurrentCall</code>
     * are changed with a aspect that records the method and the list of parameters.
     *
     * @see com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall
     * @return LinkBuilder link builder.
     */
    LinkBuilder fromCurrentCall();

    /**
     * Indicated that the nth method parameter should not be substituted by the parameter value and leaved
     * as a template variable. The resulted link will be a template.
     *
     * @param paramNumber The parameter index the should not be substituted
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder paramAsTemplate(int paramNumber);

    /**
     * Indicated that parameter with this name should not be substituted by the parameter value and leaved
     * as a template variable. The resulted link will be a template. It is important to note that the parameter
     * name is obtained by <code>RequestParam</code> and <code>PathVariable</code> annotation.
     *
     * @see org.springframework.web.bind.annotation.RequestParam
     * @see org.springframework.web.bind.annotation.PathVariable
     *
     * @param templateParamName The parameter name obtained by introspecting <code>RequestParam</code> and
     *                          <code>PathVariable</code>
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder paramAsTemplate(String templateParamName);

    LinkBuilder allParamsAsTemplate();

    <T> T fromControllerCall(Class<T> controllerClass);

    LinkBuilder link();

    Link build();

    List<Link> buildAll();
}
