package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.ModelException;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/companies"})
public class CompaniesController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		// Carregar as empresas
		// Colocar no contexto da requisição
		loadCompanies(req);
		
		// Redirecionar para companies.jsp
		ControllerUtil.forward(req, resp, "companies.jsp");
	}

	private void loadCompanies(HttpServletRequest req) {
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		List<Company> companies = List.of();
		try {
			//companies = dao.listAll();
			throw new ModelException("Erro intencional para teste de alerts.");
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, 
					"Houve um erro ao carregar os dados das empresas.");
		}
		
		req.setAttribute("listaEmpresas", companies);
	}
}
