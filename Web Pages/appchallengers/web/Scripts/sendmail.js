$(document).ready(function (){
    $('#btn-mailsend').click(function(){
        var usermail=$('#txt-usermail').val().toString();

        if(usermail!=null && usermail!=""){
            $('#btn_sendvalue').html("Tekrar Gönder");
        }

    });


});


