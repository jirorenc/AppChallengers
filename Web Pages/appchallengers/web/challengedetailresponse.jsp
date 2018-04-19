<%@ page import="com.appchallengers.appchallengers.dao.dao.ChallengesDetailInfoDao" %>
<%@ page import="com.appchallengers.appchallengers.dao.daoimpl.ChallengesDetailInfoDaoImpl" %>
<%@ page import="com.appchallengers.appchallengers.model.response.ChallengeResponse" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: MHMTNASIF
  Date: 19.04.2018
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .dairesel{
            width: 50px;
            height: 50px;
            border-radius: 150px;
            -webkit-border-radius: 150px;
            -moz-border-radius: 150px;
            background: url(http://resim-dosyasinin.adresi/resim.jpg) no-repeat;
        }
    </style>

</head>
<body>

<%!
    String dene;
    int status=-1;
    List<ChallengeResponse> challengeResponseList;
%>

<%
    ChallengesDetailInfoDao challengesDetailInfoDao= new ChallengesDetailInfoDaoImpl();
    int challengeid=Integer.parseInt(request.getParameter("challengeid"));
    int userid=Integer.parseInt(request.getParameter("userid"));
    status=Integer.parseInt(request.getParameter("status"));
   if(status==0){
       dene="populer";
   }else if(status==1){
       dene="enson";
      challengeResponseList=challengesDetailInfoDao.getChallengeDetailOrderByDesc(userid,challengeid);

   }
%>
<%if(status==0){%>

<%}%>
<%if(status==1){%>
<%for(ChallengeResponse feed: challengeResponseList){%>
<div class="container">
    <div class="row">
        <!-- LEFT PART -->
        <div class="col-sm-2"></div>
        <!-- CONTENT PART-->
        <div class="col-sm-8">
            <!-- PANEL BEGINNIG -->
            <div class="vote" id="vote-<%=feed.getChallenge_detail_id()%>"><%=feed.getVote()%></div>
            <div class="panel panel-default" id="p<%=feed.getChallenge_detail_id()%>">
                <div class="panel-heading " style="background-color: #fefefe">
                    <ul class="list-inline">
                        <li>
                            <a href="profile.jsp?ref=<%=feed.getChallenge_detail_user_id()%>" i> <!-- jsp?=feed.getChallenge_detail_user_id()-->
                                <img src="<%=feed.getProfilepicture()%>" alt="profil.jpg"  class="dairesel">
                            </a>
                        <li>
                            <a href="profile.jsp?ref=<%=feed.getChallenge_detail_user_id()%>"><strong><%=feed.getFullname()%></strong></a> <!-- jsp?=feed.getChallenge_detail_user_id()-->
                        </li>
                        <li><strong>></strong></li>
                        <li><%=feed.getHeadline()%></li>
                    </ul></div>
                <div class="panel-body">
                    <div align="center"  class="embed-responsive embed-responsive-4by3">
                        <video id="video-<%=feed.getChallenge_detail_id()%>" controls muted autoplay loop class="embed-responsive-item" onclick="this.paused ? this.play() : this.pause();">
                            <source src="<%=feed.getChallenge_url()%>" type="video/mp4">
                            <track src="<%=feed.getChallenge_url()%>" label="English subtitles"
                                   kind="subtitles" srclang="en" default></track>
                        </video>
                    </div>
                </div>
                <div class="panel-footer" style="background-color: #fefefe">

                    <ul class="list-inline">
                        <li>
                            <button type="button" class="btn btn-default btn-sm" id="btnlike-<%=feed.getChallenge_detail_id()%>"><span
                                    class="glyphicon glyphicon-thumbs-up" ></span>Like
                            </button>
                        </li>
                        <li><div class="modal-like" id="likenumber-<%=feed.getChallenge_detail_id()%>" data-toggle="modal" data-target="#begenenler" ><%=feed.getLikes()%></div></li>

                    </ul>
                    <div align="right">
                        <button type="button" id="btn_like5"><span class="glyphicon glyphicon-flag"></span></button>
                    </div>
                    <!--  <span class="pull-right"><i class="glyphicon glyphicon-search"></i></span> -->
                </div>
                <!-- PANEL ENDING -->
            </div>
        </div>
        <!--RIGHT PART -->
        <div class="col-sm-2"></div>
    </div>
</div>

<div class="modal fade" id="begenenler" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Begenenler</h4>
            </div>
            <div class="modal-body" id="model-begenenler">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script  type="text/javascript">
    $(document).ready(function () {
        $('.vote').hide();
        <% if(feed.getVote()==1){ %>
        // $('#btnlike-<%=feed.getChallenge_detail_id()%>').css("background-color","yellow");
        $('#btnlike-<%=feed.getChallenge_detail_id()%>').css("background-color","yellow");
        <%}%>
    });

</script>

<%}%>
<%}%>

<script  type="text/javascript">

    $('.btn').click(function () {
        var id=$(this).attr('id').toString();            // Tıklanılan butonun id sin, al

        var index= id.indexOf("-");                     // btnlike- kısmını at sadece id yi al  "clear_id"
        var id_firstpart = id.slice(0,index);           // - den öncesini al ve kontrol et like mı dislike butonumu
        var id_lenght=id_firstpart.length;              // uzunluğu 7 ise btnlike eger 10 ise btndislike butonuna basılmıs
        var clear_id = id.slice(index+1);               // Tıklanınlan postun saf id sine ulasatık buton id üzarinden
        var  vote=$('#vote-'+clear_id).html();
        if(vote==0){
            $('#vote-'+clear_id).html(1);
            $('#btnlike-'+clear_id).css("background-color","yellow");
        }else if(vote ==1){
            $('#vote-'+clear_id).html(0);
            $('#btnlike-'+clear_id).css("background-color","");
        }
        //alert(vote);
        if(id_lenght==7){                               // like butonuna basılma durumu

            var like_idd="likenumber-"+clear_id;         // begenelerin sayısısnı yazan div in id si
            var like_sayısı=$("#"+like_idd).html().trim().toString();
            var color="black";

            //alert("Like butonuna basıldı"+clear_id+like_sayısı);
            //  Buradaki ajax metodu beğenilen postun id sini ve  begennen kullanıcının id sini
            // parametre olarak gönderiyor. Eger bu kisi daha önce begenmisse begeni geri alınacak
            // ilk defa begenmisse begeni bir arttırılıp veri tabanına kaydedilecek
            $.ajax({
                url:"http://localhost:8080/likeresponse.jsp",
                data:{postid:clear_id,userid:<%=userid%>,like:like_sayısı.toString(),status:vote},
                success: function (cevap) {
                    // $("#"+like_idd).html(cevap);
                    $("#"+like_idd).html($(cevap).filter("div").html());
                    //  color= $(cevap).filter("div").css("color").toString();

                }
            });
        }
    });

    // modal filling with ajax
    $('.modal-like').click(function () {
        var id=$(this).attr('id').toString();
        var index= id.indexOf("-");
        var post_id = id.slice(index+1);
        $.ajax({
            url:"http://localhost:8080/likepeopleresponse.jsp",
            data:{postid:post_id},
            success: function (cevap) {
                // $("#"+like_idd).html(cevap);
                $('#model-begenenler').html(cevap);
            }
        });
    });


    $(".modal-like").hover(function () {
        $(this).css("cursor","pointer");
    },function () {
    });


    function notification() {
        alert("aa");
        $.ajax({
            url:"http://localhost:8080/notifications.jsp",
            data:{userid:<%=userid%>},
            success: function (cevap) {
                $('#notification').html(cevap);
            }
        });

    }
</script>
</body>
</html>
