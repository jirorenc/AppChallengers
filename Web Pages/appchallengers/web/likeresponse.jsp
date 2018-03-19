<%@ page import="com.appchallengers.appchallengers.dao.dao.ChallengesDetailDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.dao.VotesDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.ChallengesDetailDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.VotesDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.entity.ChallengeDetail" %>
<%@ page import="com.appchallengers.appchallengers.model.entity.Votes" %>
<%@ page import="com.appchallengers.appchallengers.web.bussiness.BusinessMainpage" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Locale" %>
<%--
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
<body>

<%!
    long like_status;
    String likeColor;
    String like_size;
%>
<%!

    VotesDao votesDao = new VotesDaoImpl();
    ChallengesDetailDao challengesDetailDao = new ChallengesDetailDaoImpl();
    UserDaoImpl userDao = new UserDaoImpl();

    public void votes(long postid ,long userid) {
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance(Locale.US).getTime().getTime());
        Votes votes = votesDao.getVote(postid, userid);
        ChallengeDetail challengeDetail = challengesDetailDao.getChallengeDetail(postid);
        if (votes.getCreate_date() == null) {
            votesDao.addVote(new Votes(
                    challengeDetail, userDao.findUserById(userid), currentTimestamp
            ));
        } else {
            votesDao.deleteVote(votes);
        }
    }
%>
<%
    VotesDao votesDao = new VotesDaoImpl();
    ChallengesDetailDao challengesDetailDao = new ChallengesDetailDaoImpl();
    UserDaoImpl userDao = new UserDaoImpl();
    int postid = Integer.parseInt(request.getParameter("postid"));
    int userid = Integer.parseInt(request.getParameter("userid"));
    int like_number = Integer.parseInt(request.getParameter("like"));
    int votee = Integer.parseInt(request.getParameter("status"));
    BusinessMainpage businessMainpage = new BusinessMainpage();


    System.out.println("--->>>VOTE" + votee);

    if (votee == 1 && like_number >= 0) {
        like_number = like_number - 1;
        like_size = String.valueOf(like_number);
        votes(postid,userid);

    } else if (votee == 0) {
        like_number = like_number + 1;
        like_size = String.valueOf(like_number);
        System.out.println(like_size + "----->");
        votes(postid,userid);

    }
    System.out.println("post id -->>" + postid + " user id -->>" + userid + "begeni--->" + like_number + " AJAXAJAXAJAXJXJAXAJXAJXJAJXAXAJXAJXJAX");
    System.out.println("likestatus -->>" + like_status + " likenumber -->>" + like_number);
%>

<div id="response" style="color:<%=likeColor%>;"><%=like_size%>
</div>
<%
    votee = -1;
    like_number = -1;
    likeColor = null;
    like_size = null;
%>

</body>
</html>
