package com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts;

import javax.servlet.http.HttpServletRequest;

public interface RequestPartsFactory {

    ChainedRequestParts create(HttpServletRequest request);
}
