package br.com.nucleoti.didotzapidata.client;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
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
    		.andExpect(jsonPath("$[0].id", is(user1.getId())))
			.andExpect(jsonPath("$[0].name", is(user1.getName())))
			.andExpect(jsonPath("$[0].cpf", is(user1.getCpf())))
			.andExpect(jsonPath("$[1].name", is(user2.getName())))
			.andExpect(jsonPath("$[1].id", is(user2.getId())))
			.andExpect(jsonPath("$[1].cpf", is(user2.getCpf())));
    }
    
//    @Test
//    public void addClient() throws Exception {
//    	String clientToBeAdded = json(new Client("user3", "User Number Three", "123.123.123-12"));
//    	
//    	mockMvc.perform(post("/clients", ))
//    }
//    
//
//    @Test
//    public void updateClient() throws Exception {
//    	
//    }
    

    @Test
    public void deleteClient() throws Exception {
    	
    }
    

    // Methods and attributes for the logic responsible to convert an object to JSON string
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
