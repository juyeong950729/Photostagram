$(function(){

        let regPassword  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/; // 8자 이상, 영문, 숫자, 특수문자
        let isPrePassOk = false;        // 이전 비밀번호를 옳게 썼는가?
        let isCheckPassOk = false;      // 비밀번호와 비밀번호 확인이 같은가?
        let isNewPassOk = false;        // 비밀번호가 유효성에 알맞게 설정되었는가?

        var form = $('#passForm');
        form.submit(function(e){
            e.preventDefault();

            let prePass = $('input[name=prePass]').val();
            let pass1 = $('input[name=pass1]').val();
            let pass2 = $('input[name=pass2]').val();

            $.ajax({
                url: '/Photostagram/profile/prePass',
                type: 'post',
                data: {"prePass": prePass},
                dataType: 'json',
                success: function(data){
                    if (data.result > 0){
                        isPrePassOk = true;
                    } else {
                        isPrePassOk = false;
                        alert ('이전 비밀번호를 확인해주세요.');
                        return;
                    }
                }
            });

            if(isPrePassOk == true){

                if(pass1 == pass2) {
                    isCheckPassOk = true;

                    if(!pass1.match(regPassword)){
                        isNewPassOk = false;
                        alert ('새 비밀번호가 형식에 맞지 않습니다. 8자 이상의 영문, 숫자, 특수문자를 포함한 비밀번호를 입력해주세요.');
                        return;
                    } else {
                        isNewPassOk = true;
                    }

                } else {
                    isCheckPassOk = false;
                    alert ('입력한 새 비밀번호가 같은지 확인해주세요.');
                    return;
                }
            }


            if (isPrePassOk == true && isCheckPassOk == true && isNewPassOk == true){
                this.submit();
            }

        });



    });