import businesslayer.ChatManager;
import businesslayer.Message;
import businesslayer.Utilities;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
        InputStream fileInputStream = filePart.getInputStream();
        int nextID = ChatManager.getNextAttachmentID();
        File fileToSave = new File("temporary_attachment_uploads/" + nextID + ".png");
        Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        ChatManager.postMessage(parsedUsername,parsedMessage, nextID + ".png");
        response.sendRedirect("chat.jsp");
    }

    /**
     * DOWNLOAD
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //by default, pressing the front end's Download Chat will set d1 and d2 to null, thus returning all messages.
       Date date1= Utilities.stringToDate(request.getParameter("from"));
       Date date2= Utilities.stringToDate(request.getParameter("to"));
        ArrayList<Message> messages = ChatManager.listMessages(date1,date2);
        String ext = "png";

        response.setContentType("image/" + ext);
        response.setHeader("Content-disposition", "attachment; filename=\""+"temporary_attachment_uploads/" + request.getParameter("filename") + "\"");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "-1");
        response.getOutputStream().write(Files.readAllBytes(new File("temporary_attachment_uploads/" + request.getParameter("filename")).toPath()));
    }
}
