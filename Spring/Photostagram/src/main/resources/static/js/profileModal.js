
  /*** 프로필 팔로잉 클릭 시 뜨는 모달 창 ***/

    $(function(){
      var modal = document.getElementById("fCancel");
      var btn = document.getElementById("following");
      var span = document.getElementsByClassName("fClose")[0];

      btn.onclick = function() {
        modal.style.display = "block";
      }

      span.onclick = function() {
        modal.style.display = "none";
      }

      window.onclick = function(event) {
        if (event.target == modal) {
          modal.style.display = "none";
        }
      }
    });


    /*** 팔로워 모달창 ***/

    $(function(){
      var modal = document.getElementById("followerModalWindow");
      var btn = document.getElementById("followerModal");
      var span = document.getElementsByClassName("closeFollow")[0];

      btn.onclick = function() {
        modal.style.display = "block";
      }

      span.onclick = function() {
        modal.style.display = "none";
      }

      window.onclick = function(event) {
        if (event.target == modal) {
          modal.style.display = "none";
        }
      }

     $('#followerModal').click(function(e){
        e.preventDefault();
     });

    });

    /*** 팔로잉 모달창 ***/

    $(function(){
      var modal = document.getElementById("followingModalWindow");
      var btn = document.getElementById("followingModal");
      var span = document.getElementsByClassName("closeFollowing")[0];

      btn.onclick = function() {
        modal.style.display = "block";
      }

      span.onclick = function() {
        modal.style.display = "none";
      }

      window.onclick = function(event) {
        if (event.target == modal) {
          modal.style.display = "none";
        }
      }

     $('#followingModal').click(function(e){
        e.preventDefault();
     });

    });

    /*** 내 팔로워 중 삭제를 클릭하면 뜨는 모달 창 ***/

    $(function(){
          let modal = $('#followerDelete');
          let btn = $('.userSelect');
          let close = $('.pClose');


          $(document).on("click",".userSelect",function(e){
          //btn.click(function(e){
            modal.show();
            let tag = $(this);
            let userName = tag.attr('data-value');
            let userImg = tag.attr('data-image');

            let deleteName = $("#modalDeleteName");
            deleteName.html(userName);

            let deleteImg = $("#modalDeleteImg");

            if (userImg != null){
                deleteImg.attr('src', 'thumb/'+userImg+'/')
            } else {
                deleteImg.attr('src', 'img/44884218_345707102882519_2446069589734326272_n.jpg')
            }

            //console.log (userImg);

            $('.followerDelete').click(function(e){
                e.preventDefault();
                tag.children('.spinner').show();
                modal.hide();

                setTimeout(function(){
                    $.ajax({
                        url: '/Photostagram/profile/follow',
                        type: 'post',
                        dataType: 'json',
                        data: {'userName':userName, 'type':'myDelete'},
                        success: function(data){
                            console.log('here1');
                            if(data.result > 0){
                                console.log('here2');
                                tag.children('.spinner').hide();
                                tag.text('삭제됨').css('font-weight', 'bold');
                            }
                        }
                    });
                }, 1000);

            });
          });

          window.onclick = function(event) {
            if (event.target == modal) {
              modal.style.display = "none";
            }
          }

          close.click (function(e){
            modal.hide();
          });

        });



    /*** 팔로잉 목록 내 팔로잉, 팔로워 버튼 눌러 언팔로우 하는 모달 ***/

    $(function(){
          let modal = $('#followingCancel');
          let btn = $('.userFollowing');
          let close = $('.pClose');


          $(document).on("click",".userFollowing",function(e){
            e.preventDefault();
            modal.show();
            let tag = $(this);
            let userName = tag.attr('data-value');
            let userImg = tag.attr('data-image');

            let cancelName = $("#modalCancelName");
            cancelName.html(userName);

            let cancelImg = $("#modalCancelImg");

            if (userImg != null){
                cancelImg.attr('src', 'thumb/'+userImg+'/')
            } else {
                cancelImg.attr('src', 'img/44884218_345707102882519_2446069589734326272_n.jpg')
            }

            $('.followingCancel').click(function(e){
                e.preventDefault();
                tag.children('.spinner').show();
                modal.hide();

                setTimeout(function(){
                    $.ajax({
                        url: '/Photostagram/profile/follow',
                        type: 'post',
                        dataType: 'json',
                        data: {'userName':userName, 'type':'delete'},
                        success: function(data){
                            console.log('here1');
                            if(data.result > 0){
                                tag.parent().children('.userFollowing').hide();
                                tag.children('.spinner').hide();
                                tag.parent().children('.userFollow').show();
                            }
                        }
                    });
                }, 1000);

            });
          });

          window.onclick = function(event) {
            if (event.target == modal) {
              modal.style.display = "none";
            }
          }

          close.click (function(e){
            e.preventDefault();
            modal.hide();
          });

        });


    /*** 프로필 세팅 모달 ***/
    $(function(){

        let modal = $('#sModal');
        let btn = $('#settingIcon');
        let close = $('.pClose');

        btn.click(function(e){
            e.preventDefault();
            modal.show();
        });

        close.click (function(e){
            e.preventDefault();
            modal.hide();
        });

    });
