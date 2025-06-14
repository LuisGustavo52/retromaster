package model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.BackhoeLoader;
import model.Company;
import model.ModelException;
import model.BackhoeLoader;
import model.User;

public class MySQLBackhoeDAO implements BackhoeDAO {

	@Override
	public boolean save(BackhoeLoader backhoe) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO retros VALUES "
				+ " (DEFAULT, ?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, backhoe.getModel());
		db.setInt(2, backhoe.getCompany().getId());
		db.setInt(3, backhoe.getYear());
		db.setBoolean(4, backhoe.isStatus());
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(BackhoeLoader backhoe) throws ModelException {

	    DBHandler db = new DBHandler();

	    String sqlUpdate = "UPDATE retros SET model = ?, company_id = ?, year = ?, status = ? WHERE id = ?;";

	    db.prepareStatement(sqlUpdate);
	    db.setString(1, backhoe.getModel());
	    db.setInt(2, backhoe.getCompany().getId());
	    db.setInt(3, backhoe.getYear());
	    db.setBoolean(4, backhoe.isStatus());
	    db.setInt(5, backhoe.getId()); 

	    return db.executeUpdate() > 0;
	}


	@Override
	public boolean delete(BackhoeLoader backhoe) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM retros "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, backhoe.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<BackhoeLoader> listAll() throws ModelException {

	    DBHandler db = new DBHandler();

	    List<BackhoeLoader> backhoes = new ArrayList<>();

	    String sqlQuery = "SELECT r.*, c.id AS company_id "
	                    + "FROM retros r "
	                    + "INNER JOIN companies c ON r.company_id = c.id "
	                    + "ORDER BY r.model";

	    db.createStatement();
	    db.executeQuery(sqlQuery);

	    while (db.next()) {
	        BackhoeLoader backhoe = createBackhoeLoader(db);
	        backhoes.add(backhoe);
	    }

	    return backhoes;
	}

	@Override
	public BackhoeLoader findById(int id) throws ModelException {
		
		DBHandler db = new DBHandler();
				
		String sql = "SELECT * FROM retros WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		BackhoeLoader p = null;
		while (db.next()) {
			p = createBackhoeLoader(db);
			break;
		}
		
		return p;
	}
	
	private BackhoeLoader createBackhoeLoader(DBHandler db) throws ModelException {
		BackhoeLoader b = new BackhoeLoader(db.getInt("id"));
		b.setModel(db.getString("model"));
		b.setYear(db.getInt("year"));
		b.setStatus(db.getBoolean("status"));
		
		CompanyDAO companyDAO = DAOFactory.createDAO(CompanyDAO.class); 
		
		Company company = companyDAO.findById(db.getInt("company_id"));
		b.setCompany(company);
		
		return b;
	}

}
