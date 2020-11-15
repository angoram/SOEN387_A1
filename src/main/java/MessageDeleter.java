import businesslayer.ChatManager;
import businesslayer.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessageDeleter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message toDelete = ChatManager.findByMessageID(Integer.parseInt(req.getParameter("messageID")));
        // Check if the user is allowed to delete the given message ID
        if(toDelete != null) {
            if (req.getSession().getAttribute("username").equals(toDelete.getPostedBy())) {
                System.out.println("Deleted message ID " + req.getParameter("messageID") + " successfully.");
                ChatManager.deleteByMessageID(Integer.parseInt(req.getParameter("messageID")));
            } else {
                System.out.println("User is not authenticated to delete that message.");

            }
        }
        else{
            System.out.println("Message with ID "+ req.getParameter("messageID") + " does not exist.");
        }
        resp.sendRedirect("chat.jsp?filter=false");


    }
}
