package model;

import java.util.Date;

public class BackhoeLoader {
	private int id;
	private Company company;
	private boolean status;
	private String model;
	private int year;
	
	public BackhoeLoader() {}
	
	public BackhoeLoader(int id) {
		this.id = id;
	}
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getId() {
		return id;
	}
}
