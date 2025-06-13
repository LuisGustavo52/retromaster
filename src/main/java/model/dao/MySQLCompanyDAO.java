package model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Company;
import model.ModelException;
import model.User;

public class MySQLCompanyDAO implements CompanyDAO {

	@Override
	public boolean save(Company company) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO companies VALUES (DEFAULT, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		
		db.setString(1, company.getName());
		db.setString(2, company.getCnpj());
		db.setString(3, company.getRazao_social());
				
		return db.executeUpdate() > 0;	
	}

	@Override
	public boolean update(Company company) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE companies "
				+ " SET name = ?, "
				+ " cnpj = ?, "
				+ " razao_social = ? "
				+ " WHERE id = ?; "; 
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, company.getName());
		db.setString(2, company.getCnpj());
		
		db.setString(3, company.getRazao_social()
				);

		db.setInt(4, company.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Company company) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM companies "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, company.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Company> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		
		List<Company> companies = new ArrayList<Company>();
			
		// Declara uma instrução SQL
		String sqlQuery = " SELECT * from companies";
		
		db.createStatement();
	
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Company company = new Company(db.getInt("id"));
			company.setName(db.getString("name"));
			company.setCnpj(db.getString("cnpj"));
			company.setRazao_social(db.getString("razao_social"));
			
			companies.add(company);
		}
		
		return companies;
	}

	@Override
	public Company findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sql = "SELECT * FROM companies WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Company company = null;
		
		while (db.next()) {
			company = new Company(db.getInt("id"));
			company.setName(db.getString("name"));
			company.setCnpj(db.getString("cnpj"));
			company.setRazao_social(db.getString("razao_social"));			
			break;
		}
		
		return company;
	}
}
