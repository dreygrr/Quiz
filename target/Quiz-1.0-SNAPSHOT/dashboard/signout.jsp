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
    <%
      session.removeAttribute("_user");
      session.invalidate();

      response.sendRedirect("../signin/");
    %>
</body>
</html>
