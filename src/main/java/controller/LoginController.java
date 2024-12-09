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

import model.User;
import model.UserDAO;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String apelido = request.getParameter("apelido");
    String senha = request.getParameter("senha");

    UserDAO userDAO = new UserDAO();
    
    if (userDAO.validateLogin(apelido, senha)) {
      User user = userDAO.getUser(apelido);
      request.getSession().setAttribute("_user", user);
      response.sendRedirect("/Quiz/dashboard/");
    } else {
      request.getSession().setAttribute("wrongCredentials", true);
      response.sendRedirect("/Quiz/signin/");
    }
  }
}
