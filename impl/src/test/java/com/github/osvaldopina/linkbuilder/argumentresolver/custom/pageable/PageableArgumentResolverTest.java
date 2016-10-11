package com.github.osvaldopina.linkbuilder.argumentresolver.custom.pageable;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.easymock.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class PageableArgumentResolverTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    private PageableArgumentResolver pageableArgumentResolver = new PageableArgumentResolver();

    @Mock
    private Pageable pageable;

    private Method methodPageable;

    private Method methodNotPageable;

    @Mock
    private Sort sort;

    @Mock
    private UriTemplateAugmenter uriTemplateAugmenter;

    @Mock
    private UriTemplate uriTemplate;

    @Mock
    private VariableSubstitutionController variableSubstitutionController;

    @Before
    public void setUp() throws Exception {
        methodPageable = PageableArgumentResolverTest.class.getMethod("pageable", Pageable.class);
        methodNotPageable = PageableArgumentResolverTest.class.getMethod("equals", Object.class);
    }

    @Test
    public void resolveForPageable() throws Exception {

        replayAll();

        assertTrue(pageableArgumentResolver.resolveFor(methodPageable, 0));

        verifyAll();
    }

    @Test
    public void resolveForNonPageable() throws Exception {

        replayAll();

        assertFalse(pageableArgumentResolver.resolveFor(methodNotPageable, 0));

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

        pageableArgumentResolver.augmentTemplate(uriTemplateAugmenter, methodPageable, 0);

        verifyAll();

    }

    @Test
    public void AddToTemplateValuesFromPageableObjectTemplatedParamNamesEmpty() throws Exception {

        EasyMock.expect(variableSubstitutionController.substitute(methodPageable, 0, "page", pageable)).andReturn(true);
        EasyMock.expect(pageable.getPageNumber()).andReturn(1);
        EasyMock.expect(uriTemplate.set("page", 1)).andReturn(uriTemplate);

        EasyMock.expect(variableSubstitutionController.substitute(methodPageable, 0, "size", pageable)).andReturn(true);
        EasyMock.expect(pageable.getPageSize()).andReturn(2);
        EasyMock.expect(uriTemplate.set("size", 2)).andReturn(uriTemplate);

        EasyMock.expect(variableSubstitutionController.substitute(methodPageable, 0, "sort", pageable)).andReturn(true);
        EasyMock.expect(pageable.getSort()).andReturn(sort);
        EasyMock.expect(uriTemplate.set("sort", sort)).andReturn(uriTemplate);

        replayAll();

        pageableArgumentResolver.setTemplateVariables(uriTemplate, methodPageable, 0, pageable,
                variableSubstitutionController);

        verifyAll();

    }

    @Test
    public void AddToTemplateValuesFromPageableObjectTemplatedParamNamesWithPageableParams() throws Exception {

        EasyMock.expect(variableSubstitutionController.substitute(methodPageable, 0, "page", pageable)).andReturn(false);
        EasyMock.expect(variableSubstitutionController.substitute(methodPageable, 0, "size", pageable)).andReturn(false);
        EasyMock.expect(variableSubstitutionController.substitute(methodPageable, 0, "sort", pageable)).andReturn(false);

        replayAll();

        pageableArgumentResolver.setTemplateVariables(uriTemplate, methodPageable, 0, pageable, variableSubstitutionController);

        verifyAll();

    }

    public void pageable(Pageable pageable) {

    }

}