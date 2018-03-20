<%@ page import="com.appchallengers.appchallengers.dao.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.web.bussiness.BussinessLogin" %>
<%--

<%--
>>>>>>> 91c64ae2046da8213343f7b3f8f4167d92670b3e
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 17.2.2018
  Time: 23:44
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
    <script src="Script/jquerylib.js" type="text/javascript"></script>

    <title></title>


</head>
<body style="background-color:#fdfbfb ">


<%!
    UserDao userDao;
    boolean check_login;
    String getLogin_message;

%>

<%
    userDao = new UserDaoImpl();
    if (request.getParameter("btnlogin") != null) {                    // İf click the login button for request?
        try {
            String id = request.getParameter("id");
            String hash = request.getParameter("hash");
            // getting parameters from login page using servlet
            // Validation of user entering. İf it is not true this returns login page again
            // İf user accoun ino is true , it wii creat a session for user
            // Also if Account will not found Servlet will seend a 'login_message' to page
            String user_name = request.getParameter("username");
            String password = request.getParameter("password");
            int kontrol_active = userDao.findByEmail(user_name).getActive().ordinal();
            if (user_name != null || password != null) {
                BussinessLogin bl = new BussinessLogin();
                if (bl.accountControl(user_name, password)) {
                    HttpSession session2 = request.getSession();         // Creating session
                    session.setAttribute("username", user_name);
                    session.setAttribute("active", kontrol_active);      // Mail is comfirmed?
                    Cookie cookie = new Cookie("user", user_name);       // creating coookise for sesion
                    cookie.setMaxAge(60 * 60 * 24);                      //  setting cookie life-time
                    response.addCookie(cookie);
                    if (kontrol_active == 1) {                          // if mail is confirmed  by the user go to index
                        response.sendRedirect("index.jsp");
                    } else {
                        response.sendRedirect("confirmmail.jsp");       // if mail is not confirmed by the user go to Login page
                    }
                } else {                                                 // Email or password is not correct
                    getLogin_message = "Paralo yada kullanıcı adı hatalı!<br> Lütfen kontrol ediniz...";
                    //response.sendRedirect("Login.jsp");
                }
            } else {                                                    // email or password is null ?
                response.sendRedirect("Login.jsp");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
%>

<div class="container">

    <div class="row">
        <!-- LEFT PART -->
        <div class="col-sm-2"></div>
        <div class="col-sm-2" id="mobile-fisrtpart"></div>
        <div class="col-sm-4" id="carousel">
            <br><br>
            <!-- PANEL BEGINNIG -->
            <!-- PANEL BODY BEGINNING-->

            <div id="myCarousel" class="carousel slide float-sm-right" style="min-height:min-content%;" role="listbox"
                 data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner">

                    <div class="item active">
                        <img src="/images/mlt.jpg" alt="Los Angeles" style="min-height:100%;">
                        <div class="carousel-caption">
                            <h3>AppChallengers</h3>
                            <p>Stot Watching! Let's Do It</p>
                        </div>
                    </div>

                    <div class="item">
                        <img src="/images/mlt.jpg" alt="Chicago" style="min-height:100%;">
                        <div class="carousel-caption">
                            <h3>AppChallengers</h3>
                            <p>Stot Watching! Let's Do It</p>
                        </div>
                    </div>

                    <div class="item">
                        <img src="/images/mlt.jpg" alt="New York" style="min-height:100%;">
                        <div class="carousel-caption">
                            <h3>AppChallengers</h3>
                            <p>Stot Watching! Let's Do It</p>
                        </div>
                    </div>


                </div>

                <!-- Left and right controls -->
                <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
            <!-- PANEL ENDING -->
            <br>

        </div>
        <div class="col-sm-4 ">                               <!-- CONTENT PART-->
            <br><br>
            <div class="panel panel-default" style="min-height:min-content">    <!-- PANEL BEGINNIG -->

                <div class="panel-body">                      <!-- PANEL BODY BEGINNING-->
                    <div class="text-center text-muted">
                        <h2>AppChallenger</h2> <br>
                    </div>
                    <!-- FORM BEGINNIG-->
                    <form method="POST" data-toggle="validator" role="form">
                        <div class="form-group ">
                            <input type="email" id="txt-username" class="form-control input-sm" required
                                   style="background-color:#fafafa" placeholder="Enter email" name="username">
                        </div>
                        <div class="form-group">
                            <input type="password" id="txt-password" class="form-control input-sm" required
                                   style="background-color:#fafafa" placeholder="Enter password" name="password">
                        </div>
                        <br>
                        <button type="submit" id="submitt" class="btn btn-primary btn-block input-sm" name="btnlogin">
                            Login
                        </button>
                    </form>
                    <br>
                    <!-- FORM ENDİNG-->
                    <div id="message" class="text-danger text-center">
                        <%if (getLogin_message != null) {%>
                        <%=getLogin_message%>
                        <%}%>
                    </div>
                    <br>
                    <div class="text-center"><a href="forgotmypassword.jsp">Şifremi unuttum?</a></div>
                </div>                         <!-- PANEL ENDING -->
                <div class="panel-footer">     <!--PANEL FOOTER -->
                    <div class="text-center text-muted">Hesabın Yok mu? <a href="signup.jsp">Kaydol</a></div>
                </div>
            </div>
            <div class="panel panel-default ">
                <div class="panel-body">
                    <div class="text-center"><a href="https://play.google.com/store?hl=tr">
                        <img src="/images/google-play-badge.png" alt="GooglePlay" style="width:50%">
                    </a></div>
                </div>
            </div>
            <div class="col-sm-2"></div>      <!--RIGHT PART OF COLOUM -->
        </div>
    </div>
</div>
<!-- FOOTER BEGINNING-->
<hr>
<div class="container">
    <div class="col-sm-2"></div>
    <div class="col-sm-8">
        <ul class="list-inline input-sm">
            <li><a href="#"><strong>HAKIMIZDA</strong></a></li>
            <li><a href="#"><strong>DESTEK</strong></a></li>
            <li><a href="#"><strong>GİZLİLİK</strong></a></li>
            <li><a href="#"><strong>KOŞULLAR</strong></a></li>
            <ul class="list-inline pull-right">
                <li>
                    <div>
                        <div class="text-muted"><span class="glyphicon glyphicon-copyright-mark"></span>2018
                            AppChallengers
                        </div>
                    </div>
                </li>
            </ul>
        </ul>

    </div>
    <div class="col-sm-2"></div>
    <div class="col-sm-2" id="mobile-secondpart"></div>
</div>
<!-- FOOTER ENDING-->
<%
    getLogin_message = null;
%>
<script type="text/javascript">

    // setting the templete for mobile devices
    $(document).ready(function () {
        var width = window.innerWidth;
        var height = window.innerHeight;
        $('#mobile-fisrtpart').hide();
        $('#mobile-secondpart').hide()
        if (width < 768) {
            $('#carousel').hide();
            $('#mobile-fisrtpart').show();
            $('#mobile-secondpart').show()

        }

    });
</script>

</body>
</html>
