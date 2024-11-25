<%@tag language="java" body-content="empty"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav>
  <a href="../"><h1 class="props-logo">Quizzando</h1></a>

  <ul>
    <li><a href="../home/"><i class="fa-solid fa-house"></i></a></li>
    <li><a href="#"><i class="fa-solid fa-ranking-star"></i></a></li>
    
    <c:choose>
      <c:when test="${not empty sessionScope._user}">
        <li>
          <a href="../dashboard/">
            <div 
              class="user-ppic"
              style="--profile-url: url('/Quiz/ProfilePicture?apelido=${_user.getApelido()}');">
            </div>
          </a>
        </li>
        
        <li><a href="../dashboard/signoutpage.jsp"><i class="fa-solid fa-sign-out"></i></a></li>
      </c:when>
      <c:otherwise>
        <li><a href="../signin/"><i class="fa-solid fa-sign-in"></i></a></li>
        <li><a href="../signup/"><i class="fa-solid fa-user-plus"></i></a></li>
    </c:otherwise>
    </c:choose>
  </ul>
</nav>