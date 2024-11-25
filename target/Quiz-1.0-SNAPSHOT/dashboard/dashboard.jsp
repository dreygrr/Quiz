<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    
    <custom:navbar />

    <div class="wrapper dashboard-container">
        <div class="block profile-about">
            <div class="user-ppic" style="--profile-url: url('/Quiz/ProfilePicture?apelido=${_user.getApelido()}');"></div>

            <h1><c:out value="${_user.getNome()}" /></h1>
            <h2><c:out value="${sessionScope._user.getApelido()}"/></h2>

            <div class="profile-sections">
                <div class="stats">
                    <div class="stat account-age">
                        <i class="fa-solid fa-calendar icon"></i>
    
                        <div class="content">
                            <h4>Member since</h4>
                            <h3><c:out value="${_user.getEntrouEm()}" /></h3>
                        </div>
                    </div>
                </div>

                <div class="about">
                    <h4>About me</h4>
                    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptate sit dolorum explicabo quasi tempora commodi accusantium aspernatur voluptatibus quo quam officia qui nesciunt hic, perferendis enim excepturi recusandae praesentium architecto.</p>
                </div>

                <div class="info">
                  <ul>
                    <li><b>Fullname: </b>( - )</li>
                    <li><b>Phone: </b>( - )</li>
                    <li><b>E-mail: </b>( - )</li>
                  </ul>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
