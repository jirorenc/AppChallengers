<%@ page import="com.appchallengers.appchallengers.dao.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.entity.Users" %>
<%@ page import="com.appchallengers.appchallengers.util.EmailUtil" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 22.2.2018
  Time: 14:51
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

</head>
<body style="background-color:#fdfbfb ">

<%
    UserDao userDao= new UserDaoImpl();
    String mail_sonuc="";
  if(request.getParameter("sendPasswordLink")!=null){               // Was clicked send me password link ?
      System.out.println("----->>>ÇALISIYOR"+(String)request.getParameter("sendPasswordLink"));
      String email=(String)request.getParameter("sendPasswordLink");

      if(userDao.checkEmail(email)==1){                              // Mail is correct? İs there a mail in database?
          String subject="Confirm Email";
          Users user= userDao.findByEmail(email);
          String body="https://localhost:8080/creatingnewpassword.jsp?id="+user.getId()+"&hash="+user.getPasswordSalt();
          System.out.println("----->>>>>>>>>>>>>>>"+body);
          if(EmailUtil.sendEmail(user.getEmail(),subject,body)){     // Mail is send?
              mail_sonuc="Mailinize doğrulama postası gönderildi!<br>Lünfen kontrol ediniz...";
          }else{
              mail_sonuc="Beklenmedik bir hata oluştu! Lütfen daha sonra tekrar deneyiniz!";
          }
      }else{                                                        // İf There is no account of this eposta
          mail_sonuc="Bu eposta adresine ait hesap bulunamadı! ";
      }


  }
%>

<div class="container">

    <div class="row">
        <!-- LEFT PART -->
        <div class="col-sm-4"></div>
        <!-- CONTENT PART-->
        <div class="col-sm-4">
            <!-- PANEL BEGINNIG -->
            <br><br>
            <div class="panel panel-default" style="height:80%">
                <div class="panel-body">
                    <div class="text-center text-muted">
                        <h2>AppChallenger</h2>
                    </div>
                    <br>
                    <!-- FORM BEGINNIG-->
                    <form  method="POST" data-toggle="validator" role="form">

                        <div class="form-group">
                            <div class="text-center text-mute">Lütfen şifrenizi yenilemek için mail adresinizi giriniz...</div>
                        </div>

                        <div class="form-group">
                            <input type="email" id="txt-sendPasswordLink" class="form-control" required
                                   style="background-color:#fafafa" placeholder="Enter email" name="sendPasswordLink">
                        </div>

                        <br>
                        <button type="submit" id="btn_sendlink" class="btn btn-primary btn-block">
                            Gönder
                        </button>
                        <br>
                        <div class="text-center text-success"><%=mail_sonuc%></div>
                    </form>
                    <!-- FORM BEGINNIG-->
                </div>
                <!-- PANEL ENDING -->
            </div>
            <!--PANEL FOOTER -->
        </div>

        <!--RIGHT PART OF COLOUM -->
        <div class="col-sm-4"></div>
    </div>
</div>
</div>

<div class="container">
    <div class="col-sm-2"></div>
    <div class="col-sm-8">
        <ul class="list-inline">
            <li><a href="#"><strong>HAKIMIZDA</strong></a></li>
            <li><a href="#"><strong>DESTEK</strong></a></li>
            <li><a href="#"><strong>GİZLİLİK</strong></a></li>
            <li><a href="#"><strong>KOŞULLAR</strong></a></li>
            <li></li><ul class="list-inline pull-right">
            <li><div> <div class="text-muted"><span class="glyphicon glyphicon-copyright-mark"></span>2018 AppChallengers</div></div></li>
        </ul></li>
        </ul>
    </div>
    <div class="col-sm-2"></div>
</div>

</body>
</html>
