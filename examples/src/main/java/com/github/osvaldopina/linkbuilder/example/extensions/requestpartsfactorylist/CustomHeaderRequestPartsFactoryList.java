package com.github.osvaldopina.linkbuilder.example.extensions.requestpartsfactorylist;



import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactory;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl.ServletRequestRequestPartsFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomHeaderRequestPartsFactoryList implements RequestPartsFactoryList {

    List<RequestPartsFactory> requestPartsFactories = Collections.unmodifiableList(Arrays.asList(
            new CustomHeaderPartsFactory(),
            new ServletRequestRequestPartsFactory()
    ));


    @Override
    public List<RequestPartsFactory> getRequestPartsFactories() {
        return requestPartsFactories;
    }
}
