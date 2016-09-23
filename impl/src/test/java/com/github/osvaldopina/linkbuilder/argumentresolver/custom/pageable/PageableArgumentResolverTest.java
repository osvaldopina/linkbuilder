package com.github.osvaldopina.linkbuilder.argumentresolver.custom.pageable;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.custom.pageable.PageableArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.Assert.*;

public class PageableArgumentResolverTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    private PageableArgumentResolver pageableArgumentResolver = new PageableArgumentResolver();

    @Mock
    private Pageable pageable;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private Sort sort;

    @Mock
    private UriTemplateAugmenter uriTemplateAugmenter;

    @Mock
    private UriTemplate uriTemplate;

    @Mock
    private VariableSubstitutionController variableSubstitutionController;

    @Test
    public void resolveForPageable() throws Exception {
        EasyMock.expect((Class) methodParameter.getParameterType()).andReturn(Pageable.class);

        replayAll();

        assertTrue(pageableArgumentResolver.resolveFor(methodParameter));

        verifyAll();
    }

    @Test
    public void resolveForNonPageable() throws Exception {
        EasyMock.expect((Class) methodParameter.getParameterType()).andReturn(String.class);

        replayAll();

        assertFalse(pageableArgumentResolver.resolveFor(methodParameter));

        verifyAll();
    }



    @Test
    public void addToTemplate() throws Exception {

        uriTemplateAugmenter.addToQuery("page");
        EasyMock.expectLastCall();
        uriTemplateAugmenter.addToQuery("size");
        EasyMock.expectLastCall();
        uriTemplateAugmenter.addToQuery("sort");
        EasyMock.expectLastCall();

        replayAll();

        pageableArgumentResolver.augmentTemplate(uriTemplateAugmenter, methodParameter);

        verifyAll();

    }

    @Test
    public void AddToTemplateValuesFromPageableObjectTemplatedParamNamesEmpty() throws Exception {

        EasyMock.expect(variableSubstitutionController.substitute(methodParameter, "page", pageable)).andReturn(true);
        EasyMock.expect(pageable.getPageNumber()).andReturn(1);
        EasyMock.expect(uriTemplate.set("page", 1)).andReturn(uriTemplate);

        EasyMock.expect(variableSubstitutionController.substitute(methodParameter, "size", pageable)).andReturn(true);
        EasyMock.expect(pageable.getPageSize()).andReturn(2);
        EasyMock.expect(uriTemplate.set("size", 2)).andReturn(uriTemplate);

        EasyMock.expect(variableSubstitutionController.substitute(methodParameter, "sort", pageable)).andReturn(true);
        EasyMock.expect(pageable.getSort()).andReturn(sort);
        EasyMock.expect(uriTemplate.set("sort", sort)).andReturn(uriTemplate);

        replayAll();

        pageableArgumentResolver.setTemplateVariables(uriTemplate, methodParameter, pageable,
                variableSubstitutionController);

        verifyAll();

    }

    @Test
    public void AddToTemplateValuesFromPageableObjectTemplatedParamNamesWithPageableParams() throws Exception {

        EasyMock.expect(variableSubstitutionController.substitute(methodParameter, "page", pageable)).andReturn(false);
        EasyMock.expect(variableSubstitutionController.substitute(methodParameter, "size", pageable)).andReturn(false);
        EasyMock.expect(variableSubstitutionController.substitute(methodParameter, "sort", pageable)).andReturn(false);

        replayAll();

        pageableArgumentResolver.setTemplateVariables(uriTemplate, methodParameter, pageable, variableSubstitutionController);

        verifyAll();

    }

}