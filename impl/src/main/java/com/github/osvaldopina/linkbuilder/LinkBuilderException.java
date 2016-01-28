package com.github.osvaldopina.linkbuilder;

/**
 * Exception for any framework problem.
 */
public class LinkBuilderException extends RuntimeException {

    public LinkBuilderException(String message) {
        super(message);
    }

    public LinkBuilderException(String message, Throwable cause) {
        super(message, cause);
    }


 }
