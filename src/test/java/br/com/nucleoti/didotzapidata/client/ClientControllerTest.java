package br.com.nucleoti.didotzapidata.client;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.nucleoti.didotz.client.Client;
import br.com.nucleoti.didotz.DidotzApiDataApplication;
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

	private Client client1, client2;

	
	@Before
	public void setup() {
		this.client1 = new Client("client1", "Client Number One", "000.000.000-00");
        this.client2 = new Client("client2", "Client Number Two", "000.000.000-00");
        
        this.clientRepository.deleteAll();
        this.clientRepository.save(client1);
        this.clientRepository.save(client2);
		
		RestAssuredMockMvc.mockMvc(mockMvc);
	}
	
	@Test
	public void getClientById() {
		when().
			get("/clients/{id}", client1.getId()).
		then().
			statusCode(200).
			body("id", equalTo(client1.getId()));
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
		when().
			get("/clients").
		then().
			statusCode(200).
			body("id", hasItems(client1.getId(), client2.getId()));
			
	}
	
	@Test
	public void addClient() {
		Client clientToBeAdded = new Client("client3", "Client Added", "some cpf"); 
		
		// create client
		given().
			contentType("application/json").
			body(clientToBeAdded).
		when().
			post("/clients").
		then().
			statusCode(200);
		
		// check if client was added
		when().
			get("/clients/{id}", clientToBeAdded.getId()).
		then().
			statusCode(200).
			body("id", equalTo(clientToBeAdded.getId()));
	}
	
	@Test
	public void updateClient() {
		Client clientToBeUpdated = new Client("client1", "Name Updated", "updated cpf"); 
		
		// update client
		given().
			contentType("application/json").
			body(clientToBeUpdated).
		when().
			put("/clients/{id}", clientToBeUpdated.getId()).
		then().
			statusCode(200);
		
		// check if client was updated
		when().
			get("/clients/{id}", clientToBeUpdated.getId()).
		then().
			statusCode(200).
			body("name", equalTo(clientToBeUpdated.getName())).
			body("cpf", equalTo(clientToBeUpdated.getCpf()));
	}
	
	@Test
	public void deleteClient() {
		// delete client
		when().
			delete("/clients/{id}", client1.getId()).
		then().
			statusCode(200);
		
		// check if client was deleted
		when().
			get("/clients/{id}", client1.getId()).
		then().
			statusCode(404).
			body(isEmptyOrNullString());
	}
	
	
}
