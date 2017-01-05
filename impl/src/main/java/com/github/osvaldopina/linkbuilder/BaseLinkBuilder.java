package com.github.osvaldopina.linkbuilder;

import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactory;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder.CallRecorder;
import com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder.RecordCallInterceptorCreator;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreator;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreators;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core.DontSubstituteAny;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core.DontSubstituteNullValue;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core.DontSubstituteParameterIndex;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core.DontSubstituteVariableName;

public class BaseLinkBuilder implements LinkBuilder, CallRecorder {

    private LinksBuilder linksBuilder;
    private CurrentCallLocator currentCallLocator;
    private LinkPropertiesLinkCreators linkPropertiesLinkCreators;
    private LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry;
    private LinkProperties linkProperties;
    private RecordCallInterceptorCreator recordCallInterceptorCreator = RecordCallInterceptorCreator.INSTANCE;

    public BaseLinkBuilder(
            LinksBuilder linksBuilder,
            CurrentCallLocator currentCallLocator,
            LinkPropertiesLinkCreators linkPropertiesLinkCreators,
            LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry,
            Object resource
    ) {

        this.linksBuilder = linksBuilder;
        this.currentCallLocator = currentCallLocator;
        this.linkPropertiesLinkCreators = linkPropertiesLinkCreators;
        this.linkBuilderExtensionFactoryRegistry = linkBuilderExtensionFactoryRegistry;
        this.linkProperties = new BaseLinkProperties();
        this.linkProperties.setResource(resource);
    }

    public BaseLinkBuilder(BaseLinkBuilder baseLinkBuilder) {
        this.linksBuilder = baseLinkBuilder.linksBuilder;
        this.currentCallLocator = baseLinkBuilder.currentCallLocator();
        this.linkPropertiesLinkCreators = baseLinkBuilder.linkPropertiesLinkCreators;
        this.linkBuilderExtensionFactoryRegistry = baseLinkBuilder.linkBuilderExtensionFactoryRegistry;
        this.linkProperties = new LinkPropertiesDelegate(baseLinkBuilder.getLinkProperties());
    }


    public void setLinkProperties(LinkProperties linkProperties) {
        this.linkProperties = linkProperties;
    }

    public LinkProperties getLinkProperties() {
        return linkProperties;
    }

    protected CurrentCallLocator currentCallLocator() {
        return currentCallLocator;
    }

    @Override
    public LinkBuilder withSelfRel() {
        linkProperties.setRel("self");
        return this;
    }

    @Override
    public LinkBuilder withRel(String rel) {
        linkProperties.setRel(rel);
        return this;
    }

    @Override
    public LinkBuilder setResource(Object resource) {
        linkProperties.setResource(resource);
        return this;
    }

    @Override
    public LinkBuilder when(String expression) {
        linkProperties.setWhenExpression(expression);
        return this;
    }

    @Override
    public LinkBuilder fromCurrentCall() {
        linkProperties.setMethodCall(currentCallLocator.getCurrentCall());
        return this;
    }

    @Override
    public LinkBuilder dontSubstituteParameterIndex(int paramIndex) {
        linkProperties.addConditionalVariableSubstitutionStrategy(new DontSubstituteParameterIndex(paramIndex));
        return this;
    }

    @Override
    public LinkBuilder dontSubstitute(String variableName) {
        linkProperties.addConditionalVariableSubstitutionStrategy(new DontSubstituteVariableName(variableName));
        return this;
    }

    @Override
    public LinkBuilder dontSubstituteNullValues() {
        linkProperties.addConditionalVariableSubstitutionStrategy(new DontSubstituteNullValue());
        return this;
    }

    @Override
    public LinkBuilder dontSubstituteAny() {
        linkProperties.addConditionalVariableSubstitutionStrategy(new DontSubstituteAny());
        return this;
    }

    public LinkBuilder templated() {
        linkProperties.setTemplated(true);
        return this;
    }

    @Override
    public <E> E fromControllerCall(Class<E> controllerClass) {
        return recordCallInterceptorCreator.createCallRecorderForClass(controllerClass, this);
    }

    @Override
    public <E extends LinkBuilder> E extendTo(Class<E> extensionType) {
        LinkBuilderExtensionFactory linkBuilderExtensionFactory = linkBuilderExtensionFactoryRegistry.get(extensionType);
        return linkBuilderExtensionFactory.createExtension(extensionType, this);
    }

    @Override
    public LinkBuilder link() {
        return linksBuilder.link();
    }

    @Override
    public Object build() {
        LinkPropertiesLinkCreator linkPropertiesLinkCreator = linkPropertiesLinkCreators.get(linkProperties);
        return linkPropertiesLinkCreator.create(linkProperties);
    }

    public void builAndSet() {
        LinkPropertiesLinkCreator linkPropertiesLinkCreator = linkPropertiesLinkCreators.get(linkProperties);

        linkPropertiesLinkCreator.createAndSet(linkProperties);

    }

    @Override
    public void record(MethodCall methodCall) {
        linkProperties.setMethodCall(methodCall);
    }


    public LinksBuilder getLinksBuilder() {
        return linksBuilder;
    }

    public CurrentCallLocator getCurrentCallLocator() {
        return currentCallLocator;
    }

    public LinkPropertiesLinkCreators getLinkPropertiesLinkCreators() {
        return  linkPropertiesLinkCreators;

    }

    public LinkBuilderExtensionFactoryRegistry getLinkBuilderExtensionFactoryRegistry() {
        return linkBuilderExtensionFactoryRegistry;
    }

    public Object getResource() {
        return linkProperties.getResource();
    }


}

