$(function () {
  $(".mySlides").bxSlider({});



  $(document).on("click", ".comment_more", function () {
    // 피 답글 닉네임
    let idtext = $(this).parent().prev().children("span").text(); // ok
    let dynamicNo = $(this).parent().parent().next().attr("data-no"); // ok

    // 댓글번호 동적 생성
    let dynamicCommentNo =
      "<input type='hidden' name='commentNo' value='" + dynamicNo + "'>";

    $(".comment_textarea").append(dynamicCommentNo);

    let id = "@" + idtext;

    // textarea @아이디 생성
    $("#comment").val(id + " | ");
  });

  /*** 게시글 북마크 ***/
  $("#bookMark").click(function () {
    let user_no = $(this).attr("data-no");
    let article = $(this).closest("article");
    let post_no = article.attr("data-no");

    let jsonData = {
      user_no: user_no,
      post_no: post_no,
    };

    function ajaxShot(url) {
      $.ajax({
        url: url,
        method: "POST",
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
          console.log(data);
        },
      });
    }

    if ($("#bookMark").hasClass("remove")) {
      $("#bookMark").removeClass("remove").addClass("mark");
      ajaxShot("/Photostagram/insertBook");
    } else {
      $("#bookMark").removeClass("mark").addClass("remove");
      ajaxShot("/Photostagram/deleteBook");
    }
  });

  /** 댓글 좋아요 이미지변경 **/
  $(document).on("click", ".detail_comment_heart", function () {
    if ($(this).hasClass("reWhite")) {
      $(this).removeClass("reWhite").addClass("reRed");
    } else {
      $(this).removeClass("reRed").addClass("reWhite");
    }
  });

  /*** 댓글 좋아요 기능 ***/
  $(document).on("click", ".reWhite", function (e) {
    e.preventDefault();

    let article = $(this).closest("article");
    let user_no = $(this).attr("data-no");
    let comment_no = $(this).prev().attr("data-no");
    let url = "/Photostagram/detailCommentLike";

    let jsonData = {
      user_no: user_no,
      comment_no: comment_no,
    };
    let like_count = $(this).prev().prev().find(".cmt_like_count");
    let textCount = $(this).prev().prev().find(".cmt_like_count").text();

    $.ajax({
      url: url,
      method: "POST",
      data: JSON.stringify(jsonData),
      contentType: "application/json",
      dataType: "json",
      success: function (data) {
        if (data.result > 0) {
          let count = parseInt(textCount);

          like_count.text(count + 1);
        }
      },
    });

    console.log("user_no : " + user_no);
    console.log("comment_no : " + comment_no);
  });

  /*** 좋아요 된 상태 클릭시 ***/
  $(document).on("click", ".reRed", function (e) {
    e.preventDefault();

    let user_no = $(this).attr("data-no");
    let comment_no = $(this).prev().attr("data-no");
    let url = "/Photostagram/detailCommentLikeDel";

    let jsonData = {
      user_no: user_no,
      comment_no: comment_no,
    };
    let like_count = $(this).prev().prev().find(".cmt_like_count");
    let textCount = $(this).prev().prev().find(".cmt_like_count").text();

    $.ajax({
      url: url,
      method: "POST",
      data: JSON.stringify(jsonData),
      contentType: "application/json",
      dataType: "json",
      success: function (data) {
        if (data.result > 0) {
          let count = parseInt(textCount);

          like_count.text(count - 1);
        }
      },
    });
  });

  /*** 댓글 접기, 펴기 ***/
  $(".reOpen").click(function (e) {
    e.preventDefault();

    let items = $(this).parent().find(".reHide");

    if (items.is(":visible")) {
      items.hide();
    } else {
      items.show();
    }
  });

  /*** 게시글 좋아요 ***/
  let like = $("#likeThis");
  like.click(likeToggle);

  function likeToggle() {
    let user_no = $("#likeThis").attr("data-no");
    let post_no = $("#post").attr("data-no");

    if ($("#likeThis").hasClass("unlike")) {
      $("#likeThis").removeClass("unlike").addClass("like");

      let url = "/Photostagram/detailPostLike";
      let jsonData = {
        no: post_no,
        user_no: user_no,
      };

      $.ajax({
        url: url,
        method: "POST",
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
          if (data.result > 0) {
            let textCount = $(".like-count").text();
            let count = parseInt(textCount);

            $(".like-count").text(count + 1);
          } else {
            alert("등록실패");
          }
        },
      });
    } else {
      $("#likeThis").removeClass("like").addClass("unlike");

      let url = "/Photostagram/detailPostDelLike";
      let jsonData = {
        no: post_no,
        user_no: user_no,
      };

      $.ajax({
        url: url,
        method: "POST",
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
          if (data.result > 0) {
            let textCount = $(".like-count").text();
            let count = parseInt(textCount);

            $(".like-count").text(count - 1);
          } else {
            alert("좋아요 취소 실패!");
          }
        },
      });
    }
  }
});

/*** 게시물 팔로잉 클릭 시 뜨는 모달 창 ***/
$(function () {
  var modal = document.getElementById("pCancel");
  var btn = document.getElementById("postFollowing");

  btn.onclick = function () {
    modal.style.display = "block";
  };

  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  };
});

/*** 사진 슬라이드 ***/
// var slideIndex = 1;
// showDivs(slideIndex);

// function plusDivs(n) {
//   showDivs((slideIndex += n));
// }

// function showDivs(n) {
//   var i;
//   var x = document.getElementsByClassName("mySlides");
//   if (n > x.length) {
//     slideIndex = 1;
//   }
//   if (n < 1) {
//     slideIndex = x.length;
//   }
//   for (i = 0; i < x.length; i++) {
//     x[i].style.display = "none";
//   }
//   x[slideIndex - 1].style.display = "block";
