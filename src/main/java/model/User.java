package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
  private String nome;
  private String apelido;
  private String senha;
  private String entrouEm;
  
  public User() {}

  public User(String nome, String apelido, String entrouEm) {
    this.nome = nome;
    this.apelido = apelido;
    this.entrouEm = entrouEm;
  }
  
  public User(String nome, String apelido, String senha, String entrouEm) {
    this.nome = nome;
    this.apelido = apelido;
    this.senha = senha;
    this.entrouEm = entrouEm;
  }

  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  public User(String username) {
    this.apelido = username;
  }

  public String getApelido() {
    return apelido;
  }
  public void setApelido(String apelido) {
    this.apelido = apelido;
  }

  public String getSenha() {
    return senha;
  }
  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getEntrouEm() {
    return entrouEm;
  }
  public void setEntrouEm(String entrouEm) {
    this.entrouEm = entrouEm;
  }
  
  public String getFormattedEntrouEm() {
    String rawDate = this.entrouEm; // Exemplo: "2024-11-25 16:37:02"
    
    try {
      // Converte a string original para um objeto Date
      SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = originalFormat.parse(rawDate);

      // Formata o objeto Date para o formato desejado
      SimpleDateFormat desiredFormat = new SimpleDateFormat("dd/MM/yyyy");
      
      return desiredFormat.format(date);
    } catch (Exception e) {
        e.printStackTrace();
        return rawDate; // Retorna a data original caso ocorra erro
    }
  }
}
