package org.halhelper.linkbuilder;



import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deinf.osvaldo on 15/12/2015.
 */
public class LinksBuilder {

    private List<LinkBuilderImpl> linkBuilders = new ArrayList<LinkBuilderImpl>();

    private ApplicationContext applicationContext;

    public LinksBuilder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public LinkBuilder link() {
        LinkBuilderImpl linkBuilder = new LinkBuilderImpl(applicationContext, this);
        linkBuilders.add(linkBuilder);
        return linkBuilder;
    }


    public List<Link> buildAll() {
        List<Link> links = new ArrayList<Link>();
        for(LinkBuilderImpl linkBuilder:linkBuilders) {
            links.add(linkBuilder.build());
        }
        return links;
    }
}
