<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">

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
				<form action="/facebook/posts/save" method="post">
					<h1>${post == null ? "Criar" : "Atualizar"} um Post</h1>

					<div class="mb-3">
						<a class="bi bi-person" href="${pageContext.request.contextPath}/posts">Posts</a>
					</div>

					<input type="hidden" name="post_id" value="${post.id}">

					<div class="mb-3">
						<label class="form-label">Usuário</label>
						<select name="user_id" class="form-select" required>
							<option value="">Selecione o autor</option>
							<c:forEach var="u" items="${usuarios}">
								<option value="${u.id}" ${post.user.id==u.id ? 'selected' : '' }>
									${u.name}
								</option>
							</c:forEach>
						</select>
					</div>

					<div class="mb-3">
						<label class="form-label">Conteúdo da Postagem</label>
						<textarea name="content" class="form-control" rows="5"
							required>${post.content}</textarea>
					</div>

					<button type="submit" class="btn btn-primary">
						${post == null ? "Postar" : "Re-Postar"}
					</button>

				</form>
			</div>

			<div class="col-md-3"></div>
		</div>
	</div>
</body>

</html>