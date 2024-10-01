package com.safetynet.projet_5_safetynet_api.person;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.projet_5_safetynet_api.model.Person;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {
	
	Person person = new Person();
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	public MockMvc mockMvc;

	@Test
	public void testGetAllPersonsAndReturnOk() throws Exception {
		
		mockMvc.perform(get("/person"))
			.andExpect(status().isOk())
			.andReturn();
			//.andExpect(content().contentType(ListOfElements List<Person>));
		
	}
	
	@Test
	public void testPostANewPersonAndReturnCreated() throws Exception {
		
		person.setFirstName("test");
		person.setLastName("test");
		person.setAddress("test");
		person.setCity("test");
		person.setZip(0);
		person.setPhone("test");
		person.setEmail("test@test.fr");
		
		String requestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);
		
		mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(requestJson))
			.andExpect(status().isCreated())
			.andReturn();
		
	}
	
	@Test
	public void testUpdateAPersonAndReturnOk() throws Exception {
		
		testPostANewPersonAndReturnCreated();
		
		person.setAddress("try");
		person.setCity("try");
		person.setZip(11);
		person.setPhone("try");
		person.setEmail("try@try.fr");
		
		String requestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);
		
		mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(requestJson))
			.andExpect(status().isOk())
			.andReturn();
		
	}
	
	@Test
	public void testDeletedAPersonAndReturnNoContent() throws Exception {
		
		testPostANewPersonAndReturnCreated();
		
		String firstName = "test";
		String lastNameName = "test";
		
		mockMvc.perform(delete("/person")
				.param("firstName", firstName)
				.param("lastName", lastNameName))
			.andExpect(status().isOk())
			.andReturn();
		
	}

}
