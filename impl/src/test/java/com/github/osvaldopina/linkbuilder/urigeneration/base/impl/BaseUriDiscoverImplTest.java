package com.github.osvaldopina.linkbuilder.urigeneration.base.impl;

import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactory;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl.ChainedRequestParts;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

import static org.junit.Assert.assertEquals;

public class BaseUriDiscoverImplTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    BaseUriDiscoverImpl baseUriDiscoverImpl = new BaseUriDiscoverImpl();

    @Mock
    private HttpRequestDiscover httpRequestDiscover;

    @Mock
    RequestPartsFactoryList requestPartsFactoryList;

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    Enumeration<String> headers;

    @Mock
    ChainedRequestParts nextChainedRequestParts;

    @Mock
    RequestPartsFactory requestPartsFactory1;

    @Mock
    ChainedRequestParts requestParts1;

    @Mock
    RequestPartsFactory requestPartsFactory2;

    @Mock
    ChainedRequestParts requestParts2;

    @Test
    public void getBaseUriOnlyOneRequestPartsFactory() throws Exception {

        EasyMock.expect(httpRequestDiscover.getCurrentRequest()).andReturn(httpServletRequest);
        EasyMock.expect(requestPartsFactoryList.getRequestPartsFactories()).andReturn(Arrays.asList(requestPartsFactory1));
        EasyMock.expect(requestPartsFactory1.create(httpServletRequest)).andReturn(requestParts1);
        EasyMock.expect(requestParts1.getUri()).andReturn("any-url");

        replayAll();

        assertEquals("any-url", baseUriDiscoverImpl.getBaseUri());

        verifyAll();


    }

    @Test
    public void getBaseUriTwoRequestPartsFactory() throws Exception {

        EasyMock.expect(httpRequestDiscover.getCurrentRequest()).andReturn(httpServletRequest);
        EasyMock.expect(requestPartsFactoryList.getRequestPartsFactories()).andReturn(
                Arrays.asList(requestPartsFactory1, requestPartsFactory2)
        );
        EasyMock.expect(requestPartsFactory1.create(httpServletRequest)).andReturn(requestParts1);
        EasyMock.expect(requestPartsFactory2.create(httpServletRequest)).andReturn(requestParts2);
        requestParts1.setNext(requestParts2);
        EasyMock.expectLastCall();
        EasyMock.expect(requestParts1.getUri()).andReturn("any-url");

        replayAll();

        assertEquals("any-url", baseUriDiscoverImpl.getBaseUri());

        verifyAll();


    }

}