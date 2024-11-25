package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProfilePictureServlet", urlPatterns = {"/ProfilePicture"})
public class ProfilePictureServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String apelido = request.getParameter("apelido");
    
    if (apelido == null || apelido.isEmpty()) {
      response.getWriter().println("apelido não fornecido.");
      return;
    }
    
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizzando?useSSL=false&serverTimezone=America/Sao_Paulo", "root", "");
      
      // Query para buscar a foto pelo apelido
      String query = "SELECT foto FROM usuarios WHERE apelido = ?;";
      
      try (PreparedStatement stt = con.prepareStatement(query)) {
        stt.setString(1, apelido);
        
        try (ResultSet rs = stt.executeQuery()) {
          if (rs.next()) {
            byte[] foto = rs.getBytes("foto");

            if (foto != null && foto.length > 0) {
              // Configurar o tipo de resposta como imagem
              response.setContentType("image/jpeg");
              response.setContentLength(foto.length);

              // Escrever os bytes da foto no corpo da resposta
              try (OutputStream out = response.getOutputStream()) { out.write(foto); }
            } else {
              //foto nula
            }
          } else {
            response.getWriter().println("Usuário não encontrado.");
          }
        }
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace(response.getWriter());
      response.getWriter().println("Erro ao acessar o banco de dados.");
    }
  }
}
