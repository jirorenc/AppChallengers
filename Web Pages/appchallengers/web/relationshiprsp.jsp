<%@ page import="com.appchallengers.appchallengers.dao.dao.RelationshipDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.RelationshipDaoImpl" %><%--
  Created by IntelliJ IDEA.
  User: MHMTNASIF
  Date: 18.04.2018
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%!
  int status;
  int visitorid;
  int visitedid;
  RelationshipDao relationshipDao;
%>
<%
   /* visitedid=Integer.parseInt(request.getParameter("visitedid"));
    visitorid=Integer.parseInt(request.getParameter("visitorid"));
    status=Integer.parseInt(request.getParameter("status"));
  relationshipDao= new RelationshipDaoImpl();
  */
  /*if(status==0){
      relationshipDao.deleteRelationship(visitorid,visitedid);
  }else if(status==1){
      relationshipDao.addRelationship(visitorid,visitorid,visitorid);
  }else if(status==2){
      relationshipDao.acceptRelationship(visitorid,visitedid,visitorid);
  }?/
%>
aaaa
</body>
</html>
