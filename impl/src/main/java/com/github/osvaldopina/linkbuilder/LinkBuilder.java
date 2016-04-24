package com.github.osvaldopina.linkbuilder;

import org.springframework.hateoas.Link;

import java.util.List;

/**
 * Builder to configure and create a <code>Link</code>
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
     * Sets the payload variable in spel expression context so it can be used in expressions
     *
     * @param payload Object to be setted as 'payload' variable in spel context
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder setExpressionPayload(Object payload);

    /**
     * Defines spel condition expression for link rendering. If expression evaluates to true the link will
     * be rendered in buildAll()
     *
     * @param expression Spel expression
     *
     * @return linkBuilder link builder.
     */
    LinkBuilder when(String expression);
    /**
     * Sets the link href from current call parameters. Methods annotated with <code>@EnableSelfFromCurrentCall</code>
     * are changed with a aspect that records the method and the list of parameters.
     *
     * @see com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall
     * @return LinkBuilder link builder.
     */
    LinkBuilder fromCurrentCall();

    /**
     * Indicates that the nth method parameter should not be substituted by the parameter value and leaved
     * as a template variable. The resulted link will be a template.
     *
     * @param paramNumber The parameter index the should not be substituted
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder paramAsTemplate(int paramNumber);

    /**
     * Indicates that parameter with this name should not be substituted by the parameter value and leaved
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

    /**
     * Indicates that all parameters should not be substituted by the parameter value and leaved
     * as a template variable. The resulted link will be a template.
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder allParamsAsTemplate();

    /**
     * Sets the link parameters from a call to a rest controller.
     *
     * @param controllerClass Controller class.
     * @param <T> Controller type
     *
     * @return LinkBuilder link builder.
     */
    <T> T fromControllerCall(Class<T> controllerClass);

    /**
     * Creates a new <code>LinkBuilder</code>.
     *
     * @return LinkBuilder new link builder.
     */
    LinkBuilder link();

    /**
     * Creates the <code>Link</code>.
     *
     * @return Link link.
     */
    Link build();

    /**
     * Creates all links configured in the same <code>LinksBuilder</code>.
     *
     * @return List of links configured in the same <code>LinksBuilder</code>.
     */
    List<Link> buildAll();
}
