package br.com.nucleoti.didotz.client;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {
	
	@Id
	private String id;
	private String name;
	private String cpf;
	private Double points;
	
	public Client() {
		
	}
	
	public Client(String id, String name, String cpf) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.points = 0.0;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}
}
