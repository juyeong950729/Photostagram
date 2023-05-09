$(function(){

        /*** 팔로우 ***/
        $(document).on("click",".userFollow",function(e){
            e.preventDefault();
            let tag = $(this);

            tag.children('.spinner').show();

            let userName = $(this).attr('data-value');

            setTimeout(function(){
                $.ajax({
                    url: '/Photostagram/profile/follow',
                    type: 'post',
                    dataType: 'json',
                    data: {'userName':userName, 'type':'insert'},
                    success: function(data){
                        console.log('here1');
                        if(data.result > 0){
                            console.log('here2');
                            tag.parent().children('.userFollow').hide();
                            tag.children('.spinner').hide();
                            tag.parent().children('.userFollowing').show();
                        }
                    }
                });
            }, 1000);

        });

        $(document).on("click",".tagFollow",function(e){
            e.preventDefault();
            console.log ('here1');
            let tag = $(this);

            tag.children('.spinner').show();
            console.log ('here2');

            let tagId = $(this).attr('data-value');

            setTimeout(function(){
            console.log ('here3');
                $.ajax({
                    url: '/Photostagram/profile/tagFollow',
                    type: 'post',
                    dataType: 'json',
                    data: {'no':tagId, 'type':'insert'},
                    success: function(data){
                    console.log ('here4');
                        if(data.result > 0){
                        console.log ('here5');
                            tag.parent().children('.tagFollow').hide();
                            tag.children('.spinner').hide();
                            tag.parent().children('.tagFollowing').show();
                        }
                    }
                });
            }, 1000);
        });

        $(document).on("click",".tagFollowing",function(e){
            e.preventDefault();
            console.log ('here1');
            let tag = $(this);

            tag.children('.spinner').show();

            let tagId = $(this).attr('data-value');
            console.log ('here2');

            setTimeout(function(){
            console.log ('here3');
                $.ajax({
                    url: '/Photostagram/profile/tagFollow',
                    type: 'post',
                    dataType: 'json',
                    data: {'no':tagId, 'type':'delete'},
                    success: function(data){
                    console.log ('here4');
                        if(data.result > 0){
                        console.log ('here5');
                            tag.parent().children('.tagFollowing').hide();
                            tag.children('.spinner').hide();
                            tag.parent().children('.tagFollow').show();
                        }
                    }
                });
            }, 1000);
        });

        /*** 팔로잉 ***/
        /*
        $('.userFollowing').click(function(e){
            e.preventDefault();
            let tag = $(this);

            tag.children('.spinner').show();

            let userName = $(this).attr('data-value');

            setTimeout(function(){
                $.ajax({
                    url: '/Photostagram/profile/follow',
                    type: 'post',
                    dataType: 'json',
                    data: {'userName':userName, 'type':'delete'},
                    success: function(data){
                        console.log('here1');
                        if(data.result > 0){
                            console.log('here2');
                            tag.parent().children('.userFollowing').hide();
                            tag.children('.spinner').hide();
                            tag.parent().children('.userFollow').show();
                        }
                    }
                });
            }, 1000);
        });
        */

    });



 /*** 팔로우, 언팔로우 시 스타일 변화 ***/

      $(function(){
        $('#follow').click(function(){
          $('#follow').hide();
          $('#following').show();
        });
      });
      $(function(){
        $('#unFollow').click(function(){
          $('#following').hide();
          $('#follow').show();
        });
       });
      $(function(){
        $('.fClose').click(function(){
          $('#fCancel').hide();
        });
      });
      $(function(){
        $('#postFollow').click(function(){
            $('#postFollow').hide();
            $('#postFollowing').show();
        });
      });
      $(function(){
          $('#postUnFollow').click(function(){
            $('#postFollowing').hide();
            $('#postFollow').show();
          });
       });
      $(function(){
        $('.pClose').click(function(){
          $('#pCancel').hide();
        });
      });