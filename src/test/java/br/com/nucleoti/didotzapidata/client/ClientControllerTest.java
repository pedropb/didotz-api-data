package br.com.nucleoti.didotzapidata.client;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

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
    
    private Client user1, user2;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        
        this.user1 = new Client("user1", "User Number One", "000.000.000-00");
        this.user2 = new Client("user2", "User Number Two", "000.000.000-00");
        
        this.clientRepository.save(user1);
        this.clientRepository.save(user2);
    }
    
    @Test
    public void readSingleClient() throws Exception {
    	mockMvc.perform(get("/clients/" + user1.getId()))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(contentType))
    		.andExpect(jsonPath("$.id", is(user1.getId())))
			.andExpect(jsonPath("$.name", is(user1.getName())))
			.andExpect(jsonPath("$.cpf", is(user1.getCpf())));
    }
    
    @Test
    public void readAllClients() throws Exception {
    	mockMvc.perform(get("/clients"))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(contentType))
    		.andExpect(jsonPath("$.id", is(user1.getId())))
			.andExpect(jsonPath("$.name", is(user1.getName())))
			.andExpect(jsonPath("$.cpf", is(user1.getCpf())));
    }

}
