

/***  프로필 사진 미리보기  ***/

$(function(){

    function getProfile(input){
    console.log("here1");
         if(input.files && input.files[0]) {
         console.log("here2");
                 const reader = new FileReader()

                 reader.onload = e => {
                 console.log("here3");
                     const preview = document.querySelector('.addProf');
                     const previews = document.querySelector("#profilePhoto");
                     preview.src = e.target.result;
                     previews.src = e.target.result;
                 }

                console.log("here4");
                 reader.readAsDataURL(input.files[0])
             }
         }

         const real = document.querySelector('.real-addProf');
         const fake = document.querySelector("#modalAddProf");
         //const fake = document.querySelector('.addProf');

         if ($('#profilePhoto').hasClass('changeProf') == true){
            console.log("here5");
            const chan = document.querySelector('.changeProf');
            chan.addEventListener('click', () => real.click());
         }

         //fake.addEventListener('click', () => real.click());
         real.addEventListener('change', e => {
            console.log("here6");
             getProfile(e.target);
         });

});


  /*** 프로필 사진 클릭 시 뜨는 모달 창 ***/

    $(function(){
      var modal = document.getElementById("photoModal");
      const btn = document.querySelector(".addNewProf");
      const close = document.querySelector(".photoClose");
      const del = document.querySelector(".deleteProf");


        $('#profilePhoto').click(function(){

            let classValue = $(this).attr('class');

            if(classValue == 'addNewProf'){
                console.log("class1");
                //alert('addNewProf!!!');
                $('#photoModal').show();
            }else if(classValue == 'addProf'){
                console.log("class2");
                //alert('addProf!!!');
                $('.real-addProf').trigger('click');
            }else{
                return 0;
            }

        });

        $('#modalAddProf').click(function(){
            $('.real-addProf').trigger('click');
            modal.style.display="none";
            console.log("here7");
        });

      close.onclick = function() {
        modal.style.display = "none";
      }

      window.onclick = function(event) {
        if (event.target == modal) {
          modal.style.display = "none";
        }
      }

      del.onclick = function() {
        modal.style.display = "none";
        let profile = $('#profilePhoto');
        let spinner = $('.profileSpinner');
        let tag = $(this);

        spinner.show();
        setTimeout(function(){
            $.ajax({
                url: '/Photostagram/profile/upload',
                type: 'get',
                dataType: 'json',
                data: {"type":"delete"},
                success: function(data){
                    if(data.result > 0){
                        spinner.hide();
                        alert('프로필 삭제가 완료되었습니다.');
                        profile.attr('src', 'img/44884218_345707102882519_2446069589734326272_n.jpg');
                        profile.addClass('addProf');
                        profile.removeClass('addNewProf');
                    }
                }
            });
        }, 1000);


      }

    });




/***  프로필 이미지 저장 ***/

$(function(){

    $('.real-addProf').change(function(){
        let photo = $('#profilePhoto');
        var file = $(this)[0].files[0];
        var formData = new FormData();
        formData.append("file", file);

        $.ajax({
            type: 'post',
            url: '/Photostagram/profile/upload',
            data: formData,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function(data){
                if (data.result > 0) {
                    alert ('프로필 업로드가 완료되었습니다.');
                    photo.removeClass('addProf');
                    photo.addClass('addNewProf');
                }
            }
        });

    });

});
