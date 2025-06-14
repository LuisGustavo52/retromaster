<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Retroescavadeiras - Login</title>
</head>
<body>
	<%@include file="modal.html"%>
	<%@include file="nav-menu.jsp"%>
	
    <div class="d-flex flex-column min-vh-100 bg-light">
      <!-- Navbar -->
      <nav class="navbar navbar-dark bg-dark">
        <div class="container">
          <span class="navbar-brand mb-0 h1">retroescavadeiras CRUD</span>
        </div>
      </nav>

      <main class="flex-grow-1 d-flex justify-content-center align-items-center">
        <div class="card shadow-sm p-4" style="width: 100%; max-width: 400px;">
          <h2 class="text-center mb-4">Login</h2>
          <form action="/retroescavadeiras/login" method="POST">
            <div class="mb-3">
              <label for="user_login_id" class="form-label">Login (e-mail)</label>
              <input type="text" class="form-control" id="user_login_id" name="user_login" required />
            </div>
            
            <div class="mb-3">
              <label for="user_pw_id" class="form-label">Senha</label>
              <input type="password" class="form-control" id="user_pw_id" name="user_pw" required />
            </div>
            
            <div class="d-grid mb-3">
              <button type="submit" class="btn btn-dark btn-lg">Logar</button>
            </div>
            
            <c:if test="${param.erro == 'true'}">
			    <span class="text-danger small">Usuário ou senha inválidos.</span>
			</c:if>
          </form>
        </div>
      </main>

    </div>
  </body>
</html>