package controller;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UserDAO;

@WebServlet(name = "ProfilePictureServlet", urlPatterns = {"/ProfilePicture"})
public class ProfilePictureServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String apelido = request.getParameter("apelido");

    UserDAO userDAO = new UserDAO();

    byte[] foto = userDAO.getProfilePicture(apelido);
    
    if (foto != null) {
      response.setContentType("image/jpeg");
      response.setContentLength(foto.length);
      
      try (OutputStream out = response.getOutputStream()) {
        out.write(foto);
      }
    } else {
      response.getWriter().println("Foto não encontrada.");
    }
  }
}
