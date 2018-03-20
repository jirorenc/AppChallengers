<%@ page import="org.json.Cookie" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.UserDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %><%--
=======
<%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 18.2.2018
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%!
    UserDao userDao;
%>

<%
    String userName=null;
    userDao= new UserDaoImpl();

    HttpSession session1= request.getSession(false);
    if(session1.getAttribute("username")==null){                        // oturum session objesiyle acılmadysa cookie bak
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals("user"))
                    userName = cookie.getValue();
            }
        }
        if (userName == null){                                             // cookie nullsa login e gonder
            System.out.println(userName );
            response.sendRedirect("Login.jsp");
        }else{                                                              // cookie null degilse mail confirm kotrlü yap
            int status=userDao.findByEmail(userName).getActive().ordinal();
            if(status==0){
                response.sendRedirect("confirmmail.jsp");
            }else if(status==1){                                            // Daha önce mail confirm yapmıssa indexe gönder
                System.out.println("-------->>>1.cookie ile girdi");
                response.sendRedirect("index.jsp");
            }
        }
    }else{                                                                // eger session objesi bos degilse  oturm acıksa
        String userName2=session1.getAttribute("username").toString();
        int status=userDao.findByEmail(userName2).getActive().ordinal();
        if(status==0){
            response.sendRedirect("confirmmail.jsp");
        }else if(status==1){
            System.out.println("-------->>>1.session ile girdi");
            response.sendRedirect("index.jsp");
        }
    }
%>
</body>
</html>
