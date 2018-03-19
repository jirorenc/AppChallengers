<%@ page import="com.appchallengers.appchallengers.dao.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.entity.Users" %>
<%@ page import="com.appchallengers.appchallengers.web.bussiness.BussinessLogin" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 18.2.2018
  Time: 19:14
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

    <title>Title</title>
</head>
<body>



<%!
    String id;
    String hash;
    UserDao userDao;
    String login_message="";
    int pageCheck;
    String getLogin_message=null;
%>

<%
    String id=request.getParameter("id");
    String hash=request.getParameter("hash");
    if((id == null || id == "") || (hash == null || hash == "")){
        response.sendRedirect("errorpage.jsp");
    }
%>

<%
    HttpSession session2 = request.getSession(false);

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

        System.out.println("----->COOKİE BOS DEĞİL" + userName);
        pageCheck = 1;
    }
    if (pageCheck == 0) {
        if (session2.getAttribute("username") == null) {
            pageCheck = 0;

        } else {
            System.out.println("----->SESSİON BOS DEĞİL");
            pageCheck = 1;
        }
    }

%>

<%
    // Burası  daha önce oturum açmamış yani mobilden girenler için
    // Mobilden giren kullanıcınn maili onaylanması için uygun id ve hash ve email passowrd u saglamalı
    if(request.getParameter("login-submit")!=null){
        String idd=request.getParameter("id");
        String hashh=request.getParameter("hash");
        if((idd == null || idd == "") || (hashh == null || hashh == "")){
            response.sendRedirect("errorpage.jsp");
        }else{
            try{

                // getting parameters from login page using servlet
                String user_name= request.getParameter("username");
                String password = request.getParameter("password");

                System.out.println(user_name);
                // Validation of user entering. İf it is not true this returns login page again
                // İf user accoun ino is true , it wii creat a session for user
                // Also if Account will not found Servlet will seend a 'login_message' to page
                Users users=userDao.findByEmail(user_name);
                if(user_name!=null || password!=null ){
                    BussinessLogin bl= new BussinessLogin();
                    if(bl.accountControl(user_name, password)){

                        if (userDao.checkIdAndPasswordSalt(Integer.parseInt(id), hash) == 0) {    // bu hash ve id de biri var mı?
                            response.sendRedirect("errorpage.jsp");
                        } else if (userDao.checkIdAndPasswordSalt(Integer.parseInt(id), hash) == 1) {
                            HttpSession session3 = request.getSession();
                            session.setAttribute("username", user_name);
                            Cookie cookie = new Cookie("user", user_name);       // creating coookise for sesion
                            cookie.setMaxAge(60 * 60 * 24);                      //  setting cookie life-time
                            response.addCookie(cookie);
                            userDao.confirmEmail(Integer.parseInt(id), hash);
                            response.sendRedirect("index.jsp");
                        }
                    }
                    else{
                        getLogin_message = "Paralo yada kullanıcı adı hatalı!<br> Lütfen kontrol ediniz...";
                        // request.getRequestDispatcher("Login.jsp").forward(request, response);
                        // response.sendRedirect("confirm.jsp?id="+users.getId()+"&hash="+users.getPasswordSalt());
                    }
                }else{
                    response.sendRedirect("confirm.jsp?id="+users.getId()+"&hash="+users.getPasswordSalt());
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

    }
%>

<%
    userDao = new UserDaoImpl();
    String id3=request.getParameter("id");
    String hash3=request.getParameter("hash");
        if(pageCheck == 1){                                                       // Daha önceden oturum açmıssa
            if ((id3 == null || id3 == "") || (hash3 == null || hash3 == "")) {   // Lİnk Dogrumu
                out.println("HATA");
                //System.out.println(id);
               // System.out.println(hash);
                System.out.println("---------->1......");
                response.sendRedirect("errorpage.jsp");
            } else {

                if (userDao.checkIdAndPasswordSalt(Integer.parseInt(id3), hash3) == 0) {    // bu hash ve id de biri var mı?
                    System.out.println("----------------->>>2...."+"GİRDİİİEEEEEEEEEE");
                    response.sendRedirect("errorpage.jsp");
                } else if (userDao.checkIdAndPasswordSalt(Integer.parseInt(id3), hash3) == 1) {
                    userDao.confirmEmail(Integer.parseInt(id), hash);
                    response.sendRedirect("index.jsp");
                }
        }
        // Daha önce oturuum acmadıysa yada mobilden geldiyse
    }else{%>


<div class="container">

    <div class="row">
        <!-- LEFT PART -->
        <div class="col-sm-4"></div>
        <!-- CONTENT PART-->
        <div class="col-sm-4">
            <!-- PANEL BEGINNIG -->
            <br><br>
            <div class="panel panel-default" style="height:80%" >

                <div class="panel-body">

                    <div class="text-center text-muted" >
                        <h2>AppChallenger</h2>
                    </div>
                    <!-- FORM BEGINNIG-->
                    <form  method="POST" data-toggle="validator" role="form">
                        <div class="form-group">
                            <input type="email" id="txt-username" class="form-control" required
                                   style="background-color:#fafafa" placeholder="Enter email" name="username">
                        </div>
                        <div class="form-group">
                            <input type="password" id="txt-password" class="form-control" required
                                   style="background-color:#fafafa" placeholder="Enter password" name="password">
                        </div>
                        <br>
                        <button type="submit" id="submitt" name="login-submit" class="btn btn-primary btn-block">Login</button>
                    </form>
                    <!-- FORM BEGINNIG-->
                    <br>
                    <div id="message" class="text-danger text-center">
                        <%if (getLogin_message != null) {%>
                        <%=getLogin_message%>
                        <%}%>
                    </div>

                    <div class="text-center"><a href="#">Şifremi unuttum?</a>  </div>

                    <!-- PANEL ENDING -->
                </div>
                <!--PANEL FOOTER -->
                <div class="panel-footer">
                    <div class="text-center text-muted">Hesabın Yok mu?<a href="signup.jsp">Kaydol</a></div>

                </div>
            </div>

            <!--RIGHT PART OF COLOUM -->
            <div class="col-sm-4" ></div>
        </div>
    </div>
</div>



<%}%>
<%
 getLogin_message=null;
%>


</body>
</html>
