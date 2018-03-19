<%@ page import="com.sun.org.apache.xpath.internal.SourceTree" %>
<%@ page import="com.appchallengers.appchallengers.model.Users" %>
<%@ page import="com.appchallengers.appchallengers.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.Jpa.UserDaoImpl" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 13.2.2018
  Time: 19:40
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

    <title>$Title$</title>
</head>
<body>


<%!

    Cookie indexCookie = null;
    int pageCheck;
    Users users;
    String deneme;
%>
<%
    /*
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
          for (javax.servlet.http.Cookie cookie : cookies) {
            if (cookie.getName().equals("user")==false) {
            // response.sendRedirect("Login.jsp");
            }
          }
        }else {
          response.sendRedirect("Login.jsp");
        }
      */

    HttpSession session1 = request.getSession(false);
    UserDao userDao= new UserDaoImpl();
    String userName = null;
    javax.servlet.http.Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (javax.servlet.http.Cookie cookie : cookies) {
            if (cookie.getName().equals("user"))
                userName = cookie.getValue();


        }
    }
    if (userName == null) {
        pageCheck = 0;
    } else {
        users=userDao.findByEmail(userName);
        System.out.println("----->COOKİE BOS DEĞİL" + userName);
        pageCheck = 1;
    }
    if (pageCheck == 0) {
        if (session1.getAttribute("username") == null) {
            pageCheck = 0;
            System.out.println("----->SESSİON BOS DEĞİL");
        } else {
            users=userDao.findByEmail("username");
            pageCheck = 1;
        }
    }
    if (pageCheck != 1) {
        response.sendRedirect("Login.jsp");
    }
%>
<div class="page-header">
    <!--Hedaer -->
    <div class="container">
        <!--NAVBAR BEGINNING -->
        <div class="container">
        <nav class="navbar navbar-inverse navbar-fixed-top">
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


<%for(int i=233; i<243; i++){%>
<div class="container">
    <div class="row">
        <!-- LEFT PART -->
        <div class="col-sm-2"></div>
        <!-- CONTENT PART-->
        <div class="col-sm-8">
            <!-- PANEL BEGINNIG -->
            <div class="panel panel-default" id="p<%=i%>"  >
                <div class="panel-heading" style="background-color: #fefefe">Panel Heading</div>
                <div class="panel-body">
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                </div>
                <div class="panel-footer" style="background-color: #fefefe">

                    <ul class="list-inline">
                        <li>
                            <button type="button" class="btn btn-default btn-sm" id="btnlike-<%=i%>"><span
                                    class="glyphicon glyphicon-thumbs-up" ></span>Like
                            </button>
                        </li>
                        <li><div id="likenumber-<%=i%>" data-toggle="modal" data-target="#begenenler" ><%=i%></div></li>
                        <li>
                            <button type="button" class="btn btn-default btn-sm" id="btndislike-<%=i%>"><span
                                    class="glyphicon glyphicon-thumbs-down"></span>Dislike
                            </button>
                        </li>
                        <li><div id="dislikenumber-<%=i%>"><%=i-10%></div></li>

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

<%}%>

<div class="container">
    <div class="row">
        <!-- LEFT PART -->
        <div class="col-sm-2"></div>
        <!-- CONTENT PART-->
        <div class="col-sm-8">
            <!-- PANEL BEGINNIG -->
            <div class="panel panel-default" >
                <div class="panel-heading" style="background-color: #fefefe">Panel Heading</div>
                <div class="panel-body">
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                </div>
                <div class="panel-footer" style="background-color: #fefefe">
                    <ul class="list-inline">
                        <li>
                            <button type="button" class="btn btn-default btn-sm" ><span
                                    class="glyphicon glyphicon-thumbs-up"></span>Like
                            </button>
                        </li>
                        <li><span id="like-counter2">0</span></li>
                        <li>
                            <button type="button" class="btn btn-default btn-sm" ><span
                                    class="glyphicon glyphicon-thumbs-down"></span>Dislike
                            </button>
                        </li>
                        <li><span id="dislike-counter2">0</span></li>

                    </ul>
                </div>
                <!-- PANEL ENDING -->
            </div>
        </div>
        <!--RIGHT PART -->
        <div class="col-sm-2"></div>
    </div>
</div>


<div class="container">
    <div class="row">
        <!-- LEFT PART -->
        <div class="col-sm-2"></div>
        <!-- CONTENT PART-->
        <div class="col-sm-8">
            <!-- PANEL BEGINNIG -->
            <div class="panel panel-default">
                <div class="panel-heading" style="background-color: #fefefe">Panel Heading</div>
                <div class="panel-body">
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                </div>
                <div class="panel-footer" style="background-color: #fefefe">
                    <ul class="list-inline">
                        <li>
                            <button type="button" class="btn btn-link"><span
                                    class="glyphicon glyphicon-thumbs-up"></span></button>
                        </li>
                        <li><span id="like-counter3">0</span></li>
                        <li>
                            <button type="button" class="btn btn-link"><span
                                    class="glyphicon glyphicon-thumbs-up"></span></button>
                        </li>
                        <li><span id="dislike-counter3">0</span></li>

                    </ul>
                </div>
                <!-- PANEL ENDING -->
            </div>
        </div>
        <!--RIGHT PART -->
        <div class="col-sm-2"></div>
    </div>
