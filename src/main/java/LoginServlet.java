import businesslayer.ChatManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginServlet extends HttpServlet {
    /**
     * SIGN IN
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            System.out.println(ChatManager.hashedPasswordDigest("387"));
            if(ChatManager.users.containsKey(username) && ChatManager.hashedPasswordDigest(password).equals(ChatManager.users.get(username))){
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

    /**
     * SIGN OUT
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("username",null);
        resp.sendRedirect("login.jsp");
    }
}
