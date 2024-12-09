package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.UserDAO;
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

    if (fotoPart == null || fotoPart.getSize() <= 0) {
      response.getWriter().println("Por favor, envie uma foto.");
      return;
    }

    byte[] fotoBytes = fotoPart.getInputStream().readAllBytes();
    String hashedSenha = BCrypt.hashpw(senha, BCrypt.gensalt());

    UserDAO userDAO = new UserDAO();
    if (userDAO.userExists(apelido)) {
      request.getSession().setAttribute("userAlreadyExists", true);
      response.sendRedirect("/Quiz/signup/");
    } else if (userDAO.createUser(nome, apelido, hashedSenha, fotoBytes)) {
      request.getSession().setAttribute("userCreated", true);
      response.sendRedirect("/Quiz/signup/");
    } else {
      response.getWriter().println("Erro ao cadastrar o usuário.");
    }
  }
}
