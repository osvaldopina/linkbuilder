package com.github.osvaldopina.linkbuilder;

import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core.DontSubstituteAny;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core.DontSubstituteNullValue;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core.DontSubstituteParameterIndex;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core.DontSubstituteVariableName;

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
     * Sets the resource variable in expression expression context so it can be used in expressions
     *
     * @param resource Object to be setted as 'resource' variable in expression context
     * @return LinkBuilder link builder.
     */
    LinkBuilder setResource(Object resource);

    /**
     * Defines expression condition expression for link rendering. If expression evaluates to true the link will
     * be rendered in buildAll()
     *
     * @param expression Spel expression
     * @return linkBuilder link builder.
     */
    LinkBuilder when(String expression);


    LinkBuilder fromCurrentCall();

    LinkBuilder dontSubstituteParameterIndex(int paramIndex);

    LinkBuilder dontSubstitute(String variableName);

    LinkBuilder dontSubstituteNullValues();

    LinkBuilder dontSubstituteAny();

    /**
     * Sets the link parameters from a call to a rest controller.
     *
     * @param controllerClass Controller class.
     * @param <E>             Controller type
     * @return LinkBuilder link builder.
     */
    <E> E fromControllerCall(Class<E> controllerClass);


    <E extends LinkBuilder> E extendTo(Class<E> extension);

    /**
     * Creates a new <code>LinkBuilder</code>.
     *
     * @return LinkBuilder new link builder.
     *
     *
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
