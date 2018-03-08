<%@ page import="com.appchallengers.appchallengers.web.bussiness.BussinessLogin" %>
<%@ page import="com.appchallengers.appchallengers.web.bussiness.BusinessMainpage" %><%--
  Created by IntelliJ IDEA.
  User: rabbım
  Date: 5.3.2018
  Time: 00:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body >

<%!
  long like_status;
 // int like_number;
  String likeColor;
  String like_size;
%>
<%

 int postid=Integer.parseInt(request.getParameter("postid"));
 int userid=Integer.parseInt(request.getParameter("userid"));
 int like_number=Integer.parseInt(request.getParameter("like"));
 int status=Integer.parseInt(request.getParameter("status"));
    BusinessMainpage businessMainpage=new BusinessMainpage();

    if(status==1){
        like_status=businessMainpage.save_like(postid,userid,like_number);
    }else if(status==0){
        like_status=businessMainpage.save_dislike(postid,userid,like_number);
    }


    if(like_status==0 && like_number>=0){
        like_number=like_number-1;
        like_size=String.valueOf(like_number);
        likeColor="black";
    }else if(like_status==1  ){
        like_number=like_number+1;
        like_size=String.valueOf(like_number);
        likeColor="green";
        System.out.println(like_size+"----->");

    }
    System.out.println("post id -->>"+postid+" user id -->>"+userid+"begeni--->"+like_number+" AJAXAJAXAJAXJXJAXAJXAJXJAJXAXAJXAJXJAX");
    System.out.println("likestatus -->>"+like_status+" likenumber -->>"+like_number);
%>

 <div id="response" style="color:<%=likeColor%>;"><%=like_size%></div>
<%
    like_status=-1;
    like_number=-1;
    likeColor=null;
    like_size=null;
%>

</body>
</html>
