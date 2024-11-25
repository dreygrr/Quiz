package model;
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
}
