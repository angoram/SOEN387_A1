import businesslayer.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@WebServlet(name = "ChatLogDeleter")
/**
 * Deletes all messages in the chat using a GET request. The request contains the user's id
 * to identify the user that cleared the chat.
 */
public class ChatLogDeleter extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date date1= Utilities.stringToDate(request.getParameter("from"));
        Date date2= Utilities.stringToDate(request.getParameter("to"));
        ChatManager.clearChat(date1,date2);
        response.sendRedirect("chat.jsp");
    }
}
