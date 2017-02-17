package com.github.osvaldopina.linkbuilder.example.annotation.resource.simple;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
@ContextConfiguration(classes = AnnotationResourceSimpleApplication.class)
public class AnnotationResourceSimpleTest {

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
				.andExpect(jsonPath("$._links.self.href")
						.value("http://localhost/"))
				.andExpect(jsonPath("$._links.direct-link.href")
						.value("http://localhost/direct-link/anyPathValue?query=anyQueryValue"))
				.andExpect(jsonPath("$._links.direct-link-overrided.href")
						.value("http://localhost/direct-link/anyPathValue?query=anyQueryValue"))
				.andExpect(jsonPath("$._links.direct-link-templated.href")
						.value("http://localhost/direct-link/templated?templated=templated-value{&non_templated}"));
	}
}