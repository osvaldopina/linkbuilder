package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts;

import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl.ChainedRequestParts;

import javax.servlet.http.HttpServletRequest;

public interface RequestPartsFactory {

    ChainedRequestParts create(HttpServletRequest request);
}
