<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.4.2/css/all.css">

  <link rel="stylesheet" href="signin.css">

  <title>Quizzando &bull; Entrar</title>
</head>
<body>
  <form action="${pageContext.request.contextPath}/LoginController" method="post">
    <h1>Entrar</h1>

    <c:if test="${not empty sessionScope.wrongCredentials}">
      <div class="msg msg-err"><i class="fa-solid fa-triangle-exclamation"></i> <p>Apelido/senha incorretos.</p></div>

      <c:remove var="wrongCredentials" scope="session" />
    </c:if>
      
    <c:if test="${not empty sessionScope.noUserFound}">
      <div class="msg msg-err"><i class="fa-solid fa-triangle-exclamation"></i> <p>O usuário informado não existe.</p></div>

      <c:remove var="noUserFound" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.noAuth}">
      <div class="msg"><i class="fa-solid fa-circle-info"></i> <p>Entre primeiro.</p></div>

      <c:remove var="noAuth" scope="session" />
    </c:if>

    <div class="field">
      <label for="apelidoId">apelido </label>
      <input type="text" name="apelido" id="apelidoId" required>
    </div>

    <div class="field">
      <label for="senhaId">senha </label>
      <input type="password" name="senha" id="senhaId" required>
    </div>

    <input class="btn btn-fill" type="submit" value="Entrar">

    <div class="links">
      <ul>
        <li><a href="../home/"><i class="fa-regular fa-arrow-left"></i> Página inicial</a></li>
        <li>Novo por aqui? <a href="../signup/">Crie sua conta</a>!</li>
      </ul>
    </div>
  </form>
</body>
</html>
