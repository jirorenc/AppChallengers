<%@ page import="com.appchallengers.appchallengers.dao.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.entity.Users" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.GetUserInfoDao" %>
<%@ page import="com.appchallengers.appchallengers.model.response.ChallengeResponse" %>
<%@ page import="java.util.List" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.GetUserInfoDaoImpl" %><%--
  Created by IntelliJ IDEA.
  User: rabb�m
  Date: 15.4.2018
  Time: 13:49
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
         .dairesel{
             width: 50px;
             height: 50px;
             border-radius: 150px;
             -webkit-border-radius: 150px;
             -moz-border-radius: 150px;
             background: url(http://resim-dosyasinin.adresi/resim.jpg) no-repeat;
         }
     </style>
    <title>AppChallengers</title>
</head>
<body>

<!-- Bu sayfa kullan�c� kabul etti�i cahllenge lar� g�rmek istegi�inde ajax cevab� olarak
     d�nderilecek.-->

 <%!
     int userid;
     int visitorid;
     int status;
 %>
 <%
   userid=Integer.parseInt(request.getParameter("userid"));
   status=Integer.parseInt(request.getParameter("status"));
     UserDao userDao= new UserDaoImpl();
     Users users=userDao.findUserById(userid);
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
     Users session_user= userDao.findByEmail(userName);
     visitorid=(int)session_user.getId();
     System.out.println("--->>>>"+status);
     // Kabul eden ki�iler listesi olu�turulacak
     GetUserInfoDao getUserInfoDao= new GetUserInfoDaoImpl();
     List<ChallengeResponse> userChallengeFeedList2 = getUserInfoDao.getUserAcceptedChallenges(visitorid, userid);
     List<ChallengeResponse> userChallengeFeedList = getUserInfoDao.getUserChallenges(visitorid, userid);

 %>

  <%if(status==0){%>
   <!-- Bu kabul edilen chalenge postlar�ya doldurulacak  bura yukar�daki kanbul eden liste g�re de�itirilecek-->
<%for(ChallengeResponse feed: userChallengeFeedList2){%>
<div class="container">
    <div class="row">
        <!-- LEFT PART -->
        <div class="col-sm-2"></div>
        <!-- CONTENT PART-->
        <div class="col-sm-8">
            <!-- PANEL BEGINNIG -->
            <div class="vote" id="vote-<%=feed.getChallenge_detail_id()%>"><%=feed.getVote()%></div>
            <div class="panel panel-default" id="p<%=feed.getChallenge_detail_id()%>">
                <div class="panel-heading " style="background-color: #fefefe">
                    <ul class="list-inline">
                        <li>

                            <img src="<%=feed.getProfilepicture()%>" alt="profil.jpg"  class="dairesel">

                        <li>
                            <div class="text-info"><strong><%=feed.getFullname()%></strong></div><!-- jsp?=feed.getChallenge_detail_user_id()-->
                        </li>
                        <li><strong>></strong></li>
                        <li><div class="text-muted"><%=feed.getHeadline()%></div></li>
                    </ul></div>
                <div class="panel-body">
                    <div align="center"  class="embed-responsive embed-responsive-4by3">
                        <video id="video-<%=feed.getChallenge_detail_id()%>" controls muted autoplay loop class="embed-responsive-item" onclick="this.paused ? this.play() : this.pause();">
                            <source src="<%=feed.getChallenge_url()%>" type="video/mp4">
                            <track src="<%=feed.getChallenge_url()%>" label="English subtitles"
                                   kind="subtitles" srclang="en" default></track>
                        </video>
                    </div>
                </div>
                <div class="panel-footer" style="background-color: #fefefe">

                    <ul class="list-inline">
                        <li>
                            <button type="button" class="btn btn-default btn-sm" id="btnlike-<%=feed.getChallenge_detail_id()%>"><span
                                    class="glyphicon glyphicon-thumbs-up" ></span>Like
                            </button>
                        </li>
                        <li><div class="modal-like" id="likenumber-<%=feed.getChallenge_detail_id()%>" data-toggle="modal" data-target="#begenenler" ><%=feed.getLikes()%></div></li>

                    </ul>
                    <div align="right">
                        <button type="button" id="btn_like8"><span class="glyphicon glyphicon-flag"></span></button>
                    </div>
                    <!--  <span class="pull-right"><i class="glyphicon glyphicon-search"></i></span> -->
                </div>
                <!-- PANEL ENDING -->
            </div>
        </div>
        <!--RIGHT PART -->
        <div class="col-sm-2"></div>
    </div>
</div>

<script  type="text/javascript">
    $(document).ready(function () {
        $('.vote').hide();
        <% if(feed.getVote()==1){ %>
        // $('#btnlike-<%=feed.getChallenge_detail_id()%>').css("background-color","yellow");
        $('#btnlike-<%=feed.getChallenge_detail_id()%>').css("background-color","yellow");
        <%}%>
    });

</script>

<%}%>

 <%}%>

 <%if(status==1){%>
<!-- Bu k�s�m mychallge k�sm�n� doldurucack profilin ilk yuklendi�i duruuyla ayn� olacak-->
<%for(ChallengeResponse feed: userChallengeFeedList){%>
<div class="container">
    <div class="row">
        <!-- LEFT PART -->
        <div class="col-sm-2"></div>
        <!-- CONTENT PART-->
        <div class="col-sm-8">
            <!-- PANEL BEGINNIG -->
            <div class="vote" id="vote-<%=feed.getChallenge_detail_id()%>"><%=feed.getVote()%></div>
            <div class="panel panel-default" id="p<%=feed.getChallenge_detail_id()%>">
                <div class="panel-heading " style="background-color: #fefefe">
                    <ul class="list-inline">
                        <li>

                            <img src="<%=feed.getProfilepicture()%>" alt="profil.jpg"  class="dairesel">

                        <li>
                            <div class="text-info"><strong><%=feed.getFullname()%></strong></div><!-- jsp?=feed.getChallenge_detail_user_id()-->
                        </li>
                        <li><strong>></strong></li>
                        <li><div class="text-muted"><%=feed.getHeadline()%></div></li>
                    </ul></div>
                <div class="panel-body">
                    <div align="center"  class="embed-responsive embed-responsive-4by3">
                        <video id="video-<%=feed.getChallenge_detail_id()%>" controls muted autoplay loop class="embed-responsive-item" onclick="this.paused ? this.play() : this.pause();">
                            <source src="<%=feed.getChallenge_url()%>" type="video/mp4">
                            <track src="<%=feed.getChallenge_url()%>" label="English subtitles"
                                   kind="subtitles" srclang="en" default></track>
                        </video>
                    </div>
                </div>
                <div class="panel-footer" style="background-color: #fefefe">

                    <ul class="list-inline">
                        <li>
                            <button type="button" class="btn btn-default btn-sm" id="btnlike-<%=feed.getChallenge_detail_id()%>"><span
                                    class="glyphicon glyphicon-thumbs-up" ></span>Like
                            </button>
                        </li>
                        <li><div class="modal-like" id="likenumber-<%=feed.getChallenge_detail_id()%>" data-toggle="modal" data-target="#begenenler" ><%=feed.getLikes()%></div></li>

                    </ul>
                    <div align="right">
                        <button type="button" id="btn_like5"><span class="glyphicon glyphicon-flag"></span></button>
                    </div>
                    <!--  <span class="pull-right"><i class="glyphicon glyphicon-search"></i></span> -->
                </div>
                <!-- PANEL ENDING -->
            </div>
        </div>
        <!--RIGHT PART -->
        <div class="col-sm-2"></div>
    </div>
</div>

<script  type="text/javascript">
    $(document).ready(function () {
        $('.vote').hide();
        <% if(feed.getVote()==1){ %>
        // $('#btnlike-<%=feed.getChallenge_detail_id()%>').css("background-color","yellow");
        $('#btnlike-<%=feed.getChallenge_detail_id()%>').css("background-color","yellow");
        <%}%>
    });

</script>

<%}%>
</div>
 <%}%>
</body>
</html>
