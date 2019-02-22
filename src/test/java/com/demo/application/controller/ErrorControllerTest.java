package com.demo.application.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.demo.application.config.TestContext;
import com.demo.application.configuration.WebAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppConfig.class })
@WebAppConfiguration
public class ErrorControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void show404Page() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(ErrorController.URL_USER_NOT_FOUND_ERROR))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name(ErrorController.VIEW_NOT_FOUND))
		.andExpect(MockMvcResultMatchers.forwardedUrl(WebAppConfig.VIEW_RESOLVER_PREFIX + ErrorController.VIEW_NOT_FOUND + WebAppConfig.VIEW_RESOLVER_SUFFIX));
	}

	@Test
	public void showInternalServerErrorPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/error/error"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name(ErrorController.VIEW_INTERNAL_SERVER_ERROR))
		.andExpect(MockMvcResultMatchers.forwardedUrl(WebAppConfig.VIEW_RESOLVER_PREFIX + ErrorController.VIEW_INTERNAL_SERVER_ERROR + WebAppConfig.VIEW_RESOLVER_SUFFIX));
	}
}
