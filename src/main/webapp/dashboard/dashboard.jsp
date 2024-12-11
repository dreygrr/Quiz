<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="model.User"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
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
      <c:set var="_user" value="${sessionScope._user}" />
    </c:when>

    <c:otherwise>
      <c:set var="noAuth" value="true" scope="session" />
      <c:redirect url="../signin/"/>
    </c:otherwise>
  </c:choose>

  <div class="wrapper dashboard-container">
    <div class="block profile-about">
      <div class="user-ppic" style="--profile-url: url('/Quiz/ProfilePicture?apelido=${_user.getApelido()}');"></div>

      <h1><c:out value="${_user.getNome()}" /></h1>
      <h2>#<c:out value="${sessionScope._user.getApelido()}" /></h2>

      <div class="profile-sections">
        <div class="section about">
          <h4>Sobre mim</h4>
          <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptate sit dolorum explicabo quasi tempora commodi accusantium aspernatur voluptatibus quo quam officia qui nesciunt hic, perferendis enim excepturi recusandae praesentium architecto.</p>
        </div>

        <div class="section other-options">
          <h4>Outras opções</h4>
          
          <a class="btn full lightred" href="signoutpage.jsp">Sair <i class="fa-regular fa-sign-out"></i></a>
        </div>
      </div>
    </div>
            
    <div class="block profile-stats">
      <div class="section stats">
        <div class="stat ranking">
          <i class="fa-solid fa-ranking-star icon"></i>

          <div class="content">
            <h4>Ranking</h4>
            <h3>#2</h3>
          </div>
        </div>
          
        <div class="stat score">
          <i class="fa-solid fa-star icon"></i>

          <div class="content">
            <h4>Score</h4>
            <h3>+${sessionScope.userTotalScore}</h3>
          </div>
        </div>

        <div class="stat answers">
          <i class="fa-solid fa-pencil icon"></i>

          <div class="content">
            <h4>Right answers</h4>
            <h3>${sessionScope.userAnswersCount}</h3>
          </div>
        </div>

        <div class="stat account-age">
          <i class="fa-solid fa-calendar icon"></i>

          <div class="content">
            <h4>Member since</h4>
            <h3><c:out value="${_user.getFormattedEntrouEm()}" /></h3>
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <custom:footer />
  <custom:navbar />
</body>
</html>
