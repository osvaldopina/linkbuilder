package org.halhelper.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.UriTemplateBuilder;
import org.halhelper.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;

import java.util.List;

/**
 * Created by deinf.osvaldo on 23/12/2015.
 */
public interface ArgumentResolver {

    boolean resolveFor(MethodParameter methodParameter);

    void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, MethodParameter methodParameter);

    void setTemplateVariables(UriTemplate template, MethodParameter methodParameter, Object parameter,
                              List<String> templatedParamNames);


}
