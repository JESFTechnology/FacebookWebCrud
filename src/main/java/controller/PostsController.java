package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		String postIdStr = req.getParameter("post_id");
		int postId = Integer.parseInt(postIdStr);
		
		Post post = new Post(postId);
		
		PostDAO postDAO = DAOFactory.createDAO(PostDAO.class);	
		try {
			postDAO.delete(post);
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
	}
	
	private void updatePost(HttpServletRequest req, int postId){
		Post post = fillPost(req, postId);
		
		// Criar um UserDAO e atualiza o user
		PostDAO postDAO = DAOFactory.createDAO(PostDAO.class);	
		try {
			postDAO.update(post);
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
	}
	
	private void insertPost(HttpServletRequest req) {
		Post post = fillPost(req, null);
		
		// Criar um UserDAO e persistir o user
		PostDAO postDAO = DAOFactory.createDAO(PostDAO.class);	
		try {
			postDAO.save(post);
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
	}
	
	private Post fillPost(HttpServletRequest req, Integer postId) {
		// Recuperar os dados do form
		String postContent = req.getParameter("post_content");
		String postPostDate = req.getParameter("post_date");
		String postIDUser = req.getParameter("user_id");
		
		// Criar um User a partir dos dados do form
		Post post;
		if (postId == null) post = new Post();
		else post = new Post(postId);
		
		post.setContent(postContent);
		
		Date postDate = null;
		if (postPostDate != null && !postPostDate.trim().isEmpty()) {
		    try {
		        // Formato DD/MM/YYYY comum em BR
		        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		        sdf.setLenient(false); // Não aceita datas inválidas
		        postDate = sdf.parse(postPostDate);
		    } catch (ParseException e) {
		        // Log do erro e usar data atual como fallback
		        System.err.println("Erro ao parsear data: " + postPostDate + " - " + e.getMessage());
		        postDate = new Date(); // Data atual como fallback
		    }
		}

		post.setPostDate(postDate);
		post.setUser(new User(Integer.parseInt(postIDUser)));
		
		// Criptografando a senha (Emerson@Senha)
		
		return post;
	}

}
