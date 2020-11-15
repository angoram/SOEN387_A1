<%@ page import="businesslayer.ChatManager" %>
<%@ page import="businesslayer.Message" %><%--
  Created by IntelliJ IDEA.
  User: koran
  Date: 11/15/2020
  Time: 1:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit message</title>
</head>
<body>
    <%!
        String getMessage(String messageID){
            int id = Integer.parseInt(messageID);
            Message m = ChatManager.findByMessageID(id);
            return (m.getMessageContent());
        }
    %>
    <h1>Edit message</h1>
    <form method="post" action="MessageEditor?messageID=<%=request.getParameter("messageID")%>">
        <input type="textarea"  value="<%=getMessage(request.getParameter("messageID"))%>" width="100" height="100" name="edited-message">
        <input type="submit" value="Set">
    </form>
</body>
</html>
