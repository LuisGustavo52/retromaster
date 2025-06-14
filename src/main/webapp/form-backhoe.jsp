<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <%@include file="base-head.jsp" %>
  </head>
  <body>
    <%@include file="nav-menu.jsp" %>

    <div id="container" class="container-fluid">
      <h3 class="page-header">
        ${not empty retroescavadeira ? "Editar Retroescavadeira" : "Cadastrar Retroescavadeira"}
      </h3>

      <form action="${pageContext.request.contextPath}/retro/${action}" method="POST">
        <input type="hidden" value="${retroescavadeira.id}" name="id" />

        <div class="row">
          <div class="form-group col-md-6">
            <label for="model">Modelo</label>
            <input type="text" class="form-control" id="model" name="model"
                   placeholder="Modelo da retroescavadeira"
                   required
                   oninvalid="this.setCustomValidity('Por favor, informe o modelo.')"
                   oninput="setCustomValidity('')"
                   value="${retroescavadeira.model}" />
          </div>

          <div class="form-group col-md-3">
            <label for="year">Ano</label>
            <input type="number" class="form-control" id="year" name="year"
                   placeholder="Ano de fabricação"
                   required
                   min="1950"
                   max="2100"
                   oninvalid="this.setCustomValidity('Por favor, informe o ano.')"
                   oninput="setCustomValidity('')"
                   value="${retroescavadeira.year}" />
          </div>

          <div class="form-group col-md-3">
  <label for="status">Ativa</label><br>
  <input type="checkbox" id="status" name="status" 
         ${retroescavadeira.status ? "checked" : ""}>
</div>


        <div class="row">
          <div class="form-group col-md-6">
            <label for="company">Empresa</label>
            <select id="company" class="form-control" name="company"
                    required
                    oninvalid="this.setCustomValidity('Por favor, selecione uma empresa.')"
                    oninput="setCustomValidity('')">
              <option value="" disabled ${empty retroescavadeira ? "selected" : ""}>Selecione uma empresa</option>
              <c:forEach var="company" items="${companies}">
                <option value="${company.id}"
                        ${retroescavadeira.company.id == company.id ? "selected" : ""}>
                  ${company.name}
                </option>
              </c:forEach>
            </select>
          </div>
        </div>

        <hr />
        <div id="actions" class="row pull-right">
          <div class="col-md-12">
            <a href="${pageContext.request.contextPath}/retros" class="btn btn-default">Cancelar</a>
            <button type="submit" class="btn btn-primary">
              ${not empty retroescavadeira ? "Atualizar" : "Cadastrar"}
            </button>
          </div>
        </div>
      </form>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>
