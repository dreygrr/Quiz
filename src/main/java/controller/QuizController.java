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

import model.Question;
import model.QuestionDAO;
import model.User;
import model.UserDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;

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
      Question currentQuestion = (Question) request.getSession().getAttribute("currentQuestion");
      String userAnswer = request.getParameter("answer");
      User user = (User) request.getSession().getAttribute("_user");
      String pontuacaoTxt = "";
      
      if (currentQuestion != null) {        
        if (currentQuestion.isCorrect(userAnswer)) {
          if (user != null) {
            QuestionDAO questionDAO = new QuestionDAO();
            UserDAO userDAO = new UserDAO();
            
            pontuacaoTxt = "Você ganhou +" + currentQuestion.getPoints() + " pontos!";

            if (!questionDAO.questionExists(currentQuestion.getId())) questionDAO.saveQuestion(currentQuestion);
            
            questionDAO.saveUserResponse(userDAO.getUserId(user.getApelido()), currentQuestion.getId());
            
            // Calcular pontuação total
            int userTotalScore = questionDAO.calculateUserScore(user.getApelido());
            int userAnswersCount = userDAO.countCorrectAnswers(user.getApelido());
            
            request.getSession().setAttribute("userTotalScore", userTotalScore);
            request.getSession().setAttribute("userAnswersCount", userAnswersCount);
          }

          // Feedback
          request.setAttribute("result", "Correto! " + pontuacaoTxt);
        } else {
          request.setAttribute("result", "Incorreto. A resposta correta era: " + currentQuestion.getCorrectAnswer());
        }
        
        // Repassar dados para o JSP
        request.setAttribute("question", currentQuestion.getQuestion());
        request.setAttribute("answers", currentQuestion.getAllAnswersShuffled());
        request.setAttribute("userAnswer", userAnswer);
      }
      
    request.getRequestDispatcher("./quiz/quiz.jsp").forward(request, response);
    }
  }
  
  
  
  private void fetchNewQuestion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

      // Criar a pergunta
      String question = StringEscapeUtils.unescapeHtml(questionData.getString("question"));
      String category = questionData.getString("category");
      String difficulty = questionData.getString("difficulty");
      String correctAnswer = StringEscapeUtils.unescapeHtml(questionData.getString("correct_answer"));
      JSONArray incorrectAnswersJson = questionData.getJSONArray("incorrect_answers");
      
      List<String> incorrectAnswers = new ArrayList<>();
      for (int i = 0; i < incorrectAnswersJson.length(); i++) {
        incorrectAnswers.add(StringEscapeUtils.unescapeHtml(incorrectAnswersJson.getString(i)));
      }
      
      Question questionObj = new Question(question, category, difficulty, correctAnswer, incorrectAnswers);
      
      request.getSession().setAttribute("currentQuestion", questionObj);
      
      request.getRequestDispatcher("./quiz/quiz.jsp").forward(request, response);
     
    } catch (Exception e) {
      e.printStackTrace();
      response.getWriter().println("Erro ao carregar a pergunta: " + e.getMessage());
    }
  }
}
