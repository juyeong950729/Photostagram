// email code 입력
$(function(){

    // 화면에 이메일 띄우기
    let userData = sessionStorage.getItem("user");
    let user = JSON.parse(userData);

    let email = user.email;
    document.getElementById('userEmail').innerHTML = email;

    $('#email-next').click(function(e){
        e.preventDefault();
        let inputCode = $('input[name="input-code"]').val();
//        console.log("inputCode = " + inputCode);
        if (inputCode == emailCode) {
            alert('이메일 인증이 완료되었습니다.');
            $('.main-content').load('/Photostagram/member/terms', function(){
//                alert('terms load!!!');
            });
        } else if (inputCode == "") {
            alert('인증번호를 입력하여주세요.');
        } else {
            alert('인증번호가 일치하지 않습니다. 다시 확인해주세요.');
        }
    });
});






