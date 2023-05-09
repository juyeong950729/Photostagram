function dragOver(e){
    e.stopPropagation();
    e.preventDefault();
    
    if (e.type == "dragover") {
        $(e.target).css({
            "background-color": "black",
            "outline-offset": "-20px"
        });
    } else {
        $(e.target).css({
            "background-color": "white",
            "outline-offset": "-10px"
        });
    }
}

function uploadFiles(e){
    e.stopPropagation();
    e.preventDefault();
    console.log(e.dataTransfer)
    console.log(e.originalEvent.dataTransfer)

    e.dataTransfer = e.originalEvent.dataTransfer;

    files = e.dataTransfer.files;

    if (files[0].type.match(/image.*/)) {
        $('#modal_add_feed_content_drop').css({
            display : 'flex',
            "top" : $(window).scrollTop()+'px'
        });
        $('#input_image_drop').css({
            "background-image": "url(" + window.URL.createObjectURL(files[0]) + ")",
            "outline": "none",
            "background-size": "contain",
            "background-repeat" : "no-repeat",
            "background-position" : "center"
        });
        $('#modal_add_feed').css({
            display: 'none'
        })
        if(files.length > 1){
          $('.afterBtnDrop').css({"visibility":"visible"});
        }
        $('video').css({"display":"none"});
    }else if(files[0].type.match(/video.*/)){
       $('#modal_add_feed_content').css({
           display : 'flex',
           "top" : $(window).scrollTop()+'px'
       });
       $('.modal_image_upload_content').css({
         //"background-image": "url(./img/play.png)",
         "outline": "none",
         "background-size": "contain",
         "background-repeat" : "no-repeat",
         "background-position" : "center"
       });
       $('video').attr('src', window.URL.createObjectURL(images[0]));
       $('#modal_add_feed').css({
           display: 'none'
       });
   }else{
        alert('이미지가 아닙니다.');
        return;
    }
}

  $(function(){

      $('#search').on('click',function(){
          $('.sidesearch').toggleClass('on');
          $('.side').toggleClass('on');              
      });

      $('#search_close').on('click', function(){
        $('.sidesearch').toggleClass('on');
        $('.side').toggleClass('on');
      });

      $('#alarm').on('click',function(){
          $('.sidealarm').toggleClass('on');
          $('.side').toggleClass('on');              
      });

      $('#alarm_close').on('click',function(){
        $('.sidealarm').toggleClass('on');
        $('.side').toggleClass('on');              
      });

      let modal = $('#modal_add_feed');
      let modal_feed = $('#modal_add_feed_content');
      let modal_feed_drop = $('#modal_add_feed_content_drop');

      $('#add_feed').on('click', function(){
          modal.css('top', $(window).scrollTop()+'px');
          modal.css('display', 'flex');
          $('body').css('overflow-y', 'hidden');
          $('.bx-controls-direction').css('display', 'none');
      });

      $('#close_modal').on('click', function(){
        modal.css('display', 'none');
        $('body').css('overflow-y', 'visible');
        $('.bx-controls-direction').css('display', 'flex');
        $('.tagModal').css({display:'none'});
        $('#tooltipArea').empty();
      });

      $('#close_modal_feed').on('click', function(){
        modal_feed.css('display', 'none');
        $('body').css('overflow-y', 'visible');
        $('.tagModal').css({display:'none'});
        $('#tooltipArea').empty();
      });

      $('#close_modal_feed_drop').on('click', function(){
        modal_feed_drop.css('display', 'none');
        $('body').css('overflow-y', 'visible');
        $('.tagModal').css({display:'none'});
        $('#tooltipArea').empty();
      });
      
      $('.modal_image_upload')
        .on("dragover", dragOver)
        .on("dragleave", dragOver)
        .on("drop", uploadFiles);

      $('#file_upload').on('change', function(){
          //alert('file_upload!');

          let file = this.files;
          console.log('file : ' +file);
          let dataTransfer = new DataTransfer();

          let fileArray = Array.from(file);
          console.log('fileArray : ' +fileArray);
          
          fileArray.forEach(file => {
              dataTransfer.items.add(file);
          });

          let images= dataTransfer.files;
          console.log('images : '+images);

          if (images[0].type.match(/image.*/)) {
              $('#modal_add_feed_content').css({
                  display : 'flex',
                  "top" : $(window).scrollTop()+'px'
              });
              $('.modal_image_upload_content').css({
                "background-image": "url(" + window.URL.createObjectURL(images[0]) + ")",
                "outline": "none",
                "background-size": "contain",
                "background-repeat" : "no-repeat",
                "background-position" : "center",
              });                  
              $('#modal_add_feed').css({
                  display: 'none'
              });
              if(images.length > 1){
                $('.afterBtn').css({"visibility":"visible"});
              }
              $('video').css({"display":"none"});
          }else if(images[0].type.match(/video.*/)){
              $('#modal_add_feed_content').css({
                  display : 'flex',
                  "top" : $(window).scrollTop()+'px'
              });
              $('.modal_image_upload_content').css({
                //"background-image": "url(./img/play.png)",
                "outline": "none",
                "background-size": "contain",
                "background-repeat" : "no-repeat",
                "background-position" : "center"
              });
              $('video').attr('src', window.URL.createObjectURL(images[0]));
              $('#modal_add_feed').css({
                  display: 'none'
              });
          }else{
              alert('이미지가 아닙니다.');
              return;
          }
      });
    
      

    let j = 0;

    $('.afterBtn').on('click', function(){
        let inputFile = $('#file_upload');
        let file = inputFile[0].files;

        console.log(file);

        let dataTransfer = new DataTransfer();

        let fileArray = Array.from(file);
        console.log('fileArray : ' +fileArray);

        fileArray.forEach(f => {
            dataTransfer.items.add(f);
        });

        let images= dataTransfer.files;
        console.log('images : '+images);

        if(j+1 == images.length)return;
        if(j+2 == images.length){
            $(this).css({"visibility":"hidden"});
        }
        j++;

        console.log("j1 :"+j);

        let length = $('#tooltipArea').children('.tag1').length;
        for(i=0; i<length; i++){
            if($('#tooltipArea').children().eq(i).attr('data-page') == j){
                $('#tooltipArea').children().eq(i).show();
            }else{
                $('#tooltipArea').children().eq(i).hide();
            }
        }

        $('.modal_image_upload_content').css({
            "background-image": "url(" + window.URL.createObjectURL(images[j]) + ")",
            "outline": "none",
            "background-size": "contain",
            "background-repeat" : "no-repeat",
            "background-position" : "center"
        }); 
        console.log("j2 :"+j);

        $('.beforeBtn').css({"visibility":"visible"});
    });

    $('.beforeBtn').on('click', function(){
        let inputFile = $('#file_upload');
        let file = inputFile[0].files;

        console.log(file);

        let dataTransfer = new DataTransfer();

        let fileArray = Array.from(file);
        console.log('fileArray : ' +fileArray);

        fileArray.forEach(f => {
            dataTransfer.items.add(f);
        });

        let images= dataTransfer.files;
        console.log('images : '+images);

        if(j == 0) return;
        if(j == 1){
            $(this).css({"visibility":"hidden"});
        }
        j--;

        let length = $('#tooltipArea').children('.tag1').length;
        for(i=0; i<length; i++){
            if($('#tooltipArea').children().eq(i).attr('data-page') == j){
                $('#tooltipArea').children().eq(i).show();
            }else{
                $('#tooltipArea').children().eq(i).hide();
            }
        }

        $('.modal_image_upload_content').css({
            "background-image": "url(" + window.URL.createObjectURL(images[j]) + ")",
            "outline": "none",
            "background-size": "contain",
            "background-repeat" : "no-repeat",
            "background-position" : "center"
        }); 
        console.log("j"+j); 
        $('.afterBtn').css({"visibility":"visible"});
    });

    $('.afterBtnDrop').on('click', function(){
        let file = files;

        console.log(file);

        let dataTransfer = new DataTransfer();

        let fileArray = Array.from(file);
        console.log('fileArray : ' +fileArray);

        fileArray.forEach(f => {
            dataTransfer.items.add(f);
        });

        let images= dataTransfer.files;
        console.log('images : '+images);

        if(j+1 == images.length)return;
        if(j+2 == images.length){
            $(this).css({"visibility":"hidden"});
        }
        j++;

        console.log("j1 :"+j);

        let length = $('#tooltipArea').children('.tag1').length;
        for(i=0; i<length; i++){
            if($('#tooltipArea').children().eq(i).attr('data-page') == j){
                $('#tooltipArea').children().eq(i).show();
            }else{
                $('#tooltipArea').children().eq(i).hide();
            }
        }

        $('.modal_image_upload_content').css({
            "background-image": "url(" + window.URL.createObjectURL(images[j]) + ")",
            "outline": "none",
            "background-size": "contain",
            "background-repeat" : "no-repeat",
            "background-position" : "center"
        });
        console.log("j2 :"+j);
        $('.beforeBtnDrop').css({"visibility":"visible"});
    });

    $('.beforeBtnDrop').on('click', function(){
        let file = files;

        console.log(file);

        let dataTransfer = new DataTransfer();

        let fileArray = Array.from(file);
        console.log('fileArray : ' +fileArray);

        fileArray.forEach(f => {
            dataTransfer.items.add(f);
        });

        let images= dataTransfer.files;
        console.log('images : '+images);

        if(j == 0) return;
        if(j == 1){
            $(this).css({"visibility":"hidden"});
        }

        j--;

        let length = $('#tooltipArea').children('.tag1').length;
        for(i=0; i<length; i++){
            if($('#tooltipArea').children().eq(i).attr('data-page') == j){
                $('#tooltipArea').children().eq(i).show();
            }else{
                $('#tooltipArea').children().eq(i).hide();
            }
        }

        $('.modal_image_upload_content').css({
            "background-image": "url(" + window.URL.createObjectURL(images[j]) + ")",
            "outline": "none",
            "background-size": "contain",
            "background-repeat" : "no-repeat",
            "background-position" : "center"
        });
        console.log("j"+j);
        $('.afterBtnDrop').css({"visibility":"visible"});
    });

    $('#more_view').on('click', function(){
        $('.more_logout').toggleClass('on');
        $('.more_my').toggleClass('on');
    });

    //게시글 업로드 버튼 클릭시
    $(document).on('click', '#button_write_feed', ()=>{
      let inputFile = $('#file_upload');
      let files = inputFile[0].files;

      console.log(files);

      let dataTransfer = new DataTransfer();
      let fileArray = Array.from(files);
      console.log('fileArray : ' +fileArray);

      fileArray.forEach(f => {
          dataTransfer.items.add(f);
      });

      let images= dataTransfer.files;
      let urls = [];
      for(i=0; i<images.length; i++){
        let url = window.URL.createObjectURL(images[i]);
        urls.push(url);
      }
      console.log('urls : ' +urls);

      let content = $('#input_content').val();
      let user_no = $('#input_user_id').data('no');
      console.log('content : '+content);
      console.log('user_no : '+user_no);

      let length = $('.tag1').length;
      let users = [];
      let tops = [];
      let lefts = [];
      let pages = [];

      for(i=0; i<length; i++){
          users.push($('#tooltipArea').children().eq(i).attr('data-no'));
          console.log("offsetx top : "+$('#tooltipArea').children().eq(i).position().top);
          console.log("offsetx left : "+$('#tooltipArea').children().eq(i).position().left);
          tops.push(Math.ceil($('#tooltipArea').children().eq(i).position().top));
          lefts.push(Math.ceil($('#tooltipArea').children().eq(i).position().left));
          pages.push($('#tooltipArea').children().eq(i).attr('data-page'));
      }

      console.log('user_no : '+users);
      console.log('tops : '+tops);
      console.log('lefts : '+lefts);
      console.log('pages : '+pages);

      let formData = new FormData();
      for(j=0; j<images.length; j++){
        formData.append('files', files[j]);
      }
      formData.append('content', content);
      formData.append('user_no', user_no);
      formData.append('tags', users);
      formData.append('tops', tops);
      formData.append('lefts', lefts);
      formData.append('pages', pages);

      $.ajax({
        url:'/Photostagram/postUpload',
        processData: false,
        contentType: false,
        method:'POST',
        data:formData,
        dataType:'json',
        success:function(data){
          if(data.result > 0){
            //alert('업로드되었습니다.');
            $('#modal_add_feed_content').css({display : 'none'});
            $('#input_content').val('');
            $('#tooltipArea').empty();
            location.href='/Photostagram/index';
          }
        }
      });
    });

    // 게시물 업로드
    $(document).on('click', '#button_write_feed_drop', function(){
        let content = $('#input_content_drop').val();
        let user_id = $('#input_user_id_drop').data('no');
        let file = files;

        console.log('file_formData : '+file[0]);
        console.log('file_formData : '+file[1]);

        let fd = new FormData();

        let length = $('.tag1').length;
        let users = [];

        for(i=0; i<length; i++){
            users.push($('#tooltipArea').children().eq(i).attr('data-no'));
        }

        for(j=0; j<file.length; j++){
          fd.append('files', file[j]);
        }
        fd.append('content', content);
        fd.append('user_no', user_id);
        fd.append('tags', users);

        $.ajax({
            url:'/Photostagram/postUpload',
            processData: false,
            contentType: false,
            method:'POST',
            data:fd,
            dataType:'json',
            success:function(data){
              if(data.result > 0){
                alert('업로드되었습니다.');
                $('#modal_add_feed_content').css({display : 'none'});
              }
            }
        });
    });

    // 검색 버튼 클릭시
    $('#searchBtn').on('click', function(){
        let searchItem = $('#searchAll').val();
        let user_no = $('input[name=user_no]').val();
        console.log('user_no : '+user_no);

        let isHashTag = /(#[^\s#]+)/g;
        if(isHashTag.test(searchItem)){
            // 검색 아이템이 해시태그일 경우
            console.log('true');
            let cate = 2;
            let content = searchItem.substring(1);
            console.log(content);

            let jsonData = {"user_no":user_no, "searchItem":content, "cate":cate};

            $.ajax({
                url:'/Photostagram/searchHashtag',
                method:'POST',
                data:jsonData,
                dataType:'json',
                success:function(data){
                    if(data.result.length > 0){
                        let r = data.result;
                        console.log(r);
                        $('#searchListRecent').hide();
                        $('.searchListAll').empty();
                        r.forEach(function(i){
                            let searchResult = "<div class='searchlist' data-no='"+i.no+"'>";
                                searchResult += "<a>";
                                searchResult += "<div><img src=./img/hashtag.PNG></div>";
                                searchResult += "<div>";
                                searchResult += "<div><h3>#"+i.hashtag+"</h3></div>";
                                searchResult += "<div><h8>게시물"+i.countPost+"개</h8></div>";
                                searchResult += "</div>";
                                searchResult += "</a>";
                                searchResult += "</div>";

                            $('.searchListAll').append(searchResult);
                            $('.searchListAll').trigger("create");
                        });

                        $('.searchlist > a').on('click', function(e){
                            e.preventDefault();
                            let list = $(this).closest('div');
                            let dataNo = list.attr('data-no');
                            console.log('dataNo : '+dataNo);

                            let jsonData = {
                                "user_no":user_no,
                                "searchItem":content,
                                "cate":cate,
                                "searchResult":dataNo
                            };

                            console.log(jsonData);

                            $.ajax({
                                url:'/Photostagram/insertSearchResult',
                                method:'POST',
                                data:jsonData,
                                dataType:'json',
                                success:function(data){
                                    //alert('입력성공!');
                                    location.href='/Photostagram/search?no='+dataNo;
                                }
                            });
                        });
                    }else{
                        alert("검색 결과가 없습니다.");
                        return;
                    }
                }
            });
        }else{
            // 검색 아이템이 계정일 경우
            console.log('false');
            let cate = 1;
            let jsonData = {"user_no":user_no, "searchItem":searchItem, "cate":cate};

            $.ajax({
                url:'/Photostagram/searchUser',
                method:'POST',
                data:jsonData,
                dataType:'json',
                success:function(data){
                    console.log('성공!');
                    if(data.result.length > 0){
                        let r = data.result;
                        console.log(r);
                        $('#searchListRecent').hide();
                        $('.searchListAll').empty();
                        r.forEach(function(i){
                            let searchResult = "<div class='searchlist' data-username='"+i.username+"' data-no='"+i.no+"'>";
                                searchResult += "<a href='/Photostagram/profile?username="+i.username+"'>";
                                if(i.profileImg != null){
                                    searchResult += "<div><img src=/Photostagram/thumb/"+i.profileImg+"></div>";
                                }else{
                                    searchResult += "<div><img src=./img/44884218_345707102882519_2446069589734326272_n.jpg></div>";
                                }
                                searchResult += "<div>";
                                searchResult += "<div><h3 class='username'>"+i.username+"</h3></div>";
                                if(i.profileText != null){
                                    searchResult += "<div><h8>"+i.profileText+"</h8></div>";
                                }else{
                                    searchResult += "<div><h8></h8></div>";
                                }
                                searchResult += "</div>";
                                searchResult += "</a>";
                                searchResult += "</div>";

                            $('.searchListAll').append(searchResult);
                            $('.searchListAll').trigger("create");
                        });

                        $('.searchlist > a').on('click', function(e){
                            e.preventDefault();
                            let list = $(this).closest('div');
                            let dataNo = list.attr('data-no');
                            let username = list.attr('data-username');
                            console.log('dataNo : '+dataNo);

                            let jsonData = {
                                "user_no":user_no,
                                "searchItem":searchItem,
                                "cate":cate,
                                "searchResult":dataNo
                            };

                            console.log(jsonData);

                            $.ajax({
                                url:'/Photostagram/insertSearchResult',
                                method:'POST',
                                data:jsonData,
                                dataType:'json',
                                success:function(data){
                                    //alert('입력성공!');
                                    location.href='/Photostagram/profile?username='+username;
                                }
                            });
                        });
                    }else{
                        alert("검색 결과가 없습니다.");
                        return;
                    }
                }
            });
        }
    });

    // 검색 삭제 버튼 클릭시
    $('.searchDelete').on('click', function(){
        let searchNoDiv = $(this).closest('div');
        let searchNo = searchNoDiv.attr('data-searchNo');
        let searchList = $(this).parent().parent();
        console.log('searchNo : '+searchNo);

        let jsonData = {"searchNo":searchNo};

        $.ajax({
            url:'/Photostagram/deleteSearch',
            method:'POST',
            data:jsonData,
            dataType:'json',
            success:function(data){
                if(data.result > 0){
                    console.log(searchList);
                    searchList.remove();
                }
            }
        });
    });

    $('#deleteSearchAll').on('click', function(){
        let user_no = $('input[name=user_no]').val();

        let jsonData = {"user_no":user_no};

        $.ajax({
            url:'/Photostagram/deleteSearchAll',
            method:'POST',
            data:jsonData,
            dataType:'json',
            success:function(data){
                $('.searchListAll').hide();
            }
        });
    });

    $('.followBtn').on('click', function(){
        let btn = $(this);
        let div = $(this).closest('div');
        let user_no = div.attr('data-userNO');
        let my_no = div.attr('data-myNo');
        let status = div.attr('data-status');

        btn.children('.snipper').show();

        let jsonData = {"user_no":user_no, "my_no":my_no};
        console.log(jsonData);

        if(status == '0'){
            setTimeout(function(){
                $.ajax({
                    url:'/Photostagram/insertFollow',
                    method:'POST',
                    data:jsonData,
                    dataType:'json',
                    success:function(data){
                        btn.css({display : 'none'});
                        btn.children('.snipper').hide();
                        btn.parent().children('.followingBtn').show();
                    }
                });
            }, 1000);
        }
    });

    $('.followingBtn').on('click', function(){
        let btn = $(this);
        let div = $(this).closest('div');
        let user_no = div.attr('data-userNO');
        let my_no = div.attr('data-myNo');
        let status = div.attr('data-status');

        btn.children('.spinner').show();

        let jsonData = {"user_no":user_no, "my_no":my_no};
        console.log(jsonData);

        if(status == '1'){
            setTimeout(function(){
                $.ajax({
                    url:'/Photostagram/deleteFollow',
                    method:'POST',
                    data:jsonData,
                    dataType:'json',
                    success:function(data){
                        btn.css({display : 'none'});
                        btn.children('.snipper').hide();
                        btn.parent().children('.followBtn').show();
                    }
                });
            }, 1000);
        }
    });

    let x = 0;
    let y = 0;

    $('#tooltipArea').on('click', function(e){

        console.log('0- target id : ' + e.target.id);
        console.log('1- target id : ' + typeof e.target.id);

        console.log('2- target id : ' + e.target.id == 'beforeBtn');
        console.log('3- target id : ' + e.target.id == 'afterBtn');

        if(e.target.id != 'afterBtn' && e.target.id != 'beforeBtn'){
            x = e.offsetX;
            y = e.offsetY;

            console.log('------------------');
            console.log('x 좌표 : '+x);
            console.log('x 좌표 : '+y);

            $('.tagModal').css({left:x-18, top:y, display:'block'});
        }



    });

    $('input[name=tagUser]').on('keyup', function(){
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
                    $('.tagUserList').empty();
                    let tagUsers = data.result;
                    console.log(tagUsers);
                    tagUsers.forEach(function(tagUser){
                        let tags = "<div class='tagSearches'>";
                            tags += "<div class='tagSearchUser' data-userno="+tagUser.no+" data-username="+tagUser.username+">";
                            if(tagUser.profileImg != null){
                                tags += "<img src='/Photostagram/thumb/"+tagUser.profileImg+"'>";
                            }else{
                                tags += "<img src='/Photostagram/img/44884218_345707102882519_2446069589734326272_n.jpg'>";
                            }
                            tags += "<div class='tagUserInfo'>";
                            tags += "<div>"+tagUser.username+"</div>";
                            if(tagUser.profileText != null){
                                tags += "<span>"+tagUser.profileText+"</span>";
                            }else{
                               tags += "<span></span>";
                            }
                            tags += "</div>";
                            tags += "</div>";
                            tags += "</div>";

                        $('.tagUserList').append(tags);
                    });
                }
            });
        }, 1000);
    });

    $(document).on('click', '.tagSearches', function(){
        let select = $(this).children('.tagSearchUser');
        let user_no = select.attr('data-userno');
        let username = select.attr('data-username');

        console.log('username : '+username);

        $('.tagSearches').empty();
        let val = $('input[name=tagUser]').val();
        val = '';
        $('.tagModal').hide();

        let length = $('.tag1').length;

        for(i=0; i<length; i++){
            let no = $('#tooltipArea').children().eq(i).attr('data-no');
            if(no == user_no){
                let tagObject = $('#tooltipArea').children().eq(i);
                tagObject.css({
                    left:x-18,
                    top:$(window).scrollTop()+y,
                    display:'block'
                });
                return;
            }
        }

        let tag = "<div class='tag1' data-no="+user_no+" data-page="+j+" data-top="+y+" data-left="+x-18+">";
            tag += "<div></div>";
            tag += "<div class='username'><span>"+username+"</span><button>x</button></div>";
            tag += "</div>";

        let tagObj = $(tag);

        console.log("x좌표 : "+x);
        console.log("y좌표 : "+y);

        tagObj.css({
               left:x-18,
               top:$(window).scrollTop()+y,
               display:'block'
               });

        $('#tooltipArea').append(tagObj);
    });

    $(document).on('click', '.username > button', function(){
        $(this).parent().parent('.tag1').remove();
    });
  });