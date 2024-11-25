<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.User"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.4.2/css/all.css">

    <link rel="stylesheet" href="dashboard.css">
    
    <title>Quizzando &bull; Painel</title>
</head>
<body>
  <c:choose>
    <c:when test="${not empty sessionScope._user}">
      <c:redirect url="./dashboard.jsp" />
    </c:when>
    
    <c:otherwise>
      <c:set var="noAuth" value="true" scope="session" />
      <c:redirect url="../signin/" />
    </c:otherwise>
  </c:choose>
</body>
</html>