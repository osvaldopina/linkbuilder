package com.github.osvaldopina.linkbuilder.template;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.template.impl.UriTemplateVariableSetter;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

public class TemplateTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private UriTemplateVariableSetter uriTemplateVariableSetter;

    @Mock
    private UriTemplate uriTemplate;

    @Mock
    private Variables variables;

    @Mock
    private VariableValues variableValues;

    @TestSubject
    Template template = new Template(uriTemplate, variables);

    @Test
    public void toUri() throws Exception {
        String generatedTemplate = "templated";

        EasyMock.expect(
                uriTemplateVariableSetter.createNewTemplateAndSetVariables(uriTemplate,variables, variableValues)
        ).andReturn(uriTemplate);

        EasyMock.expect(uriTemplate.expand()).andReturn(generatedTemplate);


        replayAll();

        assertThat(template.toUri(variableValues), is(generatedTemplate));

        verifyAll();
    }

    @Test
    public void toTemplatedUri() throws Exception {
        String generatedTemplate = "templated";

        EasyMock.expect(
                uriTemplateVariableSetter.createNewTemplateAndSetVariables(uriTemplate,variables, variableValues)
        ).andReturn(uriTemplate);

        EasyMock.expect(uriTemplate.expandPartial()).andReturn(generatedTemplate);


        replayAll();

        assertThat(template.toTemplatedUri(variableValues), is(generatedTemplate));
    }

    @Test
    public void getVariables() throws Exception {

        assertThat(template.getVariables(), sameInstance(variables));
    }

}