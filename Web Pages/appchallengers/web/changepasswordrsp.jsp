<%@ page import="com.appchallengers.appchallengers.web.bussiness.BusinessMainpage" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 16.4.2018
  Time: 15:55
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

    <style>
        .td {
            border:5px white;
        }
    </style>
    <title>Title</title>
</head>
<body>

 <%!
     boolean status;
 %>


               <div class="row">
                   <div class="col-sm-4"></div>
                   <div class="col-sm-4">
                       <form id="confirmform" method="POST" data-toggle="validator" role="form">
                           <table class="table ">
                               <tr>
                                   <td>
                                       <label style="font-size: small;" for="ex-password">Eski Şifre</label>
                                   </td>
                                   <td>
                                       <input type="password" id="ex-password" class="form-control input-sm" required
                                              style="background-color:#fafafa;" placeholder="Current Password" name="expassword">
                                       </td>
                               </tr>
                               <tr >
                                   <td>
                                       <label style="font-size: small;" for="txt-password">Yeni Şifre</label>
                                   </td>
                                   <td>
                                       <input type="password" id="txt-password" class="form-control input-sm" required
                                              style="background-color:#fafafa;" placeholder="Enter password" name="password">
                                   </td>
                               </tr>
                               <br>
                               <tr >
                                   <td>
                                       <label style="font-size: small;" for="txt-passwordConfirm">Onaylama </label>
                                   </td>
                                   <td>
                                       <input type="password" id="txt-passwordConfirm" class="form-control input-sm" required
                                              style="background-color:#fafafa;" placeholder="Confirm password"
                                              name="passwordConfirm">
                                   </td>
                               </tr>
                               <br>
                               <tr>
                                   <td>
                                   </td>
                                   <td>
                                       <button type="submit" id="changePassword" name="changepassword"
                                               class="btn btn-primary btn-block" disabled="true">Change Password
                                       </button>
                                   </td>
                               </tr>
                           </table>
                       </form>
                   </div>
                   <div class="col-sm-4"></div>
               </div>

               <script  type="text/javascript">

                   $('#txt-passwordConfirm').keyup(function () {
                       var ps = $('#txt-password').val();
                       var pscf = $('#txt-passwordConfirm').val();
                       if (ps == pscf && (pscf != "" && pscf != null)) {
                           $('#txt-password').attr("readonly", true);
                           $('#txt-passwordConfirm').attr("readonly", true);
                           $('#changePassword').removeAttr("disabled");
                       }
                   });

               </script>
</body>
</html>
