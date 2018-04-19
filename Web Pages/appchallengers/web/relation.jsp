<%@ page import="com.appchallengers.appchallengers.dao.dao.RelationshipDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.RelationshipDaoImpl" %><%--
  Created by IntelliJ IDEA.
  User: MHMTNASIF
  Date: 18.04.2018
  Time: 22:34
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
    String a;

%>
<%

    visitedid=Integer.parseInt(request.getParameter("visitedid"));
     visitorid=Integer.parseInt(request.getParameter("visitorid"));
     status=Integer.parseInt(request.getParameter("status"));
    System.out.println(status);
   relationshipDao= new RelationshipDaoImpl();

  if(status==0){
      relationshipDao.deleteRelationship(visitedid,visitorid);
      a="1";
  }else if(status==1){
      relationshipDao.addRelationship(visitedid,visitorid,visitorid);
      a="2";
  }else if(status==2){
      relationshipDao.acceptRelationship(visitedid,visitorid,visitedid);
      a="3";
  }
%>
</body>
</html>
