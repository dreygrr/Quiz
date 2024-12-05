<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.4.2/css/all.css">
  <link rel="stylesheet" href="/Quiz/quiz/quiz.css">

  <title>Quizzando &bull; Quiz</title>
  
  <script>
    document.addEventListener("DOMContentLoaded", () => {
      const btnNovaPergunta = document.querySelector("#newQuestion");
      const btnTitle = btnNovaPergunta.querySelector(".btn-timer-title");

      if (btnNovaPergunta && btnTitle) {
        btnNovaPergunta.disabled = true;
        btnNovaPergunta.classList.add('disabled');
        
        btnTitle.innerHTML = "4";

        setButtonTimer(btnTitle);

        setTimeout(() => {
          btnNovaPergunta.disabled = false;
          btnNovaPergunta.classList.remove('disabled');
        }, 4000);
      }
    });



    const setButtonTimer = (btnTitleTag) => {
      let i = 3;

      const btnTimer = setInterval(() => {
        btnTitleTag.innerHTML = i; 
        i--; 
      }, 1000); 

      setTimeout(() => {
        clearInterval(btnTimer); 
        btnTitleTag.innerHTML = "Nova pergunta"; 
      }, 4000);
    };
  </script>
</head>
<body>
  <div class="question-container block">
    <div class="form-container">
      <form action="${pageContext.request.contextPath}/QuizController" method="post">
        <h4>Pergunta:</h4>
        <h3>${question}</h3>

        <input type="hidden" name="action" value="answer">
        
        <c:if test="${not empty result}">
          <p class="quiz-result"><b>${result}</b></p>
        </c:if>
          
        <div class="fields">
          <c:forEach var="answer" items="${answers}">
            <div class="field">
              <input id="${answer}Id" type="radio" name="answer" value="${answer}" required>
              <label for="${answer}Id">${answer}</label>
            </div>
          </c:forEach>
        </div>

        <button class="btn full fill responder" type="submit">
          <span>Responder</span>
          <i class="fa-regular fa-arrow-right"></i>
        </button>
      </form>

      <form action="${pageContext.request.contextPath}/QuizController" method="get">
        <input type="hidden" name="action" value="next">
        <button id="newQuestion" class="btn full" type="submit" disabled>
          <span class="btn-timer-title"></span>
          <i class="fa-regular fa-arrow-rotate-right"></i>
        </button>
      </form>
    </div>
  </div>
      
  <custom:footer />
  <custom:navbar />
</body>
</html>