</div>


<div class="container">
    <div class="row">
        <!-- LEFT PART -->
        <div class="col-sm-2"></div>
        <!-- CONTENT PART-->
        <div class="col-sm-8">
            <!-- PANEL BEGINNIG -->
            <div class="panel panel-default">
                <div class="panel-heading" style="background-color: #fefefe">Panel Heading</div>
                <div class="panel-body">
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                    <h1>Post Content</h1>
                </div>
                <div class="panel-footer" style="background-color: #fefefe">
                    <ul class="list-inline">
                        <li>
                            <button type="button" class="btn btn-link" id="furkan"><span
                                    class="glyphicon glyphicon-thumbs-up"></span></button>
                        </li>
                        <li><span id="like-counter4">0</span></li>
                        <li>
                            <button type="button" class="btn btn-link"><span
                                    class="glyphicon glyphicon-thumbs-up"></span></button>
                        </li>
                        <li><span id="dislike-counter4">0</span></li>

                    </ul>
                </div>
                <!-- PANEL ENDING -->
            </div>
        </div>
        <!--RIGHT PART -->
        <div class="col-sm-2"></div>
    </div>
</div>


<!-- BEGENELER MODAL-->
<div class="modal fade" id="begenenler" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Begenenler</h4>
            </div>
            <div class="modal-body">
                <p>Furkan ÖNEL</p>
                <p>Mahmut Nasifoğlu</p>
                <p>Akif Bilici</p>
                <p>Mehmet Aras</p>
                <p>Furkan ÖNEL</p>
                <p>Mahmut Nasifoğlu</p>
                <p>Akif Bilici</p>
                <p>Mehmet Aras</p>
                <p>Furkan ÖNEL</p>
                <p>Mahmut Nasifoğlu</p>
                <p>Akif Bilici</p>
                <p>Mehmet Aras</p>
                <p>Furkan ÖNEL</p>
                <p>Mahmut Nasifoğlu</p>
                <p>Akif Bilici</p>
                <p>Mehmet Aras</p>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<!-- BEGENELER MODAL-->


<div class="page-fooer">
    <h3>Footer</h3>
</div>

<script  type="text/javascript">

    $('.btn').click(function () {
       var id=$(this).attr('id').toString();            // Tıklanılan butonun id sin, al

        var index= id.indexOf("-");                     // btnlike- kısmını at sadece id yi al  "clear_id"
        var id_firstpart = id.slice(0,index);           // - den öncesini al ve kontrol et like mı dislike butonumu
        var id_lenght=id_firstpart.length;              // uzunluğu 7 ise btnlike eger 10 ise btndislike butonuna basılmıs
        var clear_id = id.slice(index+1);               // Tıklanınlan postun saf id sine ulasatık buton id üzarinden
        if(id_lenght==7){                               // like butonuna basılma durumu

            var like_idd="likenumber-"+clear_id;         // begenelerin sayısısnı yazan div in id si
            var like_sayısı=$("#"+like_idd).html().trim().toString();
            var status=1;
            var color="black";
            //alert("Like butonuna basıldı"+clear_id+like_sayısı);
            //  Buradaki ajax metodu beğenilen postun id sini ve  begennen kullanıcının id sini
            // parametre olarak gönderiyor. Eger bu kisi daha önce begenmisse begeni geri alınacak
            // ilk defa begenmisse begeni bir arttırılıp veri tabanına kaydedilecek
            $.ajax({
               url:"http://localhost:8080/likeresponse.jsp",
                data:{postid:clear_id,userid:<%=users.getId().toString()%>,like:like_sayısı.toString(),status:status},
                success: function (cevap) {
                  // $("#"+like_idd).html(cevap);
                    $("#"+like_idd).html($(cevap).filter("div").html());
                  //  color= $(cevap).filter("div").css("color").toString();

                }
            });

        }else if(id_lenght==10){                         // dislike butonuna basılma durumu
            //alert("Dislike butonuna basıldı"+clear_id);

            var dislike_idd="dislikenumber-"+clear_id;         // begenelerin sayısısnı yazan div in id si
            var dislike_sayısı=$("#"+dislike_idd).html().trim().toString();
            var status=0;
            //alert("Like butonuna basıldı"+clear_id+like_sayısı);
            //  Buradaki ajax metodu beğenilen postun id sini ve  begennen kullanıcının id sini
            // parametre olarak gönderiyor. Eger bu kisi daha önce begenmisse begeni geri alınacak
            // ilk defa begenmisse begeni bir arttırılıp veri tabanına kaydedilecek
            $.ajax({
                url:"http://localhost:8080/likeresponse.jsp",
                data:{postid:clear_id,userid:<%=users.getId().toString()%>,like:dislike_sayısı.toString(),status:status},
                success: function (cevap) {
                    // $("#"+like_idd).html(cevap);
                    $("#"+dislike_idd).html($(cevap).filter("div").html());

                }
            });

        }

    });
</script>

</body>
</html>
