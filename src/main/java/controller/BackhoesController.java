package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BackhoeLoader;
import model.Company;
import model.ModelException;
import model.dao.BackhoeDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {
    "/retros", 
    "/retro/form", 
    "/retro/delete", 
    "/retro/insert", 
    "/retro/update"
})
public class BackhoesController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        switch (action) {
            case "/retroescavadeiras/retro/form": {
                CommonsController.listCompanies(req);
                req.setAttribute("action", "insert");
                ControllerUtil.forward(req, resp, "/form-backhoe.jsp");
                break;
            }
            case "/retroescavadeiras/retro/update": {
                CommonsController.listCompanies(req);
                BackhoeLoader b = loadBackhoe(req);
                req.setAttribute("retroescavadeira", b);
                req.setAttribute("action", "update");
                ControllerUtil.forward(req, resp, "/form-backhoe.jsp");
                break;
            }
            default:
                listBackhoes(req);
                ControllerUtil.transferSessionMessagesToRequest(req);
                ControllerUtil.forward(req, resp, "/backhoeLoaders.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        if (action == null || action.isEmpty()) {
            ControllerUtil.forward(req, resp, "/backhoeLoaders.jsp");
            return;
        }

        switch (action) {
            case "/retroescavadeiras/retro/delete":
                deleteBackhoe(req, resp);
                break;
            case "/retroescavadeiras/retro/insert":
                insertBackhoe(req, resp);
                break;
            case "/retroescavadeiras/retro/update":
                updateBackhoe(req, resp);
                break;
            default:
                System.out.println("URL inválida " + action);
        }

        ControllerUtil.redirect(resp, req.getContextPath() + "/retros");
    }

    private void listBackhoes(HttpServletRequest req) {
        BackhoeDAO dao = DAOFactory.createDAO(BackhoeDAO.class);

        try {
            List<BackhoeLoader> list = dao.listAll();
            req.setAttribute("backhoeList", list);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private BackhoeLoader loadBackhoe(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        BackhoeDAO dao = DAOFactory.createDAO(BackhoeDAO.class);

        try {
            BackhoeLoader backhoe = dao.findById(id);
            if (backhoe == null)
                throw new ModelException("Retroescavadeira não encontrada.");
            return backhoe;
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
            return null;
        }
    }

    private void insertBackhoe(HttpServletRequest req, HttpServletResponse resp) {
        BackhoeLoader b = new BackhoeLoader();
        loadDataFromRequest(req, b);

        BackhoeDAO dao = DAOFactory.createDAO(BackhoeDAO.class);

        try {
            if (dao.save(b)) {
                ControllerUtil.sucessMessage(req, "Retroescavadeira '" + b.getModel() + "' salva com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Retroescavadeira não pôde ser salva.");
            }
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void updateBackhoe(HttpServletRequest req, HttpServletResponse resp) {
        BackhoeLoader b = loadBackhoe(req);
        if (b == null) return;

        loadDataFromRequest(req, b);

        BackhoeDAO dao = DAOFactory.createDAO(BackhoeDAO.class);

        try {
            if (dao.update(b)) {
                ControllerUtil.sucessMessage(req, "Retroescavadeira '" + b.getModel() + "' atualizada com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Retroescavadeira não pôde ser atualizada.");
            }
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void deleteBackhoe(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));

        BackhoeDAO dao = DAOFactory.createDAO(BackhoeDAO.class);

        try {
            BackhoeLoader b = dao.findById(id);
            if (b == null)
                throw new ModelException("Retroescavadeira não encontrada para deleção.");

            if (dao.delete(b)) {
                ControllerUtil.sucessMessage(req, "Retroescavadeira '" + b.getModel() + "' deletada com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Retroescavadeira não pôde ser deletada.");
            }
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void loadDataFromRequest(HttpServletRequest req, BackhoeLoader b) {
        String model = req.getParameter("model");
        int year = Integer.parseInt(req.getParameter("year"));
        boolean status = "on".equals(req.getParameter("status"));
        int companyId = Integer.parseInt(req.getParameter("company"));

        Company company = new Company(companyId);

        b.setModel(model);
        b.setYear(year);
        b.setStatus(status);
        b.setCompany(company);
    }
}
