<%@page import="model.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.4.2/css/all.css">

  <script src="${pageContext.request.contextPath}/home/buttonDelay.js" defer></script>
  <link rel="stylesheet" href="home.css">

  <title>Quizzando &bull; Página inicial</title>
</head>
<body>  
  <div class="home-landing">
    <h1>Quizzando</h1>
    
    <form method="GET" action="${pageContext.request.contextPath}/QuizController">
      <input type="hidden" name="action" value="next">
      <button id="initQuiz" class="btn" type="submit">
        <span class="btn-timer-title">Iniciar Quiz</span>
        <i class="fa-regular fa-brain"></i>
      </button>
    </form>
  </div>

  <div class="wrapper home-container">
    <div class="block">
      <h2>Apresentando seu quiz favorito!</h2>

      <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Commodi dolores amet totam rem sunt nesciunt, error at rerum cumque fugiat. In, atque. Tempora, maxime sint debitis veniam modi temporibus omnis!</p>
    </div>
  </div>
      
  <custom:footer />
  <custom:navbar />
</body>
</html>
