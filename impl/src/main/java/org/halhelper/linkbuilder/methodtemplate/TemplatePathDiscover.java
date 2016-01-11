package org.halhelper.linkbuilder.methodtemplate;

import org.halhelper.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.hateoas.core.AnnotationMappingDiscoverer;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * Created by osvaldopina on 1/4/16.
 */
public class TemplatePathDiscover {

    private static final AnnotationMappingDiscoverer REQUEST_MAPPING_DISCOVER =
            new AnnotationMappingDiscoverer(RequestMapping.class);


    public void augmentPath(UriTemplateAugmenter uriTemplateAugmenter, Method method) {

        String path = REQUEST_MAPPING_DISCOVER.getMapping(method);

        uriTemplateAugmenter.template(path);

    }

}
