package com.digitalbook.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.digitalbook.book.model.BookInfo;
import com.digitalbook.user.service.UserService;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;


@RunWith(SpringRunner.class)

@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
public class UserAuthControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private UserService userServiceMock;
	
	@MockBean
	private AuthenticationManager authenticationManager;
	
	@MockBean
	RestTemplate restTemplate;
	
	@Before
	public void setUp() {
		 this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	

	@Test
	void testSignUp() throws Exception {
		
		
	}
	
	@Test
	void testSearchBook() throws Exception{
		RestTemplate restTemplate = new RestTemplate();
		double price = 345;
		final String url = "http://localhost:8081/digitalbooks/searchBook/Novel/HarryPotter/1/"+price+"/Rowling";
		URI newurl =new URI(url);
		
		BookInfo data = restTemplate.getForObject(newurl, BookInfo.class);
		Assert.assertNotNull(data);
		
		
	}

}
