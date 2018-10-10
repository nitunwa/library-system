package com.systemlibrary.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.systemlibrary.models.User;
import com.systemlibrary.models.UserDao;

/* @RunWith(MockitoJUnitRunner.class) use MockitoJUnitRunner.class to run the test case on  MockitoJUnit  Platform.
 * @RunWith(JUnitPlatform.class)  let us run JUnit4 tests on the JUnit Platform(library).
 *  */

@RunWith(MockitoJUnitRunner.class)  //
public class AdminControllerTest {
	@InjectMocks
	AdminController adminController = new AdminController();
	@Mock
	private UserDao userDao;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
	}

	@Ignore
	@Test
	public void testShowDashboard() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateUser() throws Exception {
		doNothing().when(userDao).create(any(User.class));
		mockMvc.perform(post("/admin/createuser?name=sujan&role=admin&password=123&email=sujan@gmail.com")
				.accept(MediaType.ALL))
		        .andExpect(status().isFound())
		        .andExpect(view().name("redirect:/admin/userListReport"));
	}

	@Test
	public void testCreateUserNullTest() throws Exception {
		doNothing().when(userDao).create(any(User.class));
		mockMvc.perform(post("/admin/createuser?name=sujan&role=admin&password=&email=sujan@gmail.com")
				.accept(MediaType.ALL))
		        .andExpect(status().isOk())
		        .andExpect(view().name("admin/createUser"));
	}

	@Ignore
	@Test
	public void testCreate() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testUserListReport() {

	}

}
