package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
  private Connection getConnection() throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    
    return DriverManager.getConnection("jdbc:mysql://localhost:3306/quizzando?useSSL=false&serverTimezone=America/Sao_Paulo", "root", "");
  }

  public boolean userExists(String apelido) {
    String query = "SELECT apelido FROM usuarios WHERE apelido = ?;";
    
    try (Connection con = getConnection(); PreparedStatement stt = con.prepareStatement(query)) {
      stt.setString(1, apelido);
      try (ResultSet rs = stt.executeQuery()) {
        return rs.next();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  public int getUserId(String apelido) {
    String query = "SELECT id FROM usuarios WHERE apelido = ?;";
    
    try (Connection con = getConnection(); PreparedStatement stt = con.prepareStatement(query)) {
      stt.setString(1, apelido);
      
      try (ResultSet rs = stt.executeQuery()) {
        if (rs.next())
          return rs.getInt("id");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return 0;
  }

  public boolean createUser(String nome, String apelido, String senha, byte[] foto) {
    String query = "INSERT INTO usuarios (nome, apelido, senha, entrou_em, foto) VALUES (?, ?, ?, ?, ?);";
    
    try (Connection con = getConnection(); PreparedStatement stt = con.prepareStatement(query)) {
      stt.setString(1, nome);
      stt.setString(2, apelido);
      stt.setString(3, senha);
      stt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
      stt.setBytes(5, foto);
      return stt.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return false;
  }
  
  public boolean validateLogin(String apelido, String senha) {
    String query = "SELECT senha FROM usuarios WHERE apelido = ?;";
    
    try (Connection con = getConnection(); PreparedStatement stt = con.prepareStatement(query)) {
      stt.setString(1, apelido);
      
      try (ResultSet rs = stt.executeQuery()) {
        if (rs.next())
          return BCrypt.checkpw(senha, rs.getString("senha"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return false;
  }

  public User getUser(String apelido) {
    String query = "SELECT nome, apelido, entrou_em FROM usuarios WHERE apelido = ?;";

    try (Connection con = getConnection(); PreparedStatement stt = con.prepareStatement(query)) {
      stt.setString(1, apelido);
      
      try (ResultSet rs = stt.executeQuery()) {
        if (rs.next())
          return new User(rs.getString("nome"), rs.getString("apelido"), rs.getString("entrou_em"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  public byte[] getProfilePicture(String apelido) {
    String query = "SELECT foto FROM usuarios WHERE apelido = ?;";
    
    try (Connection con = getConnection(); PreparedStatement stt = con.prepareStatement(query)) {
      stt.setString(1, apelido);
      
      try (ResultSet rs = stt.executeQuery()) {
        if (rs.next())
          return rs.getBytes("foto");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  public int countCorrectAnswers(String apelido) {
    String query = """
      SELECT COUNT(*) AS correct_count
      FROM respostas r
      INNER JOIN questoes q ON r.id_questao = q.id
      INNER JOIN usuarios u ON r.id_usuario = u.id
      WHERE u.apelido = ?;
    """;
    
    try (Connection con = getConnection();
      PreparedStatement stmt = con.prepareStatement(query)) {
      
      stmt.setString(1, apelido);
      ResultSet rs = stmt.executeQuery();
      
      if (rs.next()) return rs.getInt("correct_count");
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return 0;
  }
}
