package com.github.osvaldopina.linkbuilder;

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
     * @return LinkBuilder link builder.
     */
    LinkBuilder withRel(String rel);

    /**
     * Sets the payload variable in expression expression context so it can be used in expressions
     *
     * @param payload Object to be setted as 'payload' variable in expression context
     * @return LinkBuilder link builder.
     */
    LinkBuilder setExpressionPayload(Object payload);

    /**
     * Defines expression condition expression for link rendering. If expression evaluates to true the link will
     * be rendered in buildAll()
     *
     * @param expression Spel expression
     * @return linkBuilder link builder.
     */
    LinkBuilder when(String expression);


    boolean whenExpressionIsTrue();

     /**
     * Indicates that the nth method parameter should not be substituted by the parameter value and leaved
     * as a template variable. The resulted link will be a template.
     *
     * @param paramNumber The parameter index the should not be substituted
     * @return LinkBuilder link builder.
     */
    LinkBuilder variableAsTemplate(int paramNumber);

    /**
     * Indicates that parameter with this name should not be substituted by the parameter value and leaved
     * as a template variable. The resulted link will be a template. It is important to note that the parameter
     * name is obtained by <code>RequestParam</code> and <code>PathVariable</code> annotation.
     *
     * @param templateParamName The parameter name obtained by introspecting <code>RequestParam</code> and
     *                          <code>PathVariable</code>
     * @return LinkBuilder link builder.
     * @see org.springframework.web.bind.annotation.RequestParam
     * @see org.springframework.web.bind.annotation.PathVariable
     */
    LinkBuilder variableAsTemplate(String templateParamName);


    /**
     * Indicates that all null parameters should no be substituted and should be leaved as a template variable.
     * The resulted link will be a template.
     *
     * @return LinkBuilder link builder.
     */
    LinkBuilder nullVariablesAsTemplate();

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
     * @param <E>             Controller type
     * @return LinkBuilder link builder.
     */
    <E> E fromControllerCall(Class<E> controllerClass);

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
    Object build();

    /**
     * Creates all links configured in the same <code>LinksBuilder</code>.
     *
     */
    void builAndSet();

}
