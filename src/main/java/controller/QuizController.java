package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "QuizController", urlPatterns = {"/QuizController"})
public class QuizController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    
    if ("next".equals(action)) {
      // Lógica para buscar uma nova pergunta da API
      fetchNewQuestion(request, response);
    } else {
      // Redirecionar para a página inicial se nenhuma ação for identificada
      response.sendRedirect("./home/index.jsp");
    }
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    String action = request.getParameter("action");
    
    if ("answer".equals(action)) {
      String userAnswer = request.getParameter("answer");
      String correctAnswer = (String) request.getSession().getAttribute("correctAnswer");

      // Verificar a resposta
      if (userAnswer != null && userAnswer.equals(correctAnswer)) {
        request.setAttribute("result", "Correto!");
      } else {
        request.setAttribute("result", "Incorreto. A resposta correta era: " + correctAnswer);
      }

      // Exibir o resultado na página do quiz
      request.getRequestDispatcher("./quiz/quiz.jsp").forward(request, response);
    }
  }
  
  
  
  private void fetchNewQuestion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // URL da API
    String apiUrl = "https://opentdb.com/api.php?amount=1";

    try {
      // Fazer a requisição
      URL url = new URL(apiUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      // Ler a resposta
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuilder responseBuilder = new StringBuilder();
      String line;
      
      while ((line = reader.readLine()) != null) {
        responseBuilder.append(line);
      }
      reader.close();

      // Processar o JSON retornado
      JSONObject jsonResponse = new JSONObject(responseBuilder.toString());
      JSONArray results = jsonResponse.getJSONArray("results");
      JSONObject questionData = results.getJSONObject(0);

      // Dados da pergunta
      String question = questionData.getString("question");
      String correctAnswer = questionData.getString("correct_answer");
      JSONArray incorrectAnswers = questionData.getJSONArray("incorrect_answers");
      
      // Combinar as respostas (certa e erradas) em um List
      List<String> allAnswers = new ArrayList<>();
      for (int i = 0; i < incorrectAnswers.length(); i++) {
        allAnswers.add(incorrectAnswers.getString(i));
      }
      allAnswers.add(correctAnswer);

      
      // Combinar as respostas (certa e erradas)
      /*
      JSONArray allAnswers = new JSONArray(incorrectAnswers.toList());
      allAnswers.put(correctAnswer);
      */

      // Embaralhar as respostas
      java.util.Collections.shuffle(allAnswers);

      // Passar os dados para o JSP
      request.setAttribute("question", question);
      request.setAttribute("answers", allAnswers);
      request.setAttribute("correctAnswer", correctAnswer);
      request.getSession().setAttribute("correctAnswer", correctAnswer);

      // Redirecionar para o JSP
      request.getRequestDispatcher("./quiz/quiz.jsp").forward(request, response);

    } catch (Exception e) {
      e.printStackTrace();
      response.getWriter().println("Erro ao carregar a pergunta: " + e.getMessage());
    }
  }
}
