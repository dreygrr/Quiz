package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {
  private String question;
  private String category;
  private String difficulty;
  private String correctAnswer;
  private List<String> incorrectAnswers;
  
  public Question() {}

  public Question(String question, String category, String difficulty, String correctAnswer, List<String> incorrectAnswers) {
    this.question = question;
    this.category = category;
    this.difficulty = difficulty;
    this.correctAnswer = correctAnswer;
    this.incorrectAnswers = new ArrayList<>(incorrectAnswers); // Garantir cópia para evitar mutação externa
  }
  
  public String getId() {
    return Integer.toHexString(question.hashCode());
  }

  public String getQuestion() {
    return question;
  }
  public void setQuestion(String question) {
    this.question = question;
  }

  public String getCategory() {
    return category;
  }
  public void setCategory(String category) {
    this.category = category;
  }

  public String getDifficulty() {
    return difficulty;
  }
  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }

  public String getCorrectAnswer() {
    return correctAnswer;
  }
  public void setCorrectAnswer(String correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

  public List<String> getIncorrectAnswers() {
    return new ArrayList<>(incorrectAnswers); // Retorna cópia para segurança
  }
  public void setIncorrectAnswers(List<String> incorrectAnswers) {
    this.incorrectAnswers = incorrectAnswers;
  }

  public List<String> getAllAnswersShuffled() {
    List<String> allAnswers = new ArrayList<>(incorrectAnswers);
    allAnswers.add(correctAnswer);
    Collections.shuffle(allAnswers);
    return allAnswers;
  }

  public boolean isCorrect(String userAnswer) {
    return correctAnswer.equals(userAnswer);
  }

  public int getPoints() {
    return switch (difficulty.toLowerCase()) {
      case "easy" -> 3;
      case "medium" -> 6;
      case "hard" -> 10;
      default -> 0;
    };
  }
}
