import businesslayer.ChatManager;
import businesslayer.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

@WebServlet(name = "Searcher")

public class Searcher extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ChatManager.filterMessages(request.getParameter("username"),request.getParameter("hashtag"),request.getParameter("date1"),request.getParameter("date2"));
            response.sendRedirect("chat.jsp?filter=true");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
