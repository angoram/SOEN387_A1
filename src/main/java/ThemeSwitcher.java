import businesslayer.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ThemeSwitcher")
public class ThemeSwitcher extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("theme").equals("Light Mode")){
            request.getSession().setAttribute("theme","Dark Mode");
        }
        else if(request.getSession().getAttribute("theme").equals("Dark Mode")){
            request.getSession().setAttribute("theme","Light Mode");
        }
        response.sendRedirect("chat.jsp");
    }
}
