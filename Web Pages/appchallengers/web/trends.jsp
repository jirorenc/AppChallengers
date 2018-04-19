<%@ page import="com.appchallengers.appchallengers.dao.dao.ApplicationDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.ApplicationDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.response.GetTrendsResponse" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 17.4.2018
  Time: 20:57
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
        #fire {
            font-size: 30px;
        }
        #trend-fire{
            font-size: 20px;
            color:#e5590f;
        }

    </style>
    <title>Title</title>
</head>
<body>
<%!
    List<GetTrendsResponse> trendlist;
%>
<%
    ApplicationDao applicationDao= new ApplicationDaoImpl();
    trendlist=applicationDao.getTrendsList();
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
                    <form class="navbar-form navbar-right" action="/action_page.php">
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
            <div class="panel panel-deafult">
                <div class="panel-heading ">
                    <div class="text-center">
                        <ul class="list-inline">
                            <li><i  id="fire" class="glyphicon glyphicon-fire" ></i></li>
                            <li><h3 style:color:#e5590f;>Thrends</h3></li>
                        </ul>
                    </div>

                </div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <tbody>
                        <%for (GetTrendsResponse feed: trendlist){%>
                        <tr>
                            <td>
                                <ul class="list-inline">
                                    <li><a href="challengedetail.jsp?ref=<%=feed.getChallengeId()%>"><span id="trend-fire" class="glyphicon glyphicon-fire"></span></a></li>
                                    <li><a href="challengedetail.jsp?ref=<%=feed.getChallengeId()%>"><%=feed.getHeadLine()%></a></li>
                                </ul>
                            </td>
                            <td><%=feed.getCounter()%></td>
                        </tr><%}%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-sm-2"></div>
    </div>
</div>

<script  type="text/javascript">
    function toggle() {
        $('.collapse').toggle();
    }
</script>

</body>
</html>
