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
      Question currentQuestion = (Question) request.getSession().getAttribute("currentQuestion");
      String userAnswer = request.getParameter("answer");

      if (currentQuestion != null) {
        if (currentQuestion.isCorrect(userAnswer)) {
          String pontuacaoTxt = "";
          
          if (request.getSession().getAttribute("_user") != null)
            pontuacaoTxt = "Você ganhou +" + currentQuestion.getPoints() + " pontos!";
          
          request.setAttribute("result", "Correto! " + pontuacaoTxt);
        } else {
          request.setAttribute("result", "Incorreto. A resposta correta era: " + currentQuestion.getCorrectAnswer());
        }
        
        request.setAttribute("question", currentQuestion.getQuestion());
        request.setAttribute("answers", currentQuestion.getAllAnswersShuffled());
        request.setAttribute("userAnswer", userAnswer);

        request.getRequestDispatcher("./quiz/quiz.jsp").forward(request, response);
      } else {
        response.sendRedirect("./home/index.jsp");
      }
      
      /*
      // Recuperar os dados da pergunta da sessão
      String question = (String) request.getSession().getAttribute("currentQuestion");
      List<String> allAnswers = (List<String>) request.getSession().getAttribute("currentAnswers");

      // Passar os dados para o JSP
      request.setAttribute("question", question);
      request.setAttribute("answers", allAnswers);
      request.setAttribute("userAnswer", userAnswer);
      request.setAttribute("correctAnswer", correctAnswer);
      request.setAttribute("score", score);
      request.setAttribute("difficulty", difficulty);
      */
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
      String question = questionData.getString("question");
      String category = questionData.getString("category");
      String difficulty = questionData.getString("difficulty");
      String correctAnswer = questionData.getString("correct_answer");
      JSONArray incorrectAnswersJson = questionData.getJSONArray("incorrect_answers");
      
      /*List<String> allAnswers = new ArrayList<>();
      for (int i = 0; i < incorrectAnswersJson.length(); i++) {
        allAnswers.add(incorrectAnswersJson.getString(i));
      }*/
      
      List<String> incorrectAnswers = new ArrayList<>();
      for (int i = 0; i < incorrectAnswersJson.length(); i++) {
        incorrectAnswers.add(incorrectAnswersJson.getString(i));
      }
      
      Question questionObj = new Question(question, category, difficulty, correctAnswer, incorrectAnswers);
      
      request.getSession().setAttribute("currentQuestion", questionObj);
      
      request.getRequestDispatcher("./quiz/quiz.jsp").forward(request, response);
      
      /*
      incorrectAnswers.add(correctAnswer);
      String score = getQuestionPoints(difficulty);
      
      // Embaralhar as respostas
      java.util.Collections.shuffle(incorrectAnswers);

      // Passar os dados para o JSP
      request.setAttribute("question", question);
      request.setAttribute("answers", incorrectAnswers);
      request.setAttribute("correctAnswer", correctAnswer);
      request.setAttribute("score", score);
      request.setAttribute("difficulty", difficulty);
      
      request.getSession().setAttribute("currentQuestion", question);
      request.getSession().setAttribute("currentAnswers", incorrectAnswers);
      request.getSession().setAttribute("correctAnswer", correctAnswer);
      request.getSession().setAttribute("score", score);
      request.getSession().setAttribute("difficulty", difficulty);

      // Redirecionar para o JSP
      request.getRequestDispatcher("./quiz/quiz.jsp").forward(request, response);
      */
    } catch (Exception e) {
      e.printStackTrace();
      response.getWriter().println("Erro ao carregar a pergunta: " + e.getMessage());
    }
  }

  
  
  private String getQuestionPoints(String difficulty) {
    String points = "0";
    
    switch (difficulty) {
      case "easy" -> points = "3";
        
      case "medium" -> points = "6";
      
      case "hard" -> points = "10";
    }
    
    return points;
  }
}
