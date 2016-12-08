package com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts;

import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl.ChainedRequestParts;

import javax.servlet.http.HttpServletRequest;

public interface RequestPartsFactory {

    ChainedRequestParts create(HttpServletRequest request);
}
