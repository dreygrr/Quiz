<%@page import="model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.4.2/css/all.css">

  <link rel="stylesheet" href="signout.css">

  <title>Quizzando &bull; Painel</title>
</head>
<body>
  <c:choose>
    <c:when test="${not empty sessionScope._user}">
      <c:set var="_user" value="${sessionScope._user}" />
    </c:when>

    <c:otherwise>
      <c:set var="noAuth" value="true" scope="session" />
      <c:redirect url="../signin/"/>
    </c:otherwise>
  </c:choose>

  <div class="wrapper">
    <div class="block">
      <h1>Você realmente deseja sair?</h1>

      <a class="btn btn-fill" href="signout.jsp">Sim</a>
      <a class="btn btn-fill" href="../dashboard/">Não</a>
    </div>
  </div>
  
  <custom:footer />
  <custom:navbar />
</body>
</html>
