import businesslayer.ChatManager;
import businesslayer.Message;
import businesslayer.Utilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Handles the passing of a message from a user to the server, and from the server
 * over to the user.
 */
@WebServlet(name = "MessagePasser")
public class MessagePasser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameSet = (String)request.getSession().getAttribute("username");
        String parsedUsername = usernameSet==null||usernameSet.equals("")?"Anonymous":usernameSet;
        String messageSet = request.getParameter("message");
        String parsedMessage = messageSet==null||messageSet.equals("")?"[No content]":messageSet;
        ChatManager.postMessage(parsedUsername,parsedMessage);
        response.sendRedirect("chat.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //by default, pressing the front end's Download Chat will set d1 and d2 to null, thus returning all messages.
       Date date1= Utilities.stringToDate(request.getParameter("from"));
       Date date2= Utilities.stringToDate(request.getParameter("to"));
        ArrayList<Message> messages = ChatManager.listMessages(date1,date2);
        // Now we have the messages between the specified dates.
        String format = request.getParameter("format");
        if(format == null){
            format = "plain";
        }
        String result = "";
        if(format.equals("plain")) {
            for (Message m : messages) {
                result += m.getPostedBy() + ": " + m.getMessageContent() + " (" + m.getDatePosted() + ")" + "\n";
            }
        }
        else if(format.equals("xml")){
            result += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            for (Message m : messages) {
                result += "<message>\n"+
                        "\t<posted-by>\n" +
                        "\t\t"+m.getPostedBy() + "\n"+
                        "\t</posted-by>\n"+
                        "\t<message-content>\n"+
                        "\t\t"+m.getMessageContent()+"\n"+
                        "\t</message-content>\n"+
                        "\t<date-posted>\n"+
                        "\t\t" + m.getDatePosted() + "\n"+
                        "\t</date-posted>\n"+
                        "</message>\n";
            }
        }
        String ext = "txt";
        if(format.equals("xml")){
            ext = "xml";
        }
        response.setContentType("text/" + format);
        response.setHeader("Content-disposition", "attachment; filename=\""+"messages."+ ext+"\"");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "-1");

        response.getOutputStream().write(result.getBytes());
    }
}
