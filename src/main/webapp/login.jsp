<%--
  Created by IntelliJ IDEA.
  User: koran
  Date: 11/14/2020
  Time: 10:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <h1>Log In</h1>
    <form method="post" action="LoginServlet">
        <input type="text" name="username">
        <input type="text" name="password">
        <input type="submit" value="Login">
    </form>
    <%!
        String usernameOrPass(){
            return "<span style='color:red'>Username or password is not correct.</span>";
        }
    %>
    <%!
        String needToLogInFirst(){
            return "<span style='color:purple'>You need to log in first.</span>";
        }
    %>
    <%
        if(request.getParameter("login-error")!=null) {
            if (request.getParameter("login-error").equals("invalid-credentials")) {
                response.getWriter().print(usernameOrPass());
            } else if (request.getParameter("login-error").equals("not-logged-in")) {
                response.getWriter().print(needToLogInFirst());
            }
        }
    %>
</head>
<body>

</body>
</html>
