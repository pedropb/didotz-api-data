package br.com.nucleoti.didotz.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.nucleoti.didotz.exception.NotFoundException;

@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@GetMapping("/clients")
	public List<Client> getAllClients() {
		return clientService.getAllClients();
	}
	
	@GetMapping("/clients/{id}")
	public Client getClient(@PathVariable String id) {
		Client client = clientService.getClient(id);
		if (client != null)
			return client;
		else
			throw new NotFoundException();
	}
	
	@PostMapping("/clients")
	public void addClient(@RequestBody Client client) {
		clientService.addClient(client);
	}

	@PutMapping("/clients/{id}")
	public void updateClient(@PathVariable String id, @RequestBody Client client) {
		clientService.updateClient(client);
	}
	
	@DeleteMapping("clients/{id}")
	public void deleteClient(@PathVariable String id) {
		clientService.deleteClient(id);
	}
}
