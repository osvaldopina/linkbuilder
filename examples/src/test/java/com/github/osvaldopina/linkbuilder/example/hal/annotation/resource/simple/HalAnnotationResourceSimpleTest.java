package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.simple;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.osvaldopina.linkbuilder.example.hal.annotation.controller.simple.HalAnnotationControllerSimpleApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = HalAnnotationResourceSimpleApplication.class)
public class HalAnnotationResourceSimpleTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void verifyResponse() throws Exception {

        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").
                        value("http://localhost/"))

                .andExpect(jsonPath("$._links.direct-link.href")
                        .value("http://localhost/direct-link/anyPathValue?query=anyQueryValue"))
                .andExpect(jsonPath("$._links.direct-link.hreflang").
                        value("href-lang-1"))

                .andExpect(jsonPath("$._links.direct-link-overrided.href")
                        .value("http://localhost/direct-link/anyPathValue?query=anyQueryValue"))
                .andExpect(jsonPath("$._links.direct-link-overrided.hreflang").
                        value("href-lang-2"))

                .andExpect(jsonPath("$._links.direct-link-templated.href")
                        .value("http://localhost/direct-link/templated?templated=templated-value{&non_templated}"))
                .andExpect(jsonPath("$._links.direct-link-templated.hreflang").
                        value("href-lang-3"))

                .andExpect(jsonPath("$._embedded.first._links.self.href").
                        value("http://localhost/first-embedded/1"))

                .andExpect(jsonPath("$._embedded.first._embedded.forthEmbeddeds[0]._links.self.href").
                        value("http://localhost/third-embedded/2"))
        ;

    }

}