package com.github.osvaldopina.linkbuilder.methodtemplate.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UriTemplateQueryVarNameValidator {

    private static final Pattern VARNAME_REGEX = Pattern.compile("([\\w\\_\\.]|%[A-Fa-f0-9]{2})+");

    public static boolean isValid(String varName) {
        Matcher matcher = VARNAME_REGEX.matcher(varName);
        return matcher.matches();
    }

    public static void assertValid(String varName) {
        if (! isValid(varName)) {
            throw new IllegalArgumentException("The query variable name '" + varName + "' does not follow rfc spec. " +
                    "Should be have only ALPHA, DIGIT, '_' or pct-encoded.");
        }
    }
}
