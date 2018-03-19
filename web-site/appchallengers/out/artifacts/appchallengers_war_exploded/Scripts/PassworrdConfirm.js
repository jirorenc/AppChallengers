$(document).ready(function () {


    $('#inputPasswordConfirm').onkeyup(function () {
        var password=$('#inputPassword').val().toString();
        var passwordConfirm=$('#inputPasswordConfirm').val().toString();
        alert("bastı");
        if(password!==passwordConfirm){
            $( "#changepassword" ).removeAttribute("disabled");
        }
    });
});