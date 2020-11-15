import businesslayer.ChatManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            if(username.equals(ChatManager.sampleUsername) && ChatManager.hashedPasswordDigest(password).equals(ChatManager.samplePasswordHashed)){
                request.getSession().setAttribute("username",username);
                response.sendRedirect("chat.jsp?filter=false");
            }
            else{
                response.sendRedirect("login.jsp?login-error=invalid-credentials");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
