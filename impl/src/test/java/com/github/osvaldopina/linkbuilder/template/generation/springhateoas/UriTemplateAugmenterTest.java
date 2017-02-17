package com.github.osvaldopina.linkbuilder.template.generation.springhateoas;

import com.damnhandy.uri.template.Expression;
import com.damnhandy.uri.template.Literal;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.UriTemplateBuilder;
import com.damnhandy.uri.template.impl.Operator;
import com.github.osvaldopina.linkbuilder.template.generation.springhateoas.UriTemplateAugmenter;
import org.junit.Test;

import static org.junit.Assert.*;

public class UriTemplateAugmenterTest {

    private UriTemplateAugmenter uriTemplateAugmenter;

    @Test
    public void addToQueryTemplateWithoutQueryExpression() throws Exception {

        UriTemplateBuilder uriTemplateBuilder = UriTemplate.createBuilder();

        uriTemplateAugmenter = new UriTemplateAugmenter(uriTemplateBuilder);

        uriTemplateAugmenter.addToQuery("var1");

        assertEquals(1, uriTemplateBuilder.getComponents().length);
        assertTrue(uriTemplateBuilder.getComponents()[0] instanceof Expression);
        Expression queryExpression = (Expression) uriTemplateBuilder.getComponents()[0];

        assertEquals(Operator.QUERY, queryExpression.getOperator());
        assertEquals(1, queryExpression.getVarSpecs().size());
        assertEquals("var1", queryExpression.getVarSpecs().get(0).getVariableName());
    }

    @Test
    public void addToQueryTemplateWithQueryExpression() throws Exception {

        UriTemplateBuilder uriTemplateBuilder = UriTemplate.buildFromTemplate("{?var1}");

        uriTemplateAugmenter = new UriTemplateAugmenter(uriTemplateBuilder);

        uriTemplateAugmenter.addToQuery("var2");

        assertEquals(1, uriTemplateBuilder.getComponents().length);
        assertTrue(uriTemplateBuilder.getComponents()[0] instanceof Expression);

        Expression queryExpression = (Expression) uriTemplateBuilder.getComponents()[0];
        assertEquals(Operator.QUERY, queryExpression.getOperator());
        assertEquals(2, queryExpression.getVarSpecs().size());
        assertEquals("var1", queryExpression.getVarSpecs().get(0).getVariableName());
        assertEquals("var2", queryExpression.getVarSpecs().get(1).getVariableName());
    }

    @Test
    public void template() throws Exception {
        UriTemplateBuilder uriTemplateBuilder = UriTemplate.createBuilder();

        uriTemplateAugmenter = new UriTemplateAugmenter(uriTemplateBuilder);

        uriTemplateAugmenter.template("templated/{var}");


        assertEquals(2, uriTemplateBuilder.getComponents().length);
        assertTrue(uriTemplateBuilder.getComponents()[0] instanceof Literal);
        assertTrue(uriTemplateBuilder.getComponents()[1] instanceof Expression);

        Literal literal = (Literal) uriTemplateBuilder.getComponents()[0];
        assertEquals("templated/", literal.toString());

        Expression expression = (Expression) uriTemplateBuilder.getComponents()[1];
        assertEquals(Operator.NUL, expression.getOperator());
        assertEquals(1, expression.getVarSpecs().size());
        assertEquals("var", expression.getVarSpecs().get(0).getVariableName());

    }
}