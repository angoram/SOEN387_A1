import businesslayer.ChatManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;



/**
 * Handles the passing of a message from a user to the server, and from the server
 * over to the user.
 */
@MultipartConfig
@WebServlet(
        name = "MessagePasser",
        urlPatterns={"/MessagePasser"}
)
public class MessagePasser extends HttpServlet {
    /**
     * SEND A MESSAGE
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameSet = (String)request.getSession().getAttribute("username");
        String parsedUsername = usernameSet==null||usernameSet.equals("")?"Anonymous":usernameSet;
        String messageSet = request.getParameter("message");
        String parsedMessage = messageSet==null||messageSet.equals("")?"[No content]":messageSet;
        Part filePart = request.getPart("file");
        InputStream fileInputStream = null;
        if(filePart.getSize() > 0) {
            fileInputStream = filePart.getInputStream();
        }
        ChatManager.postMessage(parsedUsername,parsedMessage,fileInputStream);
        response.sendRedirect("chat.jsp?filter=false");
    }

    /**
     * DOWNLOAD THE IMAGE
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ext = "png";
        response.setContentType("image/" + ext);
        response.setHeader("Content-disposition", "attachment; filename=attachment.png");
        InputStream is = ChatManager.getAttachmentBytes(Integer.parseInt(request.getParameter("messageID")));
        byte[] bytes = IOUtils.toByteArray(is);
        System.out.println(bytes);
        response.getOutputStream().write(bytes);
    }
}
