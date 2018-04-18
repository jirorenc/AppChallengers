<%@ page import="com.appchallengers.appchallengers.dao.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.entity.Users" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.ChallengesDetailDao" %>
<%@ page import="com.appchallengers.appchallengers.model.response.ChallengeResponse" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.ChallengesDetailDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.GetUserInfoDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.GetUserInfoDao" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 15.3.2018
  Time: 04:05
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

        .nav-tabs, .nav-pills {
            text-align:center;
        }
    </style>

</head>
<body>
<%!
    String user_id;
%>
<%

    String userName = null;
    javax.servlet.http.Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (javax.servlet.http.Cookie cookie : cookies) {
            if (cookie.getName().equals("user"))
                userName = cookie.getValue();


        }
    }

    user_id = (String) request.getParameter("ref");
    UserDao userDao = new UserDaoImpl();
    Users user = userDao.findUserById(Integer.parseInt(user_id));
    Users session_user=userDao.findByEmail(userName);
    System.out.println(user_id);
    UserDaoImpl userDao1= new UserDaoImpl();
    Users users=userDao1.findUserById(Integer.parseInt(user_id));
    GetUserInfoDao getUserInfoDao= new GetUserInfoDaoImpl();
    List<ChallengeResponse> userChallengeFeedList = getUserInfoDao.getUserChallenges(session_user.getId(), user.getId());

%>

<div class="page-header">
    <!--Hedaer -->
    <div class="container imput-sm">
        <!--NAVBAR BEGINNING -->
        <div class="container">
            <nav class="navbar navbar-inverse navbar-fixed-top" style="margin-bottom: 20px">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-8">
                            <div class="navbar-header navbar-left" >
                                <a class="navbar-brand" href="#">AppChallenger <span
                                        class="glyphicon glyphicon-eye-open"></span></a>
                            </div>

                            <!-- NAVBAR LIST-->
                            <ul class="nav navbar-nav navbar-right">
                                <li class="active"><!-- Give Link!--><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
                                <li><a href="#"><!-- Give Link!--><i class="glyphicon glyphicon-fire"></i></a></li>
                                <li><a href="#"><!-- Give Lİnk!--><i class="glyphicon glyphicon-user"></i></a></li>
                                    <!-- SEARCH PART -->
                                    <form class="navbar-form navbar-right" action="LogoutServlet">
                                        <div class="input-group">
                                            <div class="input-group-btn">
                                                <button class="btn btn-default input-sm" type="submit2">
                                                    <span class="glyphicon glyphicon-log-out"></span>
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </li>
                            </ul>
                            <li>
                                <!-- SEARCH PART -->
                                <form class="navbar-form navbar-right" action="LogoutServlet">
                                    <div class="input-group">
                                        <input type="text" class="form-control input-sm" placeholder="Search" name="search">
                                        <div class="input-group-btn">
                                            <button class="btn btn-default input-sm" type="submit">
                                                <i class="glyphicon glyphicon-search"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </li>
                            </ul>


                        </div>
                        <div class="col-sm-2"></div>
                    </div>
                </div>

                <!-- NAVBAR LIST-->


                <!-- SEARCH PART -->

            </nav>
        </div>
        <!--NAVBAR ENDİNG -->
    </div>
</div>


<div class="container">
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8" style="margin-top: 40px;margin-bottom: 40px" style="min-width: min-content">

            <div class="text-center">

                <ul class="list-inline">
                    <li>
                        <p><img src="<%=user.getProfilePicture()%>" alt="profil.jpg" class="daireselprofil"></p>
                    </li>
                    <li>
                        <ul style="list-style-type:none" >
                            <li>
                                <p><h3><%=user.getFullName()%></h3></p>
                            </li>
                            <li>
                                <ul class="list-inline">
                                    <li><strong>20 </strong>Gönderi</li>
                                    <li><strong>45 </strong>Takipçi</li>
                                    <li><strong>50 </strong>Takip</li>
                                    <button type="button" class="btn btn-default btn-sm">İstek Gönder</button>
                                    </li>
                                </ul>
                            </li>
                        </ul>

                    </li>
                </ul>
            </div>

            <hr>
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#">Challenge</a></li>
                    <li><a href="#">Accepted Challenge 2</a></li>
                    <li><a href="#">Friends 3</a></li>
                </ul>

        </div>
        <div class="col-sm-2"></div>
    </div>
</div>



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

<!-- BEGENELER MODAL-->
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
<!-- BEGENELER MODAL-->



<div class="page-fooer">

</div>

<script  type="text/javascript">

    $('.btn').click(function () {
        var id=$(this).attr('id').toString();            // Tıklanılan butonun id sin, al
        var index= id.indexOf("-");                     // btnlike- kısmını at sadece id yi al  "clear_id"
        var id_firstpart = id.slice(0,index);           // - den öncesini al ve kontrol et like mı dislike butonumu
        var id_lenght=id_firstpart.length;              // uzunluğu 7 ise btnlike eger 10 ise btndislike butonuna basılmıs
        var clear_id = id.slice(index+1);               // Tıklanınlan postun saf id sine ulasatık buton id üzarinden
        // var  vote=$('#vote-'+clear_id).html();
        var  vote=$('#vote-id').html(); //voteid-clear_id olacak
        if(vote==0){
            $('#vote-id').html(1);
            $('#btnlike-'+clear_id).css("background-color", "yellow");
        }else if(vote ==1){
            $('#vote-id').html(0);
            $('#btnlike-'+clear_id).css("background-color", "");
        }
        // alert(vote);
        if(id_lenght==7){                               // like butonuna basılma durumu
            var like_idd="likenumber-"+clear_id;         // begenelerin sayısısnı yazan div in id si
            var like_sayısı=$("#"+like_idd).html().trim().toString();
            var color="black";
            //alert("Like butonuna basıldı"+clear_id+like_sayısı);
            //  Buradaki ajax metodu beğenilen postun id sini ve  begennen kullanıcının id sini
            // parametre olarak gönderiyor. Eger bu kisi daha önce begenmisse begeni geri alınacak
            // ilk defa begenmisse begeni bir arttırılıp veri tabanına kaydedilecek
            $.ajax({
                url:"http://localhost:8080/likeresponse.jsp",
                data:{postid:clear_id,userid:<%=user.getId()+""%>,like:like_sayısı.toString(),status:vote},
                success: function (cevap) {
                    // $("#"+like_idd).html(cevap);
                    $("#"+like_idd).html($(cevap).filter("div").html());
                    //  color= $(cevap).filter("div").css("color").toString();
                }
            });
        }
    });

    $('.embed-responsive-item').click(function () {
        //alert("videoya tıklandı");
    });
    $('.fullname').click(function () {
        var id=$(this).attr('id').toString();
        var index= id.indexOf("-");
        var user_id = id.slice(index+1);
        //var spaceindex=<%=user.getFullName()%>.toString();
        //alert(spaceindex);
        window.location.href = "http://localhost:8080/profil.jsp";
        alert(userid);
    });

    // modal filling with ajax
    $('.modal-like').click(function () {
        var id=$(this).attr('id').toString();
        var index= id.indexOf("-");
        var post_id = id.slice(index+1);
        $.ajax({
            url:"http://localhost:8080/likepeopleresponse.jsp",
            data:{postid:post_id},
            success: function (cevap) {
                // $("#"+like_idd).html(cevap);
                $('.modal-body').html(cevap);
            }
        });
        // alert(post_id);
    });

</script>

</body>
</html>
