package com.github.osvaldopina.halexpectation;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@Ignore
public class HalExpectationsHelperTest {


    private String hal;

    private HalExpectationsHelper halExpectationsHelper;

    @Before
    public void setUp() {
        StringBuffer tmp = new StringBuffer();

        tmp.append("{ \n");
        tmp.append("    \"_links\": {\n");
        tmp.append("        \"self\": {\n");
        tmp.append("            \"href\": \"self-uri\"\n");
        tmp.append("        },\n");
        tmp.append("        \"link-rel\": {\n");
        tmp.append("            \"href\": \"some-uri\"\n");
        tmp.append("        }\n");
        tmp.append("    }\n");
        tmp.append("}");

        halExpectationsHelper = new HalExpectationsHelper(tmp.toString());
    }

    @Test
    public void hasLinkWithRel() throws Exception {
       assertTrue(halExpectationsHelper.hasLinkWithRel("link-rel"));
    }
}