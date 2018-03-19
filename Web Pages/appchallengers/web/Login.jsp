<%--
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
<body>

<%!
    String login_message;
%>
<%
   login_message = String.valueOf(request.getAttribute("login_message"));

%>

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
                    <form  data-toggle="validator" role="form">
                        <div class="form-group">
                            <input type="email" id="txt-username" class="form-control" required
                                   style="background-color:#fafafa" placeholder="Enter email" name="username">
                        </div>
                        <div class="form-group">
                            <input type="password" id="txt-password" class="form-control" required
                                   style="background-color:#fafafa" placeholder="Enter password" name="password">
                        </div>
                        <br>
                        <button type="submit" id="submitt" class="btn btn-primary btn-block">Login</button>
                    </form>
                    <!-- FORM BEGINNIG-->
                    <br>
                    <div class="text-danger text-center">
                        <%if (login_message != null) {%>
                        <%=login_message%>
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

</body>
</html>
