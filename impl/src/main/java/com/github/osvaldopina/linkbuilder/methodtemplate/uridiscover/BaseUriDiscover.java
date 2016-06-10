package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover;

import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Discovers the base uri for rest calls
 */
public interface BaseUriDiscover {

    /**
     * Returns the base uri for all rest calls
     * @return Base uri for all rest calls
     */
    String getBaseUri();

}
