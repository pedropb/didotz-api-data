package br.com.nucleoti.didotzapidata.client;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static io.restassured.module.mockmvc.matcher.RestAssuredMockMvcMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.nucleoti.didotz.DidotzApiDataApplication;
import br.com.nucleoti.didotz.client.Client;
import br.com.nucleoti.didotz.client.ClientRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DidotzApiDataApplication.class)
@AutoConfigureMockMvc
public class ClientControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
    @Autowired
    private ClientRepository clientRepository;

	private Client user1, user2;

	
	@Before
	public void setup() {
		this.user1 = new Client("user1", "User Number One", "000.000.000-00");
        this.user2 = new Client("user2", "User Number Two", "000.000.000-00");
        
        this.clientRepository.deleteAll();
        this.clientRepository.save(user1);
        this.clientRepository.save(user2);
		
		RestAssuredMockMvc.mockMvc(mockMvc);
	}
	
	@Test
	public void getClientById() {
		when().
			get("/clients/{id}", user1.getId()).
		then().
			statusCode(200).
			body("id", equalTo(user1.getId()));
	}
	
	@Test
	public void clientNotFound() {
		when().
			get("/clients/{id}", "3").
		then().
			statusCode(404).
			body(isEmptyOrNullString());
	}
	
	@Test
	public void getAllClients() {
		fail("Test not implemented");
	}
	
	@Test
	public void addClient() {
		fail("Test not implemented");
	}
	
	@Test
	public void updateClient() {
		fail("Test not implemented");
	}
	
	@Test
	public void deleteClient() {
		fail("Test not implemented");
	}
	
	
}
