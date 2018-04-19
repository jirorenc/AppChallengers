<%@ page import="com.appchallengers.appchallengers.model.entity.Users" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.RelationshipDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.RelationshipDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.response.ChallengeResponse" %>
<%@ page import="java.util.List" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.GetUserInfoDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.GetUserInfoDao" %>
<%@ page import="com.appchallengers.appchallengers.model.response.GetUserInfoResponseModel" %>
<%@ page import="com.appchallengers.appchallengers.mongodb.model.ChallengedModel" %>
<%@ page import="com.google.gson.Gson" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 15.4.2018
  Time: 22:49
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
        .daireselprofil{
            width: 150px;
            height: 150px;
            border-radius: 150px;
            -webkit-border-radius: 150px;
            -moz-border-radius: 150px;
            background: url(http://resim-dosyasinin.adresi/resim.jpg) no-repeat;
            margin-right: 10px;
            margin-bottom: 20px;
        }
        .dairesel{
            width: 50px;
            height: 50px;
            border-radius: 150px;
            -webkit-border-radius: 150px;
            -moz-border-radius: 150px;
            background: url(http://resim-dosyasinin.adresi/resim.jpg) no-repeat;
        }

        .nav-tabs > li, .nav-pills > li {
            float:none;
            display:inline-block;
            *display:inline; /* ie7 fix */
            zoom:1; /* hasLayout ie7 trigger */
        }

    </style>

    <title>Title</title>
</head>
<body>

<%!
  RelationshipDao relationshipDao;
%>
<%

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

    UserDao userDao= new UserDaoImpl();
    Users users= userDao.findByEmail(userName);
    relationshipDao=new RelationshipDaoImpl();
    GetUserInfoDao getUserInfoDao= new GetUserInfoDaoImpl();
    List<ChallengeResponse> userChallengeFeedList = getUserInfoDao.getUserChallenges(users.getId(), users.getId());
    GetUserInfoResponseModel getUserInfoResponseModel=getUserInfoDao.getUserInfo(users.getId(),users.getId());
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
                        <li><a href="#"><!-- Give Link!--><i class="glyphicon glyphicon-fire"></i></a></li>
                        <li class="active"><a href="#"><!-- Give Lİnk!--><i class="glyphicon glyphicon-user"></i></a></li>
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
<br><br><br><br><br>
<div id="sonuc">

</div>
<div class="container" >
    <div class="row">
        <div class="col-sm-2">
        </div>
        <div class="col-sm-8" >
            <div class="text-center">
                <ul class="list-inline">
                    <li>
                        <p><img src="<%=users.getProfilePicture()%>" alt="profil.jpg" class="daireselprofil"></p>
                    </li>
                    <li>
                        <ul  style="list-style-type:none" >
                            <li>
                                <ul class="list-inline">
                                    <li><h3><%=users.getFullName()%></h3></li>
                                   <li><button type="button" class="btn btn-default input-sm">Profili Düzenle</button></li>
                                    <li id="setting" > <div class="dropdown">
                                        <button class="btn btn-default dropdown-toggle input-sm" type="button" data-toggle="dropdown">
                                            <span class="glyphicon glyphicon-cog"></span>
                                            <span class="caret"></span></button>
                                        <ul class="dropdown-menu">
                                            <li><a href="changepassword.jsp">Genel Ayarlar</a></li>
                                            <li><a href="LogoutServlet">Çıkış Yap</a></li>
                                            <li><a href="#">İptal</a></li>
                                        </ul>
                                    </div></li>
                                </ul>
                            </li>
                            <li  style="margin-top:8px">
                                <ul class="list-inline">
                                    <li class="info"><strong><%=getUserInfoResponseModel.getChallenges() %></strong> Challenges</li>
                                    <li class="info modal-like"  id="friends" data-toggle="modal" data-target="#arkadaslar" ><strong><%=getUserInfoResponseModel.getFriends() %></strong> Arkadaşlar</li>
                                    <li class="info"><strong><%=getUserInfoResponseModel.getAccepted_challenges()%></strong> Accepted Challenges</li>

                                </ul>
                            </li>
                        </ul>

                    </li>
                </ul>
            </div>
            <hr>
            <ul class="nav nav-tabs nav-justified">
                <li id="mychallenge" class="active"><a href="javascript:myChallenge();"><strong>MyChallenge</strong></a></li>
                <li id="acceptedchallenge"><a href="javascript:acceptedChallenge();"><strong>Accepted Challenge</strong> </a></li>
            </ul>
        </div>
        <div class="col-sm-2">
        </div>
    </div>
</div>
<div class="content">
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
<div class="modal fade" id="begenenler" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Begenenler</h4>
            </div>
            <div class="modal-body" id="modal-content">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="arkadaslar" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Akadaşlar</h4>
            </div>
            <div class="modal-body" id="modal-arkadaslar">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div id="deneme">

</div>

<script  type="text/javascript">
    var status=-1;
    function acceptedChallenge() {
        status=0;
        $('#acceptedchallenge').addClass("active");
        $('#mychallenge').removeClass("active");
        $.ajax({
            url:"http://localhost:8080/Acceptedresponse.jsp",
            data:{userid:<%=users.getId()%>,status:status},
            success:function (response) {
                $(".content").html(response);
            }
        });

    }
    function myChallenge() {
        status=1;
        $('#acceptedchallenge').removeClass("active");
        $('#mychallenge').addClass("active");
        $.ajax({
            url:"http://localhost:8080/Acceptedresponse.jsp",
            data:{userid:<%=users.getId()%>,status:status},
            success:function (response) {
                $(".content").html(response);
            }
        });
    }
    $(".info").hover(function () {
        $(this).css("cursor","pointer");
    },function () {
    });
    $(".glyphicon").hover(function () {
        $(this).css("cursor","pointer");
    },function () {
    });
    // Arkadaşlar modalını doldurur
    $('#friends').click(function () {
        $.ajax({
            url:"http://localhost:8080/friendsrsp.jsp",
            data:{userid:<%=users.getId()%>},
            success: function (cevap) {
                $('#modal-arkadaslar').html(cevap);
            }
        });
    });

    $('.modal-like').click(function () {
        var id=$(this).attr('id').toString();
        var index= id.indexOf("-");
        var post_id = id.slice(index+1);
        $.ajax({
            url:"http://localhost:8080/likepeopleresponse.jsp",
            data:{postid:post_id},
            success: function (cevap) {
                // $("#"+like_idd).html(cevap);
                $('#modal-content').html(cevap);
            }
        });
    });

  function toggle() {
      $('.collapse').toggle();
  }

    $(".modal-like").hover(function () {
        $(this).css("cursor","pointer");
    },function () {
    });


    var wsUrl = "ws://localhost:8080/websocketserver";
    var webSocket;

    function init() {
        webSocket = new WebSocket(wsUrl);
        webSocket.onopen = function(evt) {
            onOpen(event)
        };
        webSocket.onclose = function(evt) {
            onClose(event)
        };
        webSocket.onmessage = function(evt) {
            onMessage(event)
        };
        webSocket.onerror = function(evt) {
            onError(event)
        };
    }

    function onOpen(event){
        Console.log("OnOpen Event");
    }

    function onClose(event) {
        Console.log("OnClose Event");
    }

    function onError(event) {
        Console.log("OnError Event");
    }

    function sendMessage() {
        webSocket.send(<%=users.getId()%>);
    }

    function onMessage(event) {
        //document.getElementById("sonuc").innerHTML=event.data;
        //var dene={"name":"furkan","asd":12}
        var message=event.data.toString();
        var obj=JSON.parse(jsonstring)
        alert(obj.fromUserFullName);
         /*var length=message.length;
         var jsonstring=message.slice(1,length-1);
        var obj=JSON.parse(jsonstring);*/
      /*  var length=message.length;
        var first=message.indexOf("a");
        var second=message.indexOf("b");
        var third=message.indexOf("c");

        var kimden=message.slice(0,first);
        var kime=message.slice(first+1,second);
        var action=message.slice(second+1,third);
        var type=message.slice(third+1,length);
        var sendingdata=kimden+"a"+kime+"b"+action+"c"+type;*/
       /* $.ajax({
            url:"http://localhost:8080/notificationrsp.jsp",
            data:{data:message},
            success: function (cevap) {
                $('#deneme').html(cevap);
            }
        });*/
        alert(event.data);
       // alert(kimden+kime+action+type);
    }

    window.addEventListener("load", init, false);

</script>

<button onclick="sendMessage();">Send Message</button
</body>
</html>
