package com.github.osvaldopina.linkbuilder.methodtemplate.impl;

import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.hateoas.core.AnnotationMappingDiscoverer;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

class TemplatePathDiscover {

    private static final AnnotationMappingDiscoverer REQUEST_MAPPING_DISCOVER =
            new AnnotationMappingDiscoverer(RequestMapping.class);


    public void augmentPath(UriTemplateAugmenter uriTemplateAugmenter, Method method) {

        String path = REQUEST_MAPPING_DISCOVER.getMapping(method);

        uriTemplateAugmenter.template(path);

    }

}
