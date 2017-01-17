package com.github.osvaldopina.linkbuilder.utils;

import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UriTemplateQueryVarNameValidator {

    public static final UriTemplateQueryVarNameValidator INSTANCE = new UriTemplateQueryVarNameValidator();


    private static final Pattern VARNAME_REGEX = Pattern.compile("([\\w\\_\\.]|%[A-Fa-f0-9]{2})+");


    UriTemplateQueryVarNameValidator() {

    }

    public boolean isValid(String varName) {
        Matcher matcher = VARNAME_REGEX.matcher(varName);
        return matcher.matches();
    }

    public void assertValid(String varName) {
        if (! isValid(varName)) {
            throw new IllegalArgumentException("The query variable name '" + varName + "' does not follow rfc spec. " +
                    "Should be have only ALPHA, DIGIT, '_' or pct-encoded.");
        }
    }
}
