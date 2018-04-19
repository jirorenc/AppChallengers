<%@ page import="com.appchallengers.appchallengers.model.entity.Users" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %><%--
  Created by IntelliJ IDEA.
  User: MHMTNASIF
  Date: 19.04.2018
  Time: 04:38
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
    <title>Title</title>
</head>
<body>
<%!
   int userid;

<%

    userid=Integer.parseInt(request.getParameter("userid"));
   /* data=request.getParameter("data");
    int length=data.length();
    int first=data.indexOf("a");
    int second=data.indexOf("b");
    int third=data.indexOf("c");

    kimdenid=Integer.parseInt(data.substring(0,first));
    kimeid= Integer.parseInt(data.substring(first+1,second));
    actionid=Integer.parseInt(data.substring(second+1,third));
    typeid=Integer.parseInt(data.substring(third+1,length));

    UserDao userDao=new UserDaoImpl();
    user1=userDao.findUserById(kimdenid);
    user2=userDao.findUserById(kimeid);
    actionuser=userDao.findUserById(actionid);*/

/*
    String natification_content = "";
    if (typeid == 0) {
        natification_content = " " + "sana " + mNotificationList.get(i).getChallengeHeadLine() + " için meydan okudu";
    } else if (typeid == 1) {
        natification_content = " " + "sana arkadaş isteği gönderdi";
    } else if (typeid == 2) {
        natification_content = " " + "senin arkadaş isteğini kabul etti";
    } else if (typeid== 3) {
        natification_content = " " + "senin " + mNotificationList.get(i).getChallengeHeadLine() + " için meydan okumanı beğendi";
    }*/

%>
BİLDİRİMLER
</body>
</html>
