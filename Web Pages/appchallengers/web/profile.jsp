<%@ page import="com.appchallengers.appchallengers.dao.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.entity.Users" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.RelationshipDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.RelationshipDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.GetUserInfoDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.GetUserInfoDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.response.ChallengeResponse" %>
<%@ page import="java.util.List" %>
<%@ page import="com.appchallengers.appchallengers.model.response.GetUserInfoResponseModel" %>
<%--
  Created by IntelliJ IDEA.
  User: MHMTNASIF
  Date: 18.04.2018
  Time: 16:20
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

    </style>
</head>
<body>
<%!
    int visitedid;
    int visitorid;
    long relationship_status=-1;
    String buton_value="İstek Gönder";
    int status=-2;
    int buton_status=-3;
    long action_id;
    RelationshipDao relationshipDao;
    int firsuser;
    int seconduser;
%>
<%
    visitedid= Integer.parseInt(request.getParameter("ref"));
    UserDao userDao= new UserDaoImpl();
    Users users= userDao.findUserById(visitedid);
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
    GetUserInfoDao getUserInfoDao= new GetUserInfoDaoImpl();
    relationshipDao=new RelationshipDaoImpl();
    List<ChallengeResponse> userChallengeFeedList = getUserInfoDao.getUserChallenges(visitorid, visitedid);
    GetUserInfoResponseModel  getUserInfoResponseModel=getUserInfoDao.getUserInfo(visitorid,visitedid);
    status=getUserInfoResponseModel.getStatus();
    action_id=getUserInfoResponseModel.getUseractionid();
    System.out.println(visitedid);
    System.out.println(visitorid);
    if(visitorid>visitedid){
        firsuser=visitedid;
        seconduser=visitorid;
    }else{
        firsuser=visitorid;
        seconduser=visitedid;
    }

    if(action_id==visitorid){
        if(status==1){
            buton_status=0;
            buton_value="Arkadaşlıktan Çıkar";
        }else if(status==-1){
            buton_status=1;
            buton_value="Arkadaş Ekle";
        }else if(status==0){
            buton_status=2;
            buton_value="İsteği İptal Et";
        }
    }else {
        if(status==1){
            buton_status=0;
            buton_value="Arkadaşlıktan Çıkar";
        }else if(status==-1){
            buton_status=1;
            buton_value="Arkadaş Ekle";
        }else if(status==0){
            buton_status=3;
            buton_value="İsteği Kabul Et";
        }
    }


%>


<nav class="navbar navbar-inverse navbar-fixed-top" style="margin-bottom: 50px">
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
        </div>
        <div class="col-sm-2"></div>
    </div>
</nav>
<br><br><br><br><br>
<div id="sonuc"></div>
<div class="container" >
    <div class="row">
        <div class="col-sm-2">

        </div>
        <div class="col-sm-8">
            <div class="text-center">
                <ul class="list-inline">
                    <li>
                        <p><img src="<%=getUserInfoResponseModel.getProfilepicture()%>" alt="profil.jpg" class="daireselprofil"></p>
                    </li>
                    <li>
                        <ul style="list-style-type:none" >
                            <li>
                                <p><h3><%=getUserInfoResponseModel.getFullname()%></h3></p>
                            </li>
                            <li>
                                <ul class="list-inline">
                                    <li class="info"><strong><%=getUserInfoResponseModel.getChallenges() %></strong>  Challenges</li>
                                    <li class="info modal-like"  id="friends" data-toggle="modal" data-target="#arkadaslar" ><strong><%=getUserInfoResponseModel.getFriends() %></strong>  Arkadaşlar</li>
                                    <li class="info"><strong><%=getUserInfoResponseModel.getAccepted_challenges() %></strong>  Accepted Challenges</li>
                                    <button type="button" id="btn-firendship" class="btn btn-default btn-sm"><%=buton_value%></button>
                                    </li>
                                </ul>
                            </li>
                        </ul>

                    </li>
                </ul>
            </div>
            <hr>
            <ul class="nav nav-tabs  nav-justified">
                <li id="mychallenge" class="active"><a href="javascript:myChallenge()"><strong>MyChallenge</strong></a></li>
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


