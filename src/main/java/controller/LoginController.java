package controller;

import java.io.IOException;

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
import javax.servlet.http.HttpSession;

import model.User;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.getWriter().println("servlet ta funfano");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String apelido = request.getParameter("apelido");
    String senha = request.getParameter("senha");

    // Log para verificar os valores recebidos
    System.out.println("Apelido: " + apelido);
    System.out.println("Senha: " + senha);
    
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizzando?useSSL=false&serverTimezone=America/Sao_Paulo", "root", "");
      
      String query = "SELECT nome, apelido, entrou_em FROM usuarios WHERE apelido = ? AND senha = ?;";

      try (PreparedStatement stt = con.prepareStatement(query)) {
        stt.setString(1, apelido);
        stt.setString(2, senha);

        try (ResultSet rs = stt.executeQuery()) {
          
          if (rs.next()) {
            User user = new User(rs.getString("nome"), rs.getString("apelido"), rs.getString("entrou_em"));
            
            HttpSession session = request.getSession();
            
            session.setAttribute("_user", user);

            response.sendRedirect("/Quiz/dashboard/");
          } else {
            request.getSession().setAttribute("wrongCredentials", true);
            response.sendRedirect("/Quiz/signin/");
          }
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
      response.getWriter().println("erro ao conectar ao banco" + e.getMessage());
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    

    // try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/quizzando", "root", "")) {
    //   System.out.println("Conexão com o banco de dados estabelecida!");

    //   String query = "SELECT nome, apelido FROM usuarios WHERE apelido = ? AND senha = ?";

    //   try (PreparedStatement stt = con.prepareStatement(query)) {
    //     stt.setString(1, apelido);
    //     stt.setString(2, senha);

    //     try (ResultSet rs = stt.executeQuery()) {

    //       if (rs.next()) {
    //         System.out.println("Usuário encontrado!");

    //         User user = new User(rs.getString("nome"), rs.getString("apelido"));
    //         HttpSession session = request.getSession();
    //         session.setAttribute("_user", user);

    //         response.sendRedirect("./../dashboard/");
    //       } else {
    //         System.out.println("Credenciais inválidas.");

    //         request.getSession().setAttribute("wrongCredentials", true);
    //         response.sendRedirect("./signinpage.jsp");
    //       }
    //     }
    //   }
    // } catch (SQLException e) {
    //   e.printStackTrace();
    //   response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao conectar ao banco de dados." + e.getMessage());
    // }
  }
}
