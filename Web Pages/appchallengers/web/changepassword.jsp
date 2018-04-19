<%@ page import="com.appchallengers.appchallengers.model.entity.Users" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.web.bussiness.BusinessMainpage" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 16.4.2018
  Time: 00:30
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
            width: 70px;
            height: 70px;
            border-radius: 150px;
            -webkit-border-radius: 150px;
            -moz-border-radius: 150px;
            background: url(http://resim-dosyasinin.adresi/resim.jpg) no-repeat;
            margin-right: 10px;
            margin-bottom: 20px;
        }

        .vertical-center {
            min-height: 100%;  /* Fallback for browsers do NOT support vh unit */
            min-height: 100vh; /* These two lines are counted as one :-)       */

            display: flex;
            align-items: center;
        }
        .mycontent-left {
            border-right: 1px solid #a6a6a6;
        }

    </style>

    <title>Title</title>
</head>
<body>

<%!
  Users users;
    BusinessMainpage bsm;
    int check=-1;
    int check2=-1;
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
    users= userDao.findByEmail(userName);

    bsm= new BusinessMainpage();
    if(request.getParameter("changepassword")!=null){
        System.out.println("---->>>AAAAAAAAAAAAAAAAAAAAAAAAAA");
        check=bsm.checkpassword((String)request.getParameter("expassword"));
        System.out.println("---->>>>>"+check);
        if(check==1){
            check2=1;
        }else if(check==0){
            check2=0;
        }
    }
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

 <div class="container">
     <div class="row">
         <div class="col-sm-2"></div>
         <div class="col-sm-8">
             <%if(check2==0){%>
             <div class="alert alert-danger alert-dismissible">
                 <div class="text-center">
                 <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                 <strong>UYARI !</strong>  Eski Şifreniz Hatalı.
                 </div>
             </div>
             <%}%>
             <div class="panel panel-default">
                 <div class="panel-header">
                     <div class="text-center">
                         <ul class="list-inline" style="margin-top: 30px">
                             <li><img src="<%=users.getProfilePicture()%>" alt="profil.jpg" class="daireselprofil" ></li>
                             <li><h4><%=users.getFullName()%></h4></li>
                         </ul>
                     </div>
                 </div>
                        <ul class="nav nav-tabs nav-justified">
                             <li id="sifredegistir" class="active"><a href="javascript:sifreDegistir();"><strong>Şifre Değiştir</strong></a></li>
                             <li id="profilduzenle"><a href="javascript:profiliDüzenle();"><strong>Profili Düzenle</strong> </a></li>
                             <li id="izinler"><a href="javascript:izinler();"><strong>İzinler</strong> </a></li>

                         </ul>

                  <div class="panel-body">

                         </div>
                     </div>
                 </div>
             </div>
         </div>
         <div class="col-sm-2"></div>
     </div>
 </div>

<script  type="text/javascript">

    var status=-1;
    function sifreDegistir() {
        status=0;
        $('#sifredegistir').addClass("active");
        $('#profilduzenle').removeClass("active");
        $('#izinler').removeClass("active");
       $.ajax({
            url:"http://localhost:8080/changepasswordrsp.jsp",
            success:function (response) {
                $(".panel-body").html(response);
            }
        });

    }
    function profiliDüzenle() {
        status=1;
        $('#sifredegistir').removeClass("active");
        $('#profilduzenle').addClass("active");
        $('#izinler').removeClass("active");
        $('.alert').hide();
        $.ajax({
            url:"http://localhost:8080/editprofile.jsp",
            success:function (response) {
                $(".panel-body").html(response);
            }
        });
    }
    function izinler() {
        status=2;
        $('#sifredegistir').removeClass("active");
        $('#profilduzenle').removeClass("active");
        $('#izinler').addClass("active");
        $('.alert').hide();
        $.ajax({
            url:"http://localhost:8080/permissions.jsp",
            success:function (response) {
                $(".panel-body").html(response);
            }
        });
    }

   $(document).ready(function () {
        $.ajax({
            url:"http://localhost:8080/changepasswordrsp.jsp",
            success:function (response) {
                $(".panel-body").html(response);
            }
        });
    });
    $('.menu-item').click(function() {
        $('.in').collapse('hide');
    });

    function toggle() {
        $('.collapse').toggle();
    }



</script>



</body>
</html>
