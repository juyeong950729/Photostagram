/*
    이름 : 김진우
    날짜 : 2023/03/15
    내용 : 비밀번호 찾기 js
*/

$(function(){
    let regUserName = /^(?=.*[a-z0-9])[a-z0-9]{5,19}$/; // 영어 소문자 또는 숫자 하나 이상 포함.
    let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; // 이메일

    let name = null;

    $('#checkPass-next').click(function(e){
        e.preventDefault();

        let userName = $('input[name=userName]').val();
        let email = $('input[name=email]').val();

        if(userName == "") { // 아이디가 공백일 때
            $('.check-txt').removeClass('on');
            $('.check-txt.nId').addClass('on');
            return false;
        } else if (email == "") {
            $('.check-txt').removeClass('on');
            $('.check-txt.nEmail').addClass('on');
            return false;
        } else if (!userName.match(regUserName)) {
            $('.check-txt').removeClass('on');
            $('.check-txt.id').addClass('on');
            return false;
        } else if (!email.match(regEmail)) {
            $('.check-txt').removeClass('on');
            $('.check-txt.email').addClass('on');
            return false;
        }

        let jsonData = {
                "userName" : userName,
                "email" : email
                };

        // 회원이 존재하는지?
        $.ajax({
            url: '/Photostagram/member/searchPass',
            method: 'POST',
            data: jsonData,
            dataType: 'json',
            success: function(data) {
                if(data.name != "") {
                    name = data.name;
                    sendEmailPass(userName, email);
                } else {
                    $('.check-txt').removeClass('on');
                    $('.check-txt.nEx').addClass('on');
                    return false;
                }
            }
        });
    });

    // 임시 비밀번호 이메일로 발송
    function sendEmailPass(userName, email) {

        console.log('email : ' + email);
        console.log('userName : ' + userName);

        let jsonData = {"email" : email, "userName" : userName};

        $.ajax({
            url: '/Photostagram/member/sendEmailPass',
            method: 'POST',
            data: jsonData,
            dataType: 'json',
            success: function(data) {
                if(data.result == 1) {
                    //alert('임시 비밀번호 전송 성공!!');
                    $('.main-content').load('/Photostagram/member/resultPass', function(){
                        document.getElementById('resultName').innerHTML = name;
                        document.getElementById('resultEmail').innerHTML = email;
                    });
                } else {
                    alert('임시 비밀번호 전송 실패!!!');
                    return false;
                }
            }
        });
    }
});