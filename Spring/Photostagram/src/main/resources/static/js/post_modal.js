$(function(){
    // modal 팝업 띄우기
    let modal = $('#modal_postSelect');
    let modal_delete = $('#modal_postSelect_delete');
    let modal_modify = $('#modal_modify_feed_content');

    $('.postSelect').on('click', function(){
        modal.css('top', $(window).scrollTop()+'px');
        modal.css('display', 'flex');
        $('body').css('overflow-y', 'hidden');
    });

    $('.modal_postCancel').on('click', function(){
        modal.css('display', 'none');
        $('body').css('overflow-y', 'visible');
    });

    $(document).on('click', '.modal_postDelete', function(){
        modal.css('display', 'none');
        modal_delete.css('display', 'flex');
    });

    $(document).on('click', '.post_delete_cancel', function(){
        modal_delete.css('display', 'none');
        $('body').css('overflow-y', 'visible');
    });

    $(document).on('click', '.modal_title_side_post_cancel', function(){
        modal_modify.css('display', 'none');
    });

    $('#tooltipArea_post').on('click', function(e){

        console.log('0- target id : ' + e.target.id);
        console.log('1- target id : ' + typeof e.target.id);

        console.log('2- target id : ' + e.target.id == 'beforeBtn');
        console.log('3- target id : ' + e.target.id == 'afterBtn');

        if(e.target.id != 'afterBtnPost' && e.target.id != 'beforeBtnPost'){
            x = e.offsetX;
            y = e.offsetY;

            console.log('------------------');
            console.log('x 좌표 : '+x);
            console.log('x 좌표 : '+y);

            $('.tagModalPost').css({left:x-18, top:y, display:'block'});
        }
    });

     $('input[name=tagUserPost]').on('keyup', function(){
        let searchUser = $(this).val();

        console.log('search대상 : '+searchUser);

        $('.snipper').show();

        let jsonData = {"search":searchUser};

        setTimeout(function(){
            $.ajax({
                url:'/Photostagram/findTagUser',
                method:'POST',
                data:jsonData,
                dataType:'json',
                success:function(data){
                    $('.snipper').hide();
                    $('.tagUserListPost').empty();
                    let tagUsers = data.result;
                    console.log(tagUsers);
                    tagUsers.forEach(function(tagUser){
                        let tags = "<div class='tagSearchesPost'>";
                            tags += "<div class='tagSearchUserPost' data-userno="+tagUser.no+" data-username="+tagUser.username+">";
                            if(tagUser.profileImg != null){
                                tags += "<img src='/Photostagram/thumb/"+tagUser.profileImg+"'>";
                            }else{
                                tags += "<img src='/Photostagram/img/44884218_345707102882519_2446069589734326272_n.jpg'>";
                            }
                            tags += "<div class='tagUserInfoPost'>";
                            tags += "<div>"+tagUser.username+"</div>";
                            if(tagUser.profileText != null){
                                tags += "<span>"+tagUser.profileText+"</span>";
                            }else{
                               tags += "<span></span>";
                            }
                            tags += "</div>";
                            tags += "</div>";
                            tags += "</div>";

                        $('.tagUserListPost').append(tags);
                    });
                }
            });
        }, 1000);
    });
});