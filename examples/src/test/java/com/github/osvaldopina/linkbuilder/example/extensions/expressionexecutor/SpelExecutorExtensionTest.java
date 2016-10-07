package com.github.osvaldopina.linkbuilder.example.extensions.expressionexecutor;

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

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
public class SpelExecutorExtensionTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void spelExecutorIsCalledAndReturnTrue() throws Exception {

        ExpressionExecutorDefinedReturn.returnValue = true;

        mockMvc.perform(get("/"))
        .andExpect(jsonPath("$._links.rel.href").value("http://localhost/"));

    }

    @Test
    public void spelExecutorIsCalledAndReturnFalse() throws Exception {

        ExpressionExecutorDefinedReturn.returnValue = false;

        mockMvc.perform(get("/"))
                //    .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.rel.href").doesNotExist());

    }
}