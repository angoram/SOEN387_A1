<%@ page import="businesslayer.ChatManager" %>
<%@ page import="java.io.IOException" %>
<%@ page import="businesslayer.Message" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.io.File" %>
<!DOCTYPE html>
<%--

  Created by IntelliJ IDEA.
  User: koran
  Date: 10/8/2020
  Time: 11:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    if(request.getSession().getAttribute("username") == null){
        response.sendRedirect("login.jsp?login-error=not-logged-in");
    }
%>
<%@ page contentType="text/html;charset=UTF-8"%>

    <head>
        <title>Chat Window</title>
        <link rel="stylesheet" href="stylesheets/chat_window_styling.css">
    </head>
    <body>


    <h1 id="title">Soen 387 Chat</h1>

    <div id="chat_window">
        <div id="top_bar">
            <div id="user_bar">
                <span id="welcome_message">
                    <%!
                        String printWelcomeMessage(String username){
                            return "Welcome, " + username;
                        }
                    %>
                    <%=printWelcomeMessage((String) request.getSession().getAttribute("username"))%>
                </span>
            </div>
            <a href="chat.jsp?filter=false" id="refresh_btn" >🗘</a>
        </div>
        <div id="search_bar">
            <form method="get" action="Searcher">
                <input type="text" name="username" placeholder="search user..">
                <input type="text" name="hashtag" placeholder="hashtags..">
                <input type="text" name="date1" placeholder="date1..">
                <input type="text" name="date2" placeholder="date2..">
                <input type="submit" name="submit" value="Search!">
            </form>
        </div>
        <div id="chat_log_container">
            <table class="chat_log">
                <%!
                    String printIfEdited(int messageID){
                        if(ChatManager.findByMessageID(messageID).isEdited()){
                            return "(edited)";
                        }
                        else{
                            return "";
                        }
                    }
                %>
                <%!
                    String getMessages(String username, String filter) throws IOException {
                        ArrayList<Message> list = null;
                        if(filter.equals("true")){
                            System.out.println("Showing FILTERED messages");
                            list = ChatManager.messagesToShow;

                            }
                            else{
                            System.out.println("Showing ALL messages");
                            list = ChatManager.messages;
                            }
                            String rows = "";
                            int imageName = 0;
                            for(Message m : list) {
                                imageName++;


                                rows += "<tr class=\"chat_log_block\">";
                                rows += "<td class=\"user\"><p>" + m.getPostedBy() + "</p></td>";
                                rows += "<td class=\"message\">" + "<p>" +m.getMessageContent() + "</p>";
                                rows += "<i>" + printIfEdited(m.getMessageID()) + "</i>";
                                rows += "\n";
                                if (m.getPostedBy().equals(username)) {
                                    rows += "<a href='edit_message.jsp?messageID=" + m.getMessageID() + "'>Edit</a>";
                                    rows += "<a href='MessageDeleter?messageID=" + m.getMessageID() + "'>Delete</a>";
                                }
                                BufferedImage image = null;
                                if (m.getAttachmentBytes() != null) {
                                    try {
                                        image = ImageIO.read(m.getAttachmentBytes());
                                        System.out.println(image);
                                    } catch (IOException e) {
                                        System.out.println(e);
                                    }
                                    if (image != null) {
                                        ImageIO.write(image, "png", new File(imageName + ".png"));
                                        rows += "<a href='MessagePasser?messageID=" + m.getMessageID() + "'><img src='" + imageName + ".png" + "' width='200' height='200'></a>" +
                                                "</td>";
                                    }
                                }
                                rows += "<td class=\"date\">" + m.getDatePosted() + "</td>";
                                rows += "</tr>";
                            }
                            return rows;
                        }
                    %>
                    <%=getMessages((String)request.getSession().getAttribute("username"),request.getParameter("filter"))%>
                }

            </table>
        </div>
        <div id="send_message_area">
            <form action="MessagePasser" method="post" enctype="multipart/form-data">
                <input type="textarea" id="message_to_send" name="message" placeholder="Send message.." width="140" height="140" rows="5" cols = "40">
                <input type="file" name="file"/>
                <input type="submit" name="submit" id="send_message_btn" value="↩">
            </form>
        </div>
    </div>
    </body>
</html>