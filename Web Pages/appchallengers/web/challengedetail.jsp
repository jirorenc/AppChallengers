<%@ page import="com.appchallengers.appchallengers.dao.dao.ChallengesDetailInfoDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.ChallengesDetailInfoDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.response.GetChallengeDetailInfoModel" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.entity.Users" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 17.4.2018
  Time: 23:00
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
         .panel{
             border-top: 0px;
         }
     </style>
    <title>Title</title>
</head>
<body>

<%!
  int challengeid;
  Users users;
  long userid;
  GetChallengeDetailInfoModel  detailModel;
%>
<%
    UserDao userDao= new UserDaoImpl();
    HttpSession session1 = request.getSession(false);
    String userName = null;
    javax.servlet.http.Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (javax.servlet.http.Cookie cookie : cookies) {
            if (cookie.getName().equals("user"))
                userName = cookie.getValue();
        }
    }else{
        userName=(String)session1.getAttribute("username");
    }
    users=userDao.findByEmail(userName);
    userid=users.getId();
    challengeid = Integer.parseInt(request.getParameter("ref"));
    System.out.println("-->>>>>>>>aaaa"+challengeid);
    ChallengesDetailInfoDao challengesDetailInfoDao=new ChallengesDetailInfoDaoImpl();
    detailModel=challengesDetailInfoDao.getChallengeDetailInfo(challengeid);

%>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-2"></div>
            <div class="col-sm-8">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" onclick="toggle()"  data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="index.jsp">AppChallengers</a>
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav">
                        <li ><!-- Give Link!--><a href="index.jsp"><i class="glyphicon glyphicon-home"></i></a></li>
                        <li class="active"><a href="trends.jsp"><!-- Give Link!--><i class="glyphicon glyphicon-fire"></i></a></li>
                        <li ><a href="myprofile.jsp"><!-- Give Lİnk!--><i class="glyphicon glyphicon-user"></i></a></li>
                    </ul>
                    <form class="navbar-form navbar-right" >
                        <div class="input-group">
                            <input type="text" class="form-control input-sm" placeholder="Search" name="search" >
                            <div class="input-group-btn">
                                <button class="btn btn-default input-sm" type="submit">
                                    <i class="glyphicon glyphicon-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-sm-2"></div>
        </div>
    </div>
</nav>
<br><br><br>

 <div class="container">
     <div class="row">
         <div class="col-sm-2"></div>
         <div class="col-sm-8">
             <div class="panel panel-default">
                 <div class="text-info">
                     <ul class="list-inline">
                         <li><h3><%=detailModel.getHeadLine()%></h3></li>
                         <li><h3><%=detailModel.getCounter()%></h3></li>
                     </ul>
                 </div>
                 <ul class="nav nav-tabs nav-justified">
                     <li id="popular" class="active"><a href="javascript:popular();"><strong>MyChallenge</strong></a></li>
                     <li id="enson"><a href="javascript:enson();"><strong>Accepted Challenge</strong> </a></li>
                 </ul>
                 <div class="panel-body">

                 </div>
             </div>
         </div>
         <div class="col-sm-2"></div>
     </div>
 </div>

<div id="sonuc"></div>


<script  type="text/javascript">
    var status=-1;
    function popular() {
        status=0;
        $('#popular').addClass("active");
        $('#enson').removeClass("active");
        $.ajax({
            url:"http://localhost:8080/challengedetailresponse.jsp",
            data:{status:status,userid:<%=userid%>,challengeid:<%=challengeid%>},
            success:function (response) {
                $("#sonuc").html(response);
            }
        });

    }
    function enson() {
        status=1;
        $('#popular').removeClass("active");
        $('#enson').addClass("active");
        $.ajax({
            url:"http://localhost:8080/challengedetailresponse.jsp",
            data:{status:status,userid:<%=userid%>,challengeid:<%=challengeid%>},
            success:function (response) {
                $("#sonuc").html(response);
            }
        });
    }

    function toggle() {
        $('.collapse').toggle();
    }

</script>
</body>
</html>
