//package com.demo.application.controller;
//
//import java.util.Arrays;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.demo.application.service.ContactRepository;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//// @ContextConfiguration(classes = { TestContext.class, WebAppContext.class })
//@WebAppConfiguration
//public class HomeControllerTest {
//
//	private MockMvc mockMvc;
//
//	@Autowired
//	private ContactRepository contactServiceMock;
//
//	 @Test
//	 public void getAllContacts_ShouldAddTodoEntriesToModelAndRenderTodoListView() throws Exception {
//
//	 when(todoServiceMock.findAll()).thenReturn(Arrays.asList(first, second));
//	
//	 mockMvc.perform(get("/"))
//	 .andExpect(status().isOk())
//	 .andExpect(view().name("todo/list"))
//	 .andExpect(forwardedUrl("/WEB-INF/jsp/todo/list.jsp"))
//	 .andExpect(model().attribute("todos", hasSize(2)))
//	 .andExpect(model().attribute("todos", hasItem(
//	 allOf(
//	 hasProperty("id", is(1L)),
//	 hasProperty("description", is("Lorem ipsum")),
//	 hasProperty("title", is("Foo"))
//	 )
//	 )))
//	 .andExpect(model().attribute("todos", hasItem(
//	 allOf(
//	 hasProperty("id", is(2L)),
//	 hasProperty("description", is("Lorem ipsum")),
//	 hasProperty("title", is("Bar"))
//	 )
//	 )));
//	
//	 verify(todoServiceMock, times(1)).findAll();
//	 verifyNoMoreInteractions(todoServiceMock);
//	 }
//
//}
