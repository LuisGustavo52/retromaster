package model.dao;

import java.util.List;

import model.ModelException;
import model.BackhoeLoader;
import model.Company;

public interface BackhoeDAO {
	boolean save(BackhoeLoader backhoe) throws ModelException;
	boolean update(BackhoeLoader backhoe) throws ModelException;
	boolean delete(BackhoeLoader backhoe) throws ModelException;
	List<BackhoeLoader> listAll() throws ModelException;
	BackhoeLoader findById(int id) throws ModelException;
}
