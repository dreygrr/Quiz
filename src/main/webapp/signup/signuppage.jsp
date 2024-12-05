<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.4.2/css/all.css">

  <link rel="stylesheet" href="signup.css">

  <title>Quizzando &bull; Cadastro</title>
</head>
<body>
  <form action="${pageContext.request.contextPath}/CadastroController" method="post" enctype="multipart/form-data">
    <h1>Cadastro</h1>

    <c:if test="${not empty sessionScope.userAlreadyExists}">
      <div class="msg msg-err"><i class="fa-solid fa-triangle-exclamation"></i> <p>Esse apelido já pertence a outro usuário.</p></div>

      <c:remove var="userAlreadyExists" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.userCreated}">
      <div class="msg msg-ok"><i class="fa-regular fa-check"></i> <p>Conta criada! <a href="../signin/">Entre agora</a></p></div>

      <c:remove var="userCreated" scope="session" />
    </c:if>

    <div class="field">
      <label for="fotoId">foto de perfil </label>
      <input type="file" name="foto" id="fotoId" accept="image/*">
    </div>
      
    <div class="field">
      <label for="nomeId">nome </label>
      <input type="text" name="nome" id="nomeId" required>
    </div>

    <div class="field">
      <label for="apelidoId">apelido </label>
      <input type="text" name="apelido" id="apelidoId" required>
    </div>

    <div class="field">
      <label for="senhaId">senha </label>
      <input type="password" name="senha" id="senhaId" required>
    </div>

    <input class="btn full btn-fill" type="submit" value="sign up">

    <div class="links">
      <ul>
        <li><a href="../home/"><i class="fa-regular fa-arrow-left"></i> Página inicial</a></li>
        <li>Já tem uma conta? <a href="../signin/">Entre aqui</a>!</li>
      </ul>
    </div>
  </form>
</body>
</html>
