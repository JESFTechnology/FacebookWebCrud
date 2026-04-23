<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
	<title>Cadastro de Postagem</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-3"></div>
			
			<div class="col-md-6">
				<form action="${pageContext.request.contextPath}/posts/save" 
					  method="GET">
					<h1>${post eq null ? "Criar" : "Atuializar"} um Post</h1>
					
					<div class="mb-3">
						<a class="bi bi-person"
				           href="${pageContext.request.contextPath}/posts">Postagens</a>
					</div>
					
					<input type="hidden" 
					       name="user_id"
					       value="${post.getUser().getId()}">
					       
					<input type="hidden" 
					       name="post_id"
					       value="${post.getId()}"> 
					
					<div class="mb-3">
						<label for="post_content_id" class="form-label">
							Olá ${post.getUser().getName()}, vamos fazer um post?
						</label>
						<textarea type="textarea" 
						       id="post_content_id"
						       name="post_content"
						       class="form-control"
						       placeholder="Sua postagem"
						       value="" rows="5"></textarea>
					</div>
					
					<div class="mb-3">
						<label for="post_datetime_id" class="form-label">
							Data do post
						</label>
						<input id="post_datetime_id" name="post_datetime" type="date" class="form-control">
					</div>
					<script>
					  // Get current date/time in local format
					  const now = new Date();
					  
					  // Format as YYYY-MM-DDTHH:mm
					  const year = now.getFullYear();
					  const month = String(now.getMonth() + 1).padStart(2, '0');
					  const day = String(now.getDate()).padStart(2, '0');
					  
					  const formattedDateTime = year+"-"+month+"-"+day;
					  
					  // Set the value
					  document.getElementById('post_datetime_id').value = formattedDateTime;
					</script>

					<!-- 
					<div class="mb-3">
					  <label for="formFile" class="form-label">Uma foto para esse post?</label>
					  <input class="form-control" type="file" accept=".png" id="formFile">
					</div>
					 -->
					
					${post eq null ? "" : "" }
		
					<button type="submit" class="btn btn-primary">
						${post eq null ? "Postar" : "Re-Postar"}
					</button>
				</form>
			</div>
			
			<div class="col-md-3"></div>
		</div>	
	</div>
</body>
</html>