<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="modal.html"%>
		<%@include file="nav-menu.jsp"%>
					
		<div id="container" class="container container-fluid">
			<div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}" class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
			  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			  ${message}
			</div>
			
			<h1 class="h1 display-1 fw-bold ">
				<b> Aluguel de Retroescavadeiras</b>
			</h1>
			
			<div class="container-fluid">
				<img class="main-img img-responsive img-rounded m-auto" src="${pageContext.request.contextPath}/images/escavadeiras.jpg">
			</div>
	 	 	
		</div>
		
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
			    // fecha o alert após 3 segundos
			    setTimeout(function() {
			        $("#alert").slideUp(500);
			    }, 3000);
			    
			    // ao clicar no delete de algum post, pega o nome do usuário, 
			    // o id do usuário e a ação (delete) e envia para o modal 
			    $(".modal-remove").click(function () {
		            var postContent = $(this).attr('post-content');
		            var postId = $(this).attr('post-id');
		            $(".modal-body #hiddenValue").text("o post '"+postContent+"'");
		            $("#id").attr( "value", postId);
		            $("#form").attr( "action","post/delete");
		        })
			});
		</script>
	</body>
</html>
