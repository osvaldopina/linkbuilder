package com.github.osvaldopina.linkbuilder.linkdestination.springhateoas;

public class DestinationIdentityFactorty {

    public static final DestinationIdentityFactorty INSTANCE = new DestinationIdentityFactorty();

    DestinationIdentityFactorty() {

    }

    public String destination(Class<?> controller, String rel) {
        return controller.getName() + ":" + rel;
    }



}
