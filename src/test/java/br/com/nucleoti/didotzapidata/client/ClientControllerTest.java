package br.com.nucleoti.didotzapidata.client;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nucleoti.didotz.DidotzApiDataApplication;
import br.com.nucleoti.didotz.client.Client;
import br.com.nucleoti.didotz.client.ClientRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DidotzApiDataApplication.class)
@WebAppConfiguration
public class ClientControllerTest {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    
    private Client client1, client2;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private String json(Object obj) throws JsonProcessingException {
    	return mapper.writeValueAsString(obj);
    }
    
    @Before
    public void setup() throws Exception {
    	
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        
        this.client1 = new Client("client1", "Client Number One", "000.000.000-00");
        this.client2 = new Client("client2", "Client Number Two", "000.000.000-00");
        
        this.clientRepository.deleteAll();
        this.clientRepository.save(client1);
        this.clientRepository.save(client2);
    }
    
    @Test public void
    get_client_and_return_ok() throws Exception {
    	mockMvc.perform(get("/clients/" + client1.getId()))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(contentType))
    		.andExpect(jsonPath("$.id", is(client1.getId())))
			.andExpect(jsonPath("$.name", is(client1.getName())))
			.andExpect(jsonPath("$.cpf", is(client1.getCpf())));
    }

    @Test public void
    get_client_and_return_not_found() throws Exception {
    	mockMvc.perform(get("/clients/NonExistentClient"))
    		.andExpect(status().isNotFound());
    }
    
    @Test public void 
    get_all_clients_and_return_ok() throws Exception {
    	mockMvc.perform(get("/clients"))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(contentType))
    		.andExpect(jsonPath("$[0].id", is(client1.getId())))
			.andExpect(jsonPath("$[0].name", is(client1.getName())))
			.andExpect(jsonPath("$[0].cpf", is(client1.getCpf())))
			.andExpect(jsonPath("$[1].name", is(client2.getName())))
			.andExpect(jsonPath("$[1].id", is(client2.getId())))
			.andExpect(jsonPath("$[1].cpf", is(client2.getCpf())));
    }
    
    @Test public void 
    add_client_and_get_it_and_return_ok() throws Exception {
    	Client newClient = new Client("user3", "New Client", "123.123.123-12");
    	String clientJson = json(newClient);
    	
    	mockMvc.perform(post("/clients")
    		.content(clientJson)
    		.contentType(contentType))
    		.andExpect(status().isOk());
    	
    	mockMvc.perform(get("/clients/" + newClient.getId()))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(contentType))
    		.andExpect(jsonPath("$.id", is(newClient.getId())))
			.andExpect(jsonPath("$.name", is(newClient.getName())))
			.andExpect(jsonPath("$.cpf", is(newClient.getCpf())));
    }
   
    @Test public void 
    update_client_and_get_it_and_return_ok() throws Exception {
    	client1.setName("Updated Client");
    	client1.setCpf("123.123.123-12");
    	String clientJson = json(client1);
    	
    	mockMvc.perform(put("/clients/" + client1.getId())
    		.content(clientJson)
    		.contentType(contentType))
    		.andExpect(status().isOk());
    	
    	mockMvc.perform(get("/clients/" + client1.getId()))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(contentType))
    		.andExpect(jsonPath("$.id", is(client1.getId())))
			.andExpect(jsonPath("$.name", is(client1.getName())))
			.andExpect(jsonPath("$.cpf", is(client1.getCpf())));
    }
    

    @Test public void
    delete_client_and_get_it_and_return_not_found() throws Exception {
    	mockMvc.perform(get("/clients/" + client1.getId()))
			.andExpect(status().isOk());
    	
    	mockMvc.perform(delete("/clients/" + client1.getId()))
        		.andExpect(status().isOk());

    	mockMvc.perform(get("/clients/" + client1.getId()))
			.andExpect(status().isNotFound());
    }
}
