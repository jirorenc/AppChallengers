<%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 18.2.2018
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        HttpSession session1=request.getSession(false);
         if(session1.getAttribute("username")==null){
             response.sendRedirect("Login.jsp");
         }else {
             response.sendRedirect("mainpage.jsp");
         }
    %>
<h1>aa</h1>
 <h1>aaa</h1>
</body>
</html>
