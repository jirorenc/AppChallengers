<%@ page import="com.appchallengers.appchallengers.dao.ApplicationDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.Jpa.ApplicationDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.Country" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.AbstractList" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 19.2.2018
  Time: 01:10
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
</head>
<body>

<%
    ApplicationDao applicationDao= new ApplicationDaoImpl();
    List<Country> countries= applicationDao.getCountryList();

%>

<div class="container">

    <div class="row">
        <!-- LEFT PART -->
        <div class="col-sm-4"></div>
        <!-- CONTENT PART-->
        <div class="col-sm-4">
            <!-- PANEL BEGINNIG -->
            <br><br>
            <div class="panel panel-default">

                <div class="panel-body">

                    <div class="text-center text-muted" >
                        <h2>AppChallenger</h2>
                    </div>
                    <!-- FORM BEGINNIG-->
                    <form   action="SignupServlet" method="POST" data-toggle="validator" role="form">
                        <div class="form-group">
                            <input type="text" id="txt-fullname" class="form-control" required
                                   style="background-color:#fafafa" placeholder="Name and surname" name="fullname">
                        </div>
                        <div class="form-group">
                            <select class="form-control" id="select-country" name="country" size="0"  >
                                <!-- it will be fill using for each dynamicly -->
                                <option>Poland</option>
                                <option>Hungry</option>
                                <option>France</option>
                                <option>Turkey</option>
                                <option>Turkey</option>
                                <%for( Country i: countries){%>
                                <option><%=i.getCountryName()%></option>
                                <%}%>



                            </select>
                        </div>
                        <div class="form-group">
                            <input type="email" id="txt-email" class="form-control" required
                                   style="background-color:#fafafa" placeholder="Enter email" name="email">
                        </div>
                        <div class="form-group">
                            <input type="password" id="txt-password" class="form-control" required
                                   style="background-color:#fafafa" placeholder="Enter password" name="password">
                        </div>

                        <br>
                        <button type="submit" id="submit-singup" class="btn btn-primary btn-block">Sign Up</button>
                        <br>
                        <div class="text-muted text-center">
                            Kayıt olarak kullanıcı sözleşmesini <br> kabul etmiş oluyorsunuz!
                        </div>
                    </form>
                    <!-- FORM ENDİNG-->

                    <!-- PANEL BODY ENDING -->
                </div>
                <!--PANEL FOOTER -->
                <div class="panel-footer">
                    <div class="text-center text-muted">Hesabın Var mı?<a href="Login.jsp">Giriş Yap</a></div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="text-center"> <a href="https://play.google.com/store?hl=tr">
                        <img src="/images/google-play-badge.png" alt="GooglePlay" style="width:50%">

                    </a></div>
                </div>
            </div>
            <!--RIGHT PART OF COLOUM -->
            <div class="col-sm-4" ></div>
        </div>
    </div>
</div>

<hr>
<div class="container">
    <div class="col-sm-2"></div>
    <div class="col-sm-8">
        <ul class="list-inline">
            <li><a href="#"><strong>HAKIMIZDA</strong></a></li>
            <li><a href="#"><strong>DESTEK</strong></a></li>
            <li><a href="#"><strong>GİZLİLİK</strong></a></li>
            <li><a href="#"><strong>KOŞULLAR</strong></a></li>
            <li><a href="#"><strong>API</strong></a></li>
            <li></li><ul class="list-inline pull-right">
            <li><div> <div class="text-muted"><span class="glyphicon glyphicon-copyright-mark"></span>2018 AppChallengers</div></div></li>
        </ul></li>
        </ul>
    </div>
    <div class="col-sm-2"></div>
</div>


<script  type="text/javascript">



</script>

</body>
</html>
