package model;

import java.util.Date;

public class Company {
	private int id;
	private String name;
	private String cnpj;
	private String razao_social;
	
	public Company() {}
	
	public Company(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazao_social() {
		return razao_social;
	}

	public void setRazao_social(String razao_social) {
		this.razao_social = razao_social;
	}

	public int getId() {
		return id;
	}
}
