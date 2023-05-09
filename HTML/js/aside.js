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
    if (files.length > 1) {
        alert('하나만 올려라.');
        return;
    }

    if (files[0].type.match(/image.*/)) {
        $('#modal_add_feed_content').css({
            display : 'flex'
        });
        $('.modal_image_upload_content').css({
            "background-image": "url(" + window.URL.createObjectURL(files[0]) + ")",
            "outline": "none",
            "background-size": "contain",
            "background-repeat" : "no-repeat",
            "background-position" : "center"
        });
        $('#modal_add_feed').css({
            display: 'none'
        })
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

      $('#add_feed').on('click', function(){
          modal.css('top', $(window).scrollTop()+'px');
          modal.css('display', 'flex');
          $('body').css('overflow-y', 'hidden');
      });

      $('#close_modal').on('click', function(){
        modal.css('display', 'none');
        $('body').css('overflow-y', 'visible');
      });

      $('#close_modal_feed').on('click', function(){
        modal_feed.css('display', 'none');
        $('body').css('overflow-y', 'visible');
        sessionStorage.clear();
      });
      
      $('.modal_image_upload')
        .on("dragover", dragOver)
        .on("dragleave", dragOver)
        .on("drop", uploadFiles);

      $('#file_upload').on('change', function(){
          alert('file_upload!');

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
                        
          let url = [];
          for(i=0 ; i<images.length ; i++){
            let page = "url(" + window.URL.createObjectURL(images[i]) + ")";
            console.log("page : " + page);
            url.push(page);
          }

          console.log("url : " + url);
          
          let jsonData = {"url":url};
          sessionStorage.setItem("url", JSON.stringify(jsonData));
          //console.log("jsonData : "+JSON.stringify(jsonData));
                      

          if (images[0].type.match(/image.*/)) {
              $('#modal_add_feed_content').css({
                  display : 'flex'
              });
              $('.modal_image_upload_content').css({
                "background-image": "url(" + window.URL.createObjectURL(images[0]) + ")",
                "outline": "none",
                "background-size": "contain",
                "background-repeat" : "no-repeat",
                "background-position" : "center"
              });                  
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
        let file = sessionStorage.getItem("url");

        console.log(file);

        let jsonData = JSON.parse(file);
        let urls = jsonData.url;
        console.log(urls[0]);
        console.log(urls.length);

        if(j == urls.length)return;
        j++;

        $('.modal_image_upload_content').css({
            "background-image": jsonData.url[j],
            "outline": "none",
            "background-size": "contain",
            "background-repeat" : "no-repeat",
            "background-position" : "center"
        }); 
        console.log("j :"+j);
    });

    $('.beforeBtn').on('click', function(){
        let file = sessionStorage.getItem("url");

        console.log(file);

        let jsonData = JSON.parse(file);
        let urls = jsonData.url;
        console.log(urls[0]);
        console.log(urls.length);

        if(j == 0)return;
        j--;

        $('.modal_image_upload_content').css({
            "background-image": jsonData.url[j],
            "outline": "none",
            "background-size": "contain",
            "background-repeat" : "no-repeat",
            "background-position" : "center"
        }); 
        console.log("j"+j); 
        
    });

    $('#more_view').on('click', function(){
        $('.more_logout').toggleClass('on');
    });
  });