<%@ page import="com.appchallengers.appchallengers.dao.dao.RelationshipDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.RelationshipDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.response.UsersBaseData" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 15.4.2018
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootsrapt lins -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="Scripts/jquerylib.js" type="text/javascript"></script>

     <style>

         .daireselimage{
             width: 30px;
             height: 30px;
             border-radius: 150px;
             -webkit-border-radius: 150px;
             -moz-border-radius: 150px;
             background: url(http://resim-dosyasinin.adresi/resim.jpg) no-repeat;
         }

     </style>

    <title>Title</title>
</head>
<body>

<%!
   int userid;
    List<UsersBaseData> usersBaseDataList;
%>
<%
   userid=Integer.parseInt(request.getParameter("userid"));
    RelationshipDao relationshipDao= new RelationshipDaoImpl();
    usersBaseDataList=relationshipDao.getFriends(userid);
%>
<%for(UsersBaseData feed: usersBaseDataList){%>
<ul class="list-inline">
    <li>
        <div class="profil-image" >
            <a href="http://localhost:8080/profil.jsp?ref=1">
                <img src="<%=feed.getProfile_picture()%>" alt="profil.jpg"  class="daireselimage">
                <!-- yukarıya resim dinamik gelecek-->
            </a>
        </div>
    <li>
        <div  class="fullname text-info">
            <a href="http://localhost:8080/profil.jsp?ref=1">
                <strong><%=feed.getFullName()%></strong>
            </a>
        </div>
    </li>
</ul>
<hr>
<%}%>

</body>
</html>
