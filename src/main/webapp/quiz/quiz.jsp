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
      <c:if test="${not empty sessionScope._user}">
        <div class="question-headers">
          <h5 class="category-header"><b><i class="fa-solid fa-book"></i></b> ${currentQuestion.category}</h5>
          <h5 class="score-header"><b><i class="fa-solid fa-star"></i> ${currentQuestion.points}</b> &mdash; ${currentQuestion.difficulty}</h5>
        </div>
      </c:if>
      
      <h4>Pergunta:</h4>
      
      <h3>${currentQuestion.question}</h3>
            
      <form action="${pageContext.request.contextPath}/QuizController" method="post">
        <input type="hidden" name="action" value="answer">
          
        <div class="fields">
          <c:forEach var="answer" items="${currentQuestion.allAnswersShuffled}">
            <div class="field ${userAnswer == answer ? "userAnswer" : ""}">
              <input 
                id="${answer}Id" 
                type="radio" name="answer" 
                value="${answer}"
                <c:if test="${not empty userAnswer}">
                  disabled
                </c:if>
                <c:if test="${userAnswer == answer}">
                  checked
                </c:if>
                required
                />
              <label for="${answer}Id">${answer}</label>
            </div>
          </c:forEach>
        </div>
        
        <c:if test="${not empty result}">
          <p class="quiz-result ${currentQuestion.correctAnswer == userAnswer ? "hit" : "miss"}">
            <c:choose>
              <c:when test="${currentQuestion.correctAnswer == userAnswer}">
                <i class="fa-regular fa-check result-icon"></i>
              </c:when>
              <c:otherwise>
                <i class="fa-regular fa-times result-icon"></i>
              </c:otherwise>
            </c:choose>
                
            <b>${result}</b>
          </p>
        </c:if>

        <button class="btn full fill responder ${not empty result ? "disabled" : ""}" ${not empty result ? "disabled" : ""} type="submit">
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
