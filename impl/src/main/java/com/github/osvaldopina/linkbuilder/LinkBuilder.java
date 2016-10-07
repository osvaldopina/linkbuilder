package com.github.osvaldopina.linkbuilder;

import java.util.List;

/**
 * Builder to configure and create a <code>Link</code>
 */
public interface LinkBuilder<T> {

    /**
     * Sets the link rel to "self".
     *
     * @return LinkBuilder Link builder.
     */
    LinkBuilder<T> withSelfRel();

    /**
     * Sets the link rel attribute.
     *
     * @param rel link rel attribute
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder<T> withRel(String rel);

    /**
     * Sets the payload variable in expression expression context so it can be used in expressions
     *
     * @param payload Object to be setted as 'payload' variable in expression context
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder<T> setExpressionPayload(Object payload);

    /**
     * Defines expression condition expression for link rendering. If expression evaluates to true the link will
     * be rendered in buildAll()
     *
     * @param expression Spel expression
     *
     * @return linkBuilder link builder.
     */
    LinkBuilder<T> when(String expression);
    /**
     * Sets the link href from current call parameters. Methods annotated with <code>@EnableSelfFromCurrentCall</code>
     * are changed with a aspect that records the method and the list of parameters.
     *
     * @see com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall
     * @return LinkBuilder link builder.
     */
    LinkBuilder<T> fromCurrentCall();

    /**
     * Indicates that the nth method parameter should not be substituted by the parameter value and leaved
     * as a template variable. The resulted link will be a template.
     *
     * @param paramNumber The parameter index the should not be substituted
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder<T> variableAsTemplate(int paramNumber);

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
    LinkBuilder<T> variableAsTemplate(String templateParamName);


    /**
     * Indicates that all null parameters should no be substituted and should be leaved as a template variable.
     * The resulted link will be a template.
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder<T> nullVariablesAsTemplate();

    /**
     * Indicates that all parameters should not be substituted by the parameter value and leaved
     * as a template variable. The resulted link will be a template.
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder<T> allParamsAsTemplate();

    /**
     * Sets the link parameters from a call to a rest controller.
     *
     * @param controllerClass Controller class.
     * @param <E> Controller type
     *
     * @return LinkBuilder link builder.
     */
     <E> E fromControllerCall(Class<E> controllerClass);

    /**
     * Creates a new <code>LinkBuilder</code>.
     *
     * @return LinkBuilder new link builder.
     */
    LinkBuilder<T> link();

    /**
     * Creates the <code>Link</code>.
     *
     * @return Link link.
     */
    T build();

    /**
     * Creates all links configured in the same <code>LinksBuilder</code>.
     *
     * @return List of links configured in the same <code>LinksBuilder</code>.
     */
    List<T> buildAll();
}