<script  type="text/javascript">
    var status=-1;
    function acceptedChallenge() {
       // alert("calıstı");
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
        //alert("calıstıtıtıtıtıt");
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
    $(".modal-like").hover(function () {
        $(this).css("cursor","pointer");
    },function () {
    });

    // Arkadaşlar modalını doldurur
    $('#friends').click(function () {
        $.ajax({
            url:"http://localhost:8080/friendsrsp.jsp",
            data:{userid:<%=visitedid%>},
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

    var btn_status= <%=buton_status%>

    $('#btn-firendship').click(function () {
            //alert(btn_status);

            if(btn_status==0){
                var sta=0;
                $.ajax({
                    url:"http://localhost:8080/relation.jsp",
                    data:{visitedid:<%=firsuser%>,visitorid:<%=seconduser%>,status:sta},
                    success: function (cevap) {
                        $('#sonuc').html(cevap);
                    }
                });
                btn_status=1;
                $('#btn-firendship').html("Arkadaş Ekle");

            }else if(btn_status==1){
                var sta=1;
                $.ajax({
                    url:"http://localhost:8080/relation.jsp",
                    data:{visitedid:<%=firsuser%>,visitorid:<%=seconduser%>,status:sta},
                    success: function (cevap) {
                        $('#sonuc').html(cevap);
                    }
                });
                btn_status=2;
                $('#btn-firendship').html("İsteği İptal Et");

            }else if(btn_status==2){
                var sta=0;
                $.ajax({
                    url:"http://localhost:8080/relation.jsp",
                    data:{visitedid:<%=firsuser%>,visitorid:<%=seconduser%>,status:sta},
                    success: function (cevap) {
                        $('#sonuc').html(cevap);
                    }
                });
                btn_status=1;
                $('#btn-firendship').html("Arkadaş Ekle");


            }else if(btn_status==3){
                var sta=2;
                $.ajax({
                    url:"http://localhost:8080/relation.jsp",
                    data:{visitedid:<%=firsuser%>,visitorid:<%=seconduser%>,status:sta},
                    success: function (cevap) {
                        $('#sonuc').html(cevap);
                    }
                });
                btn_status=0;
                $('#btn-firendship').html("Arkadaşlıktan Çıkar");
            }
        });

        function toggle() {
            $('.collapse').toggle();
        }


        // Sonradan eklenen


        $('.btn').click(function () {
            var id=$(this).attr('id').toString();            // Tıklanılan butonun id sin, al

            var index= id.indexOf("-");                     // btnlike- kısmını at sadece id yi al  "clear_id"
            var id_firstpart = id.slice(0,index);           // - den öncesini al ve kontrol et like mı dislike butonumu
            var id_lenght=id_firstpart.length;              // uzunluğu 7 ise btnlike eger 10 ise btndislike butonuna basılmıs
            var clear_id = id.slice(index+1);               // Tıklanınlan postun saf id sine ulasatık buton id üzarinden
            var  vote=$('#vote-'+clear_id).html();
            if(vote==0){
                $('#vote-'+clear_id).html(1);
                $('#btnlike-'+clear_id).css("background-color","yellow");
            }else if(vote ==1){
                $('#vote-'+clear_id).html(0);
                $('#btnlike-'+clear_id).css("background-color","");
            }
            //alert(vote);
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
                    data:{postid:clear_id,userid:<%=users.getId()+""%>,like:like_sayısı.toString(),status:vote},
                success: function (cevap) {
                    // $("#"+like_idd).html(cevap);
                    $("#"+like_idd).html($(cevap).filter("div").html());
                    //  color= $(cevap).filter("div").css("color").toString();

                }
            });
        }
    });






</script>


</body>
</html>
