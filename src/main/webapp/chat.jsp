<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="businesslayer.*" %>
<%--

  Created by IntelliJ IDEA.
  User: koran
  Date: 10/8/2020
  Time: 11:50 PM
  To change this template use File | Settings | File Templates.
--%>
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
            <a href="chat.jsp" id="refresh_btn" >🗘</a>
        </div>
        <div id="chat_log_container">
            <table class="chat_log">
                <%!
                    //returns a chat log and fits it at the bottom of the chat log container.
                    String messagesAsTableRows(){
                        String rows = "";
                        for(Message m : ChatManager.listMessages(null,null)) {
                            rows += "<tr class=\"chat_log_block\">";
                                rows += "<td class=\"user\"><p>" + m.getPostedBy()+"</p></td>";
                                rows += "<td class=\"message\">" + m.getMessageContent() + "</td>";
                                rows += "<td class=\"date\">" + m.getDatePosted() + "</td>";
                            rows += "</tr>";
                        }
                        return rows;
                    }
                %>
                <%=messagesAsTableRows()%>
            </table>
        </div>
        <div id="send_message_area">
            <form action="MessagePasser" method="post">
                <input type="textarea" id="message_to_send" name="message" placeholder="Send message.." width="140" height="140" rows="5" cols = "40">
                <input type="submit" name="submit" id="send_message_btn" value="↩">
            </form>

        </div>
    </div>
    </body>
</html>