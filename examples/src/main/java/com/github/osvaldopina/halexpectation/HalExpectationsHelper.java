package com.github.osvaldopina.halexpectation;

import org.springframework.test.util.JsonPathExpectationsHelper;

import java.text.ParseException;

public class HalExpectationsHelper {

    private String jsonHal;

    private JsonPathExpectationsHelper jsonPathExpectationsHelper;


    public HalExpectationsHelper(String jsonHal) {
        this.jsonHal = jsonHal;

    }

    public boolean hasLinkWithRel(String rel) {
        try {
            jsonPathExpectationsHelper = new JsonPathExpectationsHelper("$._links[' ]");

            jsonPathExpectationsHelper.assertValue(jsonHal,"rel");

        } catch (ParseException e) {
            return false;
        }
        return true;
    }





}
