package controller;

import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "CadastroController", urlPatterns = {"/CadastroController"})
@MultipartConfig
public class CadastroController extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Part fotoPart = request.getPart("foto");
    String nome = request.getParameter("nome");
    String apelido = request.getParameter("apelido");
    String senha = request.getParameter("senha");
    
    // Verificar se o arquivo foi enviado
    if (fotoPart == null || fotoPart.getSize() <= 0) {
      response.getWriter().println("Por favor, envie uma foto.");
      return;
    }
    
    // Converter a foto em um array de bytes
    InputStream fotoInputStream = fotoPart.getInputStream();
    byte[] fotoBytes = fotoInputStream.readAllBytes();
    
    String hashedSenha = BCrypt.hashpw(senha, BCrypt.gensalt());
    
    try {
      // Conectar ao banco de dados
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizzando?useSSL=false&serverTimezone=America/Sao_Paulo", "root", "");
      
      if (userExists(con, apelido)) {
        request.getSession().setAttribute("userAlreadyExists", true);
        response.sendRedirect("/Quiz/signup/");
      } else {
        // Query para inserir os dados
        String query = "INSERT INTO usuarios (nome, apelido, senha, entrou_em, foto) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stt = con.prepareStatement(query)) {
          stt.setString(1, nome);
          stt.setString(2, apelido);
          stt.setString(3, hashedSenha);
          stt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
          stt.setBytes(5, fotoBytes); // Adiciona os bytes da imagem

          int rowsInserted = stt.executeUpdate();

          if (rowsInserted > 0) {
            request.getSession().setAttribute("userCreated", true);
            response.sendRedirect("/Quiz/signup/");
          } else {
            response.getWriter().println("Erro ao cadastrar o usuário.");
          }
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace(response.getWriter());
    } catch (SQLException e) {
      e.printStackTrace(response.getWriter());
      response.getWriter().println("Erro ao conectar ao banco de dados.");
    }
  }
  
  
  
  private boolean userExists(Connection con, String userApelido) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      
      String query = "SELECT apelido FROM usuarios WHERE apelido = ?;";
      
      try (PreparedStatement stt = con.prepareStatement(query)) {
        stt.setString(1, userApelido);
        
        try (ResultSet rs = stt.executeQuery()) {
          return rs.next();
        }
      }
    } catch (ClassNotFoundException | SQLException e) {
      //tratar exception
    }
    
    return false;
  }
}
