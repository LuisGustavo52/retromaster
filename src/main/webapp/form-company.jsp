<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@include file="base-head.jsp"%>
</head>
<body>
	<%@include file="nav-menu.jsp"%>
	
	<div id="container" class="container-fluid">
		<h3 class="page-header">${not empty company ? 'Atualizar' : 'Adicionar'} Empresa</h3>
		
		<form action="${pageContext.request.contextPath}/company/${action}" 
			method="POST">
			
			<input type="hidden" value="${company.getId()}" name="companyId">
			
			<div class="row">
				<div class="form-group col-md-6">
					<label for="name">Nome Fantasia</label>
					<input type="text" class="form-control" id="name" name="name" 
						   autofocus="autofocus" placeholder="Nome Empresa" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o nome da empresa.')"
						   oninput="setCustomValidity('')"
						   value="${company.getName()}" />
				</div>
				
				<div class="form-group col-md-6">
					<label for="role">Razão Social</label>
					<input type="text" class="form-control" id="razao_social" name="razao_social" 
						   autofocus="autofocus" placeholder="Razão social" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe a razão social.')"
						   oninput="setCustomValidity('')" 
						   value="${company.getRazao_social()}"/>
				</div>
								
				<div class="form-group col-md-6">
					<label for="role">CNPJ</label>
					<input type="text" class="form-control" maxlength="18" id="cnpj" name="cnpj" 
						   autofocus="autofocus" placeholder="00.000.000/0000-00" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o CNPJ.')"
						   oninput="setCustomValidity('')" 
						   value="${company.getCnpj()}"/>
				</div>				
			</div>
			
			</div>
			
			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					
					<a href="${pageContext.request.contextPath}/companies" 
					   class="btn btn-default">Cancelar</a>
					
					<button type="submit" 
						    class="btn btn-primary">${not empty company ? 'Atulizar' : 'Cadastrar' } Empresa</button>
				</div>
			</div>
		</form>
		
	</div>

</body>

<script>
  const cnpjInput = document.getElementById('cnpj');

  // Máscara de visualização
  cnpjInput.addEventListener('input', function (e) {
    let value = e.target.value.replace(/\D/g, ''); // Remove tudo que não é dígito

    // Aplica a máscara
    if (value.length > 14) value = value.slice(0, 14);
    value = value.replace(/^(\d{2})(\d)/, '$1.$2');
    value = value.replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3');
    value = value.replace(/\.(\d{3})(\d)/, '.$1/$2');
    value = value.replace(/(\d{4})(\d)/, '$1-$2');

    e.target.value = value;
  });

  // Remove a máscara antes de enviar o formulário
  document.querySelector('form').addEventListener('submit', function () {
    cnpjInput.value = cnpjInput.value.replace(/\D/g, '');
  });
</script>


</html>