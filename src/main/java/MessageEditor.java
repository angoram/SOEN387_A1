import businesslayer.ChatManager;
import businesslayer.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessageEditor extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message messageToEdit = ChatManager.findByMessageID(Integer.parseInt(req.getParameter("messageID")));
        String editedMessage = req.getParameter("edited-message");
        if(req.getSession().getAttribute("username").equals(messageToEdit.getPostedBy())){
            messageToEdit.setMessageContent(editedMessage);
            messageToEdit.setEdited(true);
            System.out.println("Message edited.");
        }
        else{
            System.out.println("User is not authenticated to edit that message.");
        }
        resp.sendRedirect("chat.jsp?filter=false");

    }
}
