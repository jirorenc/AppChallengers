<%@ page import="com.appchallengers.appchallengers.model.response.UsersBaseData" %>
<%@ page import="java.util.List" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.VotesDaoImpl" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 16.3.2018
  Time: 03:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .daireselimage{
            width: 40px;
            height: 40px;
            border-radius: 150px;
            -webkit-border-radius: 150px;
            -moz-border-radius: 150px;
            background: url(http://resim-dosyasinin.adresi/resim.jpg) no-repeat;
        }
    </style>
</head>
<body>
<%
    int postid=Integer.parseInt(request.getParameter("postid"));

    //kimlerin begendiği alınacak ve asağıda templet dinamik doldurulacak
    // begenenlerin id si ve  ismi alınacacak
    VotesDaoImpl votesDao=new VotesDaoImpl();
    List<UsersBaseData> usersBaseData=votesDao.getVotes(postid);
    System.out.println("AAAAAAAAAAA----------->>>>>Girdiiii");
%>
<%for(UsersBaseData feed: usersBaseData){%>
<ul class="list-inline">
    <li>
        <div class="profil-image" >
            <a href="http://localhost:8080/profile.jsp?ref=<%=feed.getId()%>">
                <img src="<%=feed.getProfile_picture()%>" alt="profil.jpg"  class="daireselimage">
                <!-- yukarıya resim dinamik gelecek-->
            </a>
        </div>
    <li>
        <div  class="fullname text-info">
            <a href="http://localhost:8080/profile.jsp?ref=<%=feed.getId()%>">
                <strong><%=feed.getFullName()%></strong>
            </a>
        </div>
    </li>
</ul>
<hr>
<%}%>
</body>
</html>
