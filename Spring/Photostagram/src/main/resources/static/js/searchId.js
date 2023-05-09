/*
    이름 : 김진우
    날짜 : 2023/03/14
    내용 : 아이디 찾기 js
*/

//let emailCode = null;

$(function(){
    let regName  = /^[가-힣]{2,15}$/; // 한글
    let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; // 이메일

    $('#checkIdCode').click(function(e){
        e.preventDefault();

        let name = $('.checkId.name').val();
        let email = $('.checkId.email').val();

        // 유효성 검사
        if(name == "") {
            $('.check-txt').removeClass('on');
            $('.check-txt.nName').addClass('on');
            return false;
        } else if (email == "") {
            $('.check-txt').removeClass('on');
            $('.check-txt.nEmail').addClass('on');
            return false;
        } else if (!name.match(regName)) {
            $('.check-txt').removeClass('on');
            $('.check-txt.name').addClass('on');
            return false;
        }else if (!email.match(regEmail)) {
            $('.check-txt').removeClass('on');
            $('.check-txt.email').addClass('on');
            return false;
        }

        let jsonData = {"name" : name, "email" : email};

        // 회원이 맞는지 체크
        $.ajax({
            url: '/Photostagram/member/searchId',
            method: 'POST',
            data: jsonData,
            dataType: 'json',
            success: function(data) {
                if(data.userName != "") {
                    alert(email + '로 코드를 발송하였습니다.\n이메일을 확인하여주세요.');
                    $('.check-txt').removeClass('on');
                    $('.codeDiv').addClass('on');
                    $('.emailDiv').addClass('on');
                    userName = data.userName;
                    sendMail(email);
                }else {
                    $('.check-txt').removeClass('on');
                    $('.check-txt.nEx').addClass('on');
                    return false;
                }
            }
        });
    });

    function sendMail(email) {
    //    alert("sendMail!!!" + email);
        let jsonData = {"email" : email};

        $.ajax({
            url: '/Photostagram/member/sendEmail',
            method: 'POST',
            data: jsonData,
            dataType: 'json',
            success: function(data) {
                if (data.result == 1) {
                    emailCode = data.confirm;
//                    console.log("emailCode = " + emailCode);
                } else {
                    alert('이메일 코드 전송 실패!')
                    return false;
                }
            }
        });
    }

    $('#checkId-next').click(function(e){
        e.preventDefault();

        let inputCode = $('input[name=inputCode]').val();
        if(inputCode == emailCode) {
            alert('이메일 인증이 완료되었습니다.');
            $('.main-content').load('/Photostagram/member/resultId', function(){
                console.log("userName = " + userName);
                document.getElementById('result-id').innerHTML = userName;
            });
        }else if (inputCode == "") {
            alert('인증번호를 입력하여주세요.');
        } else {
            alert('인증번호가 일치하지 않습니다. 다시 확인해주세요.');
        }
    });
});

