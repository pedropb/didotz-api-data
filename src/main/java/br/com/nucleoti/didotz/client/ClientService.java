package br.com.nucleoti.didotz.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;

	public List<Client> getAllClients() {
		List<Client> clients = new ArrayList<>();
		clientRepository.findAll()
		.forEach(clients::add);
		return clients;
	}
	
	public Client getClient(String id) {
		return clientRepository.findOne(id);
	}
	
	public void addClient(Client client) {
		clientRepository.save(client);		
	}
	
	public void updateClient(Client client) {
		clientRepository.save(client);
	}
	
	public void deleteClient(String id) {
		clientRepository.delete(id);
	}
	
}
