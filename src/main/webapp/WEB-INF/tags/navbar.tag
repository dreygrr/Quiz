<%@tag language="java" body-content="empty"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="nav-hitbox" style="--nav-current-hitbox: 66px;"></div>

<nav>
  <ul>
    <li><a href="${pageContext.request.contextPath}/home/"><i class="fa-solid fa-house"></i></a></li>
    <li><a href="${pageContext.request.contextPath}/home/homepage.jsp#initQuiz"><i class="fa-solid fa-brain"></i></a></li>
    <li><a href="#"><i class="fa-solid fa-ranking-star"></i></a></li>
    
    <c:choose>
      <c:when test="${not empty sessionScope._user}">
        <li>
          <a href="${pageContext.request.contextPath}/dashboard/">
            <div 
              class="user-ppic"
              style="--profile-url: url('/Quiz/ProfilePicture?apelido=${_user.getApelido()}');">
            </div>
          </a>
        </li>
      </c:when>
      <c:otherwise>
        <li><a href="${pageContext.request.contextPath}/signin/"><i class="fa-solid fa-sign-in"></i></a></li>
        <li><a href="${pageContext.request.contextPath}/signup/"><i class="fa-solid fa-user-plus"></i></a></li>
    </c:otherwise>
    </c:choose>
  </ul>
</nav>