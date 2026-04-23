package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;
import model.utils.PasswordEncryptor;

@WebServlet(urlPatterns = {"/posts", "/posts/save", "/posts/update", "/posts/delete"})
public class PostsController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		System.out.println(action);
		
		switch (action) {
			case "/facebook/posts": {
				loadPosts(req);
				RequestDispatcher rd = req.getRequestDispatcher("/posts/posts.jsp");
				rd.forward(req, resp);
				break;
			}case "/facebook/posts/save" : {
				String postIdStr = req.getParameter("post_id");
				if (postIdStr == null || postIdStr == "") {
					insertPost(req);
				} else {
					int postId = Integer.parseInt(postIdStr);
					updatePost(req, postId);
				}
				
				resp.sendRedirect("/facebook/posts");
				break;
			} case "/facebook/posts/update": {
				
				loadPosts(req);
				
				RequestDispatcher rd = 
						req.getRequestDispatcher("/posts/form_user.jsp");		
				rd.forward(req, resp);
				break;
			} case "/facebook/posts/delete": {	
				// Deletar o usuário
				deletePost(req);
				// Redirecionar para a listagem de usuário
				resp.sendRedirect("/facebook/posts");
				break;
			} 
			default:
				throw new IllegalArgumentException("Unexpected value: " + action);
			}
		}
	}
	
	private void loadPosts(HttpServletRequest req) {
		PostDAO postDAO = DAOFactory.createDAO(PostDAO.class);
		
		List<Post> posts = List.of();
		
		try {
			posts = postDAO.listAll();
		} catch (Exception e) {
			e.printStackTrace(); // Só loga no servidor
		}
		
		// Adiciona a lista de usuários na requisição original
		req.setAttribute("posts", posts);
	}
	
	private void deletePost(HttpServletRequest req) {
		String postIdStr = req.getParameter("postId");
		int postId = Integer.parseInt(postIdStr);
		
		User user = new User(postId);
		
		UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);	
		try {
			userDAO.delete(user);
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
	}

}
