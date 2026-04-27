<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
	<title>Postagens</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-1"></div>
			
			<div class="col-md-10">
				<h1>Lista de Postagens</h1>
				
				<div class="mb-3">
					<a class="bi bi-house"
			           href="${pageContext.request.contextPath}/">Home</a>
				</div>
				
				<table class="table table-striped table-hover">
				  <thead>
				    <tr>
				      <th scope="col">#</th>	
				      <th scope="col">Descrição</th>
				      <th scope="col">Data da postagem</th>
				      <th scope="col">Usuário</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach var="posts" items="${posts}">
					    <tr>
					      <td>${posts.getId()}</td>
					      <td>${posts.getContent()}</td>
					      <td>${posts.getPostDate()}</td>
					      <td>${posts.getUser().getName()}</td>
					      <td>
					      	<a class="bi bi-pencil-square" 
					      	   href="${pageContext.request.contextPath}/posts/update?postId=${posts.getId()}"></a>
					      	
					      	<a class="bi bi-trash"
					      	   href="${pageContext.request.contextPath}/posts/delete?postId=${posts.getId()}"></a>
					      </td>
					    </tr>
					</c:forEach>
				  </tbody>
				</table>
				
				<a href="${pageContext.request.contextPath}/posts/form_post" 
				   class="btn btn-primary">
					Cadastrar Postagem
				</a>	
			</div>
			
			<div class="col-md-1"></div>
		</div>	
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>