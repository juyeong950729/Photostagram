/*
    이름 : 김진우
    날짜 : 2023/03/09
    내용 : 회원가입 유효성검사 Js
*/

// register
$(function(){
    // 데이터 검증에 사용하는 정규표현식
    let regUserName = /^(?=.*[a-z0-9])[a-z0-9]{5,19}$/; // 영어 소문자 또는 숫자 하나 이상 포함.
    let regName  = /^[가-힣]{2,15}$/; // 한글
    let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; // 이메일
        let regPassword  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/; // 8자 이상, 영문, 숫자, 특수문자

    // validation check
    let isUserNameOk = "";
    let isNameOk = false;
    let isEmailOk = "";
    let isPasswordOk = false;

    // 아이디 유효성 검증 & 중복체크
    $('#userName').focusout(function() {
        let userName = $(this).val();

        // 아이디 유효성 검증
        if(!userName.match(regUserName)){
            isUserNameOk = 'unValid';
            $('.id.result-delete').addClass('on');
            $('.id.result-check').removeClass('on');
            return;
        }

        // 아이디 중복체크
        let jsonData = {"userName" : userName};

        $.ajax({
            url: '/Photostagram/member/chkUserName',
            type: 'POST',
            data: jsonData,
            dataType: 'json',
            success: function(data) {
                if(data.result == 1) {
                    $('.id.result-delete').addClass('on');
                    $('.id.result-check').removeClass('on');
                    isUserNameOk = 'exist';
                    return;
                } else {
                    $('.id.result-delete').removeClass('on');
                    $('.id.result-check').addClass('on');
                    isUserNameOk = "";
                }
            }
        });
    });

    // 이름 유효성 체크
    $('input[name=name]').focusout(function(){
        let name = $(this).val();
        isNameOk = true;

        if(!name.match(regName)){
            isNameOk = false;
            $('.name.result-delete').addClass('on');
            $('.name.result-check').removeClass('on');
        }else{
            isNameOk = true;
            $('.name.result-delete').removeClass('on');
            $('.name.result-check').addClass('on');
        }
    });

    // 이메일 유효성 검사 & 중복 체크
    $('input[name=email]').focusout(function(){
        let email = $(this).val();

        // 이메일 유효성 검사
        if(!email.match(regEmail)){
            isEmailOk = 'unValid';
            $('.eml.result-delete').addClass('on');
            $('.eml.result-check').removeClass('on');
            return;
        }

        // 이메일 중복검사
        let jsonData = {"email" : email};

        $.ajax({
            url: '/Photostagram/member/chkEmail',
            type: 'POST',
            data: jsonData,
            dataType: 'json',
            success: function(data) {
                if(data.result == 1) {
                    $('.eml.result-delete').addClass('on');
                    $('.eml.result-check').removeClass('on');
                    isEmailOk = 'exist';
                } else {
                    $('.eml.result-delete').removeClass('on');
                    $('.eml.result-check').addClass('on');
                    isEmailOk = "";
                }
            }
        });
    });

    // 비밀번호 유효성 검사
    $('input[name=password]').focusout(function(){
        let password = $('input[name="password"]').val();
        if(!password.match(regPassword)){
            isPasswordOk = false;
            return;
        } else {
            isPasswordOk = true;
            return;
        }
    });


    // submit 전송
    $('#register-next').click(function(e){
        e.preventDefault();

        // 아이디 중복 검사
        if(isUserNameOk=='exist'){
            $('.result-txt').removeClass('on');
            $('.result-txt.exId').addClass('on');
            return false;
        } else if(isUserNameOk=='unValid'){ // 아이디 유효성 검사
            $('.result-txt').removeClass('on');
            $('.result-txt.unId').addClass('on');
            return false;
        } else if(!isNameOk){ // 이름 유효성 검사
            $('.result-txt').removeClass('on');
            $('.result-txt.name').addClass('on');
            return false;
        } else if(isEmailOk=='exist'){ // 이메일 중복검사
            $('.result-txt').removeClass('on');
            $('.result-txt.exEmail').addClass('on');
            return false;
        }else if(isEmailOk=='unValid'){ // 이메일 유효성검사
            $('.result-txt').removeClass('on');
            $('.result-txt.unEmail').addClass('on');
            return false;
        }else if(!isPasswordOk) { // 비밀번호 유효성 검사
            $('.result-txt').removeClass('on');
            $('.result-txt.pass').addClass('on');
            return false;
        }

        let username = $('input[name=username]').val();
        let name = $('input[name=name]').val();
        let email = $('input[name=email]').val();
        let password = $('input[name=password]').val();

        let userData = {
            "username": username,
            "name": name,
            "email": email,
            "password": password
        };

        // session에 저장
        sessionStorage.setItem("user", JSON.stringify(userData));

        $('.main-content').load('/Photostagram/member/birth', function(){
            //alert('birth load!!!');
        });
    });
});

// birth
$(function(){
    $('#birth-next').click(function(e){
        e.preventDefault();

        let year = $('select[name=year]').val();
        let month = $('select[name=month]').val();
        let day = $('select[name=day]').val();

        if(year == null || month == null || day == null) {
            alert('생년월일을 선택하여주세요.');
            return false;
        }

        let userData = sessionStorage.getItem("user");
        let user = JSON.parse(userData);

        // 1의 자리 수이면 앞에 0 붙이기
        if(month < 10 && day < 10) {
            month = '0' + month;
            day = '0' + day;
        }
        user.birth = year + '-' + month + '-' + day;
        sessionStorage.setItem("user", JSON.stringify(user));

        $('.main-content').load('/Photostagram/member/email', function(){

            // 이메일 보내기
            let userData = sessionStorage.getItem("user");
            let user = JSON.parse(userData);
//            console.log(user);

            let email = user.email;

            let jsonData = {"email" : email};
//            console.log(jsonData);

            $.ajax({
                url: '/Photostagram/member/sendEmail',
                method: 'POST',
                async: false, // 비동기방식을 끈다. (ajax를 수행 후 다음 함수가 실행)
                data: jsonData,
                dataType: 'json',
                success: function(data) {
                    if (data.result == 1) {
                        emailCode = data.confirm;
//                        console.log("emailCode = " + emailCode);
                    } else {
                        alert('이메일 코드 전송 실패!')
                        return false;
                    }
                }
            });
        });
    });
});

// 체크박스 모두 선택
function selectAll(selectAll) {
    const checkboxes = document.getElementsByName('check');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })
}

// terms 최종 회원가입
$(function() {
    $('#terms-signUp').click(function(e) {
        e.preventDefault();

        let isCheck1 = $('input[class=terms]').is(':checked');
        let isCheck2 = $('input[class=data]').is(':checked');
        let isCheck3 = $('input[class=locate]').is(':checked');

        if(isCheck1 && isCheck2 && isCheck3) {
            let userData = sessionStorage.getItem("user"); // session에서 가져오기

            $.ajax({
                url: '/Photostagram/member/terms',
                method: 'POST',
                contentType:'application/json',
                data: userData,
                dataType: 'json',
                success: function(data) {
                    if(data.result == 1) {
                        alert('회원가입이 완료되었습니다.');
                        sessionStorage.clear();
                        location.replace("/Photostagram/member/login");
                    } else { // 서버측 유효성 검사 만족하지 않을 때
                        alert('서버측에서의 유효성 검사를 통과하지 못했습니다. \n다시 회원가입 해주시기 바랍니다.');
                        sessionStorage.clear();
                        location.replace("/Photostagram/member/register");
                    }
                }
            });

        } else {
            alert('동의 체크를 하셔야 합니다.');
            return false;
        }
    });
});