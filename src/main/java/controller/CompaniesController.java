package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.ModelException;
import model.User;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/companies", "/company/form", 
		"/company/insert", "/company/delete", "/company/update"})
public class CompaniesController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/retroescavadeiras/company/form": {
			CommonsController.listUsers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-company.jsp");			
			break;
		}
		case "/retroescavadeiras/company/update": {
			String idStr = req.getParameter("companyId");
			int idCompany = Integer.parseInt(idStr); 
			
			CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
			
			Company company = null;
			try {
				company = dao.findById(idCompany);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			CommonsController.listUsers(req);
			req.setAttribute("action", "update");
			req.setAttribute("company", company);
			ControllerUtil.forward(req, resp, "/form-company.jsp");
			break;
		}
		default:
			listCompanies(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/companies.jsp");
		}
	}
	
	private void listCompanies(HttpServletRequest req) {
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		List<Company> companies = null;
		try {
			companies = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (companies != null)
			req.setAttribute("companies", companies);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/retroescavadeiras/company/insert": {
			insertCompany(req, resp);			
			break;
		}
		case "/retroescavadeiras/company/delete" :{
			
			deleteCompany(req, resp);
			
			break;
		}
		
		case "/retroescavadeiras/company/update" :{
			updateCompany(req, resp);
			break;
		}
		
		default:
			System.out.println("URL inválida " + action);
		}
		
		ControllerUtil.redirect(resp, req.getContextPath() + "/companies");
	}

	private void updateCompany(HttpServletRequest req, HttpServletResponse resp) {
		String companyIdStr = req.getParameter("companyId");
		String companyName = req.getParameter("name");
		String cnpj = req.getParameter("cnpj");
		String razao_social = req.getParameter("razao_social");
		
		Company company = new Company(Integer.parseInt(companyIdStr));
		company.setName(companyName);
		company.setCnpj(cnpj);
		company.setRazao_social(razao_social);
		
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		try {
			if (dao.update(company)) {
				ControllerUtil.sucessMessage(req, "Empresa '" + 
						company.getName() + "' atualizada com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empresa '" + 
						company.getName() + "' não pode ser atualizada.");
			}				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}		
	}

	private void deleteCompany(HttpServletRequest req, HttpServletResponse resp) {
		String companyIdParameter = req.getParameter("id");
		
		int companyId = Integer.parseInt(companyIdParameter);
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		try {
			Company company = dao.findById(companyId);
			
			if (company == null)
				throw new ModelException("Empresa não encontrada para deleção.");
			
			if (dao.delete(company)) {
				ControllerUtil.sucessMessage(req, "Empresa '" + 
						company.getName() + "' deletada com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empresa '" + 
						company.getName() + "' não pode ser deletado. "
								+ "Há dados relacionados à empresa.");
			}
		} catch (ModelException e) {
			// log no servidor
			if (e.getCause() instanceof 
					SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertCompany(HttpServletRequest req, HttpServletResponse resp) {
		String companyIdStr = req.getParameter("companyId");
		String companyName = req.getParameter("name");
		String cnpj = req.getParameter("cnpj");
		String razao_social = req.getParameter("razao_social");
		
		Company comp = new Company();
		comp.setName(companyName);
		comp.setCnpj(cnpj);
		comp.setRazao_social(razao_social);
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
	
		try {
			if (dao.save(comp)) {
				ControllerUtil.sucessMessage(req, "Empresa '" + comp.getName() 
				+ "' salva com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empresa '" + comp.getName()
				+ "' não pode ser salva.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
}
