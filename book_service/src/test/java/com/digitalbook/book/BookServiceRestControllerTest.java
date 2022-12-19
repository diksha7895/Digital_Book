package com.digitalbook.book;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.sql.Blob;
import java.util.Date;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import com.digitalbook.book.model.BookInfo;
import com.digitalbook.book.response.BookContentResponse;
import com.digitalbook.book.response.BookWithByteFile;
import com.digitalbook.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;



@RunWith(SpringRunner.class)

@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
public class BookServiceRestControllerTest {
/*	
	@Autowired
	private WebApplicationContext webApplicationContext;

	
	@Autowired
	private MockMvc mockMvc;
	
	
	@MockBean
	private BookService bookService;
	
	//private ObjectMapper obj = new ObjectMapper();
	
	@Before
	public void setUp() {
		 this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}


	@Test
	public void testGetBook() throws Exception {
		BookInfo books = new BookInfo();
		mockMvc.perform(get("/digitalbooks/searchBook/Novel/HarryPotter/1/345/Rowling")).andExpect(status().isOk());
	}
	
	@Test
	public void testGetBookForSubscribedBook() throws Exception{
		BookInfo books = new BookInfo();
		books.setTitle("title");
		books.setAuthorId(11);
		books.setPrice(550);
	//	books.setLogo(null);
		books.setPublisher("Chetan Bhagat");
		books.setPublishedOn(new Date());
		books.setContent("abcdefghijklm");
		books.setActive(false);
		when(bookService.getSubscribedBook(2)).thenReturn(books);
		mockMvc.perform(get("/digitalbooks/getBook/subscribed/1")).andExpect(jsonPath("$.title").value("title"));
		
	}
	
	@Test
	public void testSubscribedBookContent() throws Exception{
		BookContentResponse resp = new BookContentResponse();
		resp.setTitle("HarryPotter");
		resp.setContent("Book Content");
		resp.setActive(true);
		when(bookService.getSubscribedBookContent(7)).thenReturn(resp);
		mockMvc.perform(get("/digitalbooks/getBook/subscribed/content/1")).andExpect(jsonPath("$.content").value("Book Content"));
	}

	@Test
/*	public void testUpdateBook() throws Exception{
		BookWithByteFile bookImg = new BookWithByteFile();
		BookInfo books = new BookInfo();
		books.setTitle("title");
		books.setAuthorId(11);
		books.setPrice(550);
		books.setLogo(null);
		books.setPublisher("Chetan Bhagat");
		books.setPublishedOn(new Date());
		books.setContent("abcdefghijklm");
		books.setActive(false);
		
		byte[] file = new byte[1024];
		bookImg.setFile(file);
		bookImg.setBooks(books);
		
	when(bookService.updateBook(any(Blob.class), any(BookWithByteFile.class), anyInt() , anyInt())).thenReturn(bookImg.getBooks());
	
		mockMvc.perform(post("/digitalbooks/author/1/1").content(asJsonString(new BookWithByteFile(file,books)))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content").value("abcdefghijklm"));
		
	}

	private static String asJsonString(final Object o ) {
			try {
			return new ObjectMapper().writeValueAsString(o);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	*/
}
