package model;

import java.sql.*;

public class QuestionDAO {

  private Connection getConnection() throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");

    return DriverManager.getConnection("jdbc:mysql://localhost:3306/quizzando?useSSL=false&serverTimezone=America/Sao_Paulo", "root", "");
  }

  // Verifica se uma questão já existe no banco pelo hash
  public boolean questionExists(String questionId) {
    String query = "SELECT 1 FROM questoes WHERE id = ?";
    
    try (Connection con = getConnection();
      PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, questionId);
      ResultSet rs = stmt.executeQuery();
      
      return rs.next();

    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public boolean saveQuestion(Question question) {
    String query = """
            INSERT INTO questoes (id, questao, categoria, dificuldade, resposta_correta, respostas_incorretas)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    try (Connection con = getConnection();
      PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, question.getId());
      stmt.setString(2, question.getQuestion());
      stmt.setString(3, question.getCategory());
      stmt.setString(4, question.getDifficulty());
      stmt.setString(5, question.getCorrectAnswer());
      stmt.setString(6, String.join(";", question.getIncorrectAnswers())); // Junta respostas incorretas

      return stmt.executeUpdate() > 0;

    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return false;
  }

  // Salva uma resposta do usuário na tabela 'respostas'
  public boolean saveUserResponse(int userId, String questionId) {
    String query = """
            INSERT INTO respostas (id_usuario, id_questao)
            VALUES (?, ?)
            """;

    try (Connection con = getConnection();
      PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, userId);
      stmt.setString(2, questionId);

      return stmt.executeUpdate() > 0;

    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return false;
  }

  // Calcula a pontuação total de um usuário
  public int calculateUserScore(String apelido) {
    String query = """
      SELECT SUM(
        CASE dificuldade
          WHEN 'easy' THEN 3
          WHEN 'medium' THEN 6
          WHEN 'hard' THEN 10
          ELSE 0
          END
        ) AS total_score
        FROM respostas r
        INNER JOIN questoes q ON r.id_questao = q.id
        INNER JOIN usuarios u ON r.id_usuario = u.id
        WHERE u.apelido = ?
      """;

    try (Connection con = getConnection();
      PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, apelido);
      ResultSet rs = stmt.executeQuery();

      if (rs.next())
        return rs.getInt("total_score");

    } catch (Exception e) {
      e.printStackTrace();
    }

    return 0;
  }
}
