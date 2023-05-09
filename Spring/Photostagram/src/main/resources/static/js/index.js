/**
 *  날짜 : 2023 / 02 / 28
 *  이름 : 조광호
 */

$(function () {
  $(".bx_slider").bxSlider({
    infiniteLoop: false,
    height: 600,
    slideWidth: 600,
    speed: 200,
    moveSlides: 1,
  });

  //메인콘텐츠 더보기 누를 경우
  $(".contents").each(function () {
    let article = $(this).closest("article");
    let divCommentWord = article.find(".commentWord"); // 작성자가 쓴 내용
    let comment = divCommentWord.text().trim();
    let a_hashtag_text = article.find(".hashtag_text"); // 해시태그
    let comment_short = comment.substring(0, 10) + "...";
    let btnMore = $(
      '<a href="javascript:void(0)" class="ContentsMore">더보기</a>'
    );

    article.find(".comment").append(btnMore);

    if (comment.length >= 10) {
      divCommentWord.html(comment_short);
      a_hashtag_text.hide();
    } else {
      btnMore.hide();
    }

    btnMore.click(toggle_content);

    function toggle_content() {
      if (article.find(".ContentsMore").hasClass("short")) {
        // 접혀있는 상태(평상 시)
        article.find(".ContentsMore").html("더보기");
        divCommentWord.html(comment_short);
        a_hashtag_text.hide();
        article.find(".ContentsMore").removeClass("short");
      } else {
        article.find(".ContentsMore").html("접기");
        divCommentWord.html(comment);
        a_hashtag_text.show();
        article.find(".ContentsMore").addClass("short");
      }
    }
  });

  /** 댓글 작성 **/
  $(document).on("click", ".upload_btn", function () {
    let article = $(this).closest("article");
    let modal_cmt = $(this).closest(".posting");
    let modal_cmt_no = modal_cmt.children();

    let div = $(this).parent();
    let input = div.children();

    let uid = input.eq(0).val();
    let post_no = input.eq(1).val();
    let user_no = input.eq(2).val();
    let comment = input.eq(3).val();
    let image = $("#myProfile").children().children().attr("src");

    // 댓글쓰는게 답글이면
    if (comment.startsWith("@")) {
      console.log(uid);
      console.log("답글 내용입니다 : " + comment);
      console.log("게시글 번호 입니다 : " + post_no);
      console.log("유저 번호 입니다 : " + user_no);

      let respComment_No = $("input[name=commentNo]").last().val();
      console.log("답글 달 댓글 번호입니다 : " + respComment_No);

      let respComment = comment.split(" | ");
      console.log(respComment[0]); // 피 답글자 아이디
      console.log(respComment[1]); // 해당 답글의 내용

      let jsonData = {
        comment: respComment[1],
        post_no: post_no,
        user_no: user_no,
        parent: respComment_No,
      };

      let top_length = $("div.top").length;

      $.ajax({
        url: "/Photostagram/respCmtRegister",
        method: "POST",
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
          if (data.result > 0) {
            let str = "<div class='resp_comment_section'>";
            str += "<ul>";
            str += "<li>";
            str += "<img src='" + image + "' alt='프로필이미지'>";
            str +=
              "<div data-no='" +
              data.no +
              "' style='display: inline-block; margin-left: 2px;'>";
            str +=
              "<a class='modal_comment_id' href='/Photostagram/profile?username=" +
              uid +
              "'>" +
              uid +
              "</a>";

            str +=
              "<a href='/Photostagram/profile?username=" +
              respComment[0].substring(1) +
              "' class='resp_id' style='color:#00376B; font-size:13px; font-weight:bold; margin-left: 4px;'>" +
              respComment[0] +
              "</a>&nbsp;";

            str += "<span class='modal_comment'>" + respComment[1] + "</span>";
            str +=
              "<div class='comLike sprite_small_heart_icon_outline' data-no='" +
              user_no +
              "'></div>";

            str += "<div class='commentInfo'>";
            str += "<span>1일</span>&nbsp;&nbsp;좋아요";
            str += "<span id='md_comment_likeCount'>0</span>개&nbsp;";
            str += "<span class='resp_comment'>답글달기</span>";
            str += "</div>";
            str += "</div>";
            str += "</li>";
            str += "</ul>";
            str += "</div>";

            for (let i = 0; i < top_length; i++) {
              let top_cmt = $("div.top").eq(i);

              let value = $("div.top").eq(i).attr("data-value");
              console.log("value : " + value);

              if (top_cmt.attr("data-value") == respComment_No) {
                top_cmt.append(str);
              }
            }

            $(".modal_commentText").val("");
          }
        },
      });
    } else {
      // 답글X 일반댓글
      let jsonData = {
        uid: uid,
        post_no: post_no,
        user_no: user_no,
        comment: comment,
      };
      $.ajax({
        url: "/Photostagram/CmtRegister",
        method: "POST",
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
          if (data.result > 0) {
            let modal_comment =
              "<div class='top' data-value='" + data.no + "'>";
            modal_comment += "<img src='" + image + "' alt='프로필이미지'>";
            modal_comment += "<div class='posting'>";
            modal_comment += "<div data-no='" + data.no + "'>";
            modal_comment +=
              "<a class='modal_comment_id' href='/Photostagram/profile?username=" +
              uid +
              "'>" +
              uid +
              "</a>";
            modal_comment += "<input type='hidden' value='" + data.no + "'>";
            modal_comment +=
              "<span class='modal_comment' style='margin-left:2px;'>" +
              comment +
              "</span>";
            modal_comment +=
              "<div class='comLike sprite_small_heart_icon_outline' data-no='" +
              user_no +
              "'></div>";
            modal_comment += "<div class='commentInfo'>";
            modal_comment += "<span>1일</span>&nbsp;&nbsp;좋아요&nbsp;";
            modal_comment +=
              "<span id='md_comment_likeCount'>0</span>개&nbsp;&nbsp;";
            modal_comment += "<span class='resp_comment'>답글달기</span>";
            modal_comment += "<button class='comment_option' style='display:inline'></button>"
            modal_comment += "</div>";
            modal_comment += "</div>";
            modal_comment += "</div>";
            modal_comment += "</div>";

            article.find(".text").append(modal_comment);

            let str = "<div class='reply_user' data-no='" + data.no + "'>";
            str +=
              "<input type='hidden' class='reply_no' value='" + data.no + "'>";
            str += "<span class='reply_nick'>" + uid + "</span>";
            str +=
              "<span class='reply_content' style='margin-left:4px;'>" +
              comment +
              "</span>";
            str +=
              "<div class='comLike sprite_small_heart_icon_outline' data-no='" +
              data.user_no +
              "'></div>";
            str += "</div>";

            article.find(".comment_container").append(str);
            article.find(".commentText").val("");
            article.find(".modal_commentText").val("");

            let count = article.find("#comment-count").text(); // 현재 태그사이 텍스트받고
            let commentCount = parseInt(count); // 문자열이라 더하기가 안되기때문에 parseInt
            article.find("#comment-count").text(commentCount + 1); // 해당 텍스트에 +1
          } else {
            alert("실패");
          }
        },
      });
    }
  });

  /** Modal창 [답글달기] 클릭 이벤트**/
  $(".resp_comment").on("click", function () {
    // 피 답글 닉네임
    let idtext = $(this).parent().parent().children(":first").text();
    let posting = $(this).closest(".posting");
    // 댓글 번호
    let modal_comment_no = posting.children().attr("data-no");

    // 댓글번호 동적 생성
    let dynamicCommentNo =
      "<input type='hidden' name='commentNo' value='" + modal_comment_no + "'>";
    $(".comment-wrapper").append(dynamicCommentNo);

    // console.log("해당 댓글 번호 : " + modal_comment_no);

    let id = "@" + idtext;

    // textarea @아이디 생성
    $(".modal_commentText").val(id + " | ");
  });

  /** 게시글 좋아요 **/
  $(document).on("click", ".sprite_heart_icon_outline", function (e) {
    e.preventDefault();
    let article = $(this).closest("article");
    let post_no = article.attr("data-no"); // 게시글 번호
    let user_no = $(this).attr("data-no"); // 유저 번호
    let url = "/Photostagram/ArticleLikeAdd";

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
      success: (data) => {
        if (data.result > 0) {
          if (article.find(".artLike").hasClass("sprite_heart_icon_outline")) {
            article
              .find(".artLike")
              .removeClass("sprite_heart_icon_outline")
              .addClass("sprite_full_heart_icon_outline");

            let textCount = article.find(".like-count").text();
            let count = parseInt(textCount);

            article.find(".like-count").text(count + 1);
            article.find(".modal-like-count").text(count + 1);
          }
        }
      },
    });
  });

  /** 게시글 좋아요(2) **/
  $(document).on("click", ".sprite_full_heart_icon_outline", function (e) {
    e.preventDefault();

    let article = $(this).closest("article");
    let post_no = article.attr("data-no"); // 게시글 번호
    let user_no = $(this).attr("data-no"); // 유저 번호
    let url = "/Photostagram/DeleteArticleLike";

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
      success: (data) => {
        if (data.result > 0) {
          if (
            article.find(".artLike").hasClass("sprite_full_heart_icon_outline")
          ) {
            article
              .find(".artLike")
              .removeClass("sprite_full_heart_icon_outline")
              .addClass("sprite_heart_icon_outline");

            let textCount = article.find(".like-count").text();
            let count = parseInt(textCount);

            article.find(".like-count").text(count - 1);
            article.find(".modal-like-count").text(count - 1);
          }
        }
      },
    });
  });

  /** 댓글 좋아요 **/
  $(document).on("click", ".sprite_small_heart_icon_outline", function (e) {
    e.preventDefault();

    let article = $(this).closest("article");
    let modalPost = $(this).closest(".modal-post");
    let user_no = $(this).attr("data-no"); // 유저 번호
    let comment_no = article.find($(this).parent()).attr("data-no"); // 댓글번호
    let url = "/Photostagram/CommentLikeAdd";

    console.log("user_no : " + user_no);
    console.log("comment_no : " + comment_no);

    let jsonData = {
      user_no: user_no,
      comment_no: comment_no,
    };

    $.ajax({
      url: url,
      method: "POST",
      data: JSON.stringify(jsonData),
      contentType: "application/json",
      dataType: "json",
      success: (data) => {
        if (data.result > 0) {
          if (
            article.find(".comLike").hasClass("sprite_small_heart_icon_outline")
          ) {
            // 게시글 댓글 쪽 좋아요
            article
              .find($(this))
              .removeClass("sprite_small_heart_icon_outline")
              .addClass("sprite_full_small_heart_icon_outline");

            // modal 댓글 좋아요 개수 올리기
            let likeCount = $(this)
              .next()
              .children("#md_comment_likeCount")
              .text();
            let count = parseInt(likeCount);

            $(this)
              .next()
              .children("#md_comment_likeCount")
              .text(count + 1);
          }
        }
      },
    });
  });
  /** 댓글 좋아요(2) **/
  $(document).on(
    "click",
    ".sprite_full_small_heart_icon_outline",
    function (e) {
      e.preventDefault();

      let article = $(this).closest("article");
      let user_no = $(this).attr("data-no"); // 유저 번호
      let comment_no = article.find($(this).parent()).attr("data-no"); // 댓글번호
      let url = "/Photostagram/CommentLikeDel";

      console.log("user_no : " + user_no);
      console.log("comment_no : " + comment_no);

      let jsonData = {
        user_no: user_no,
        comment_no: comment_no,
      };

      $.ajax({
        url: url,
        method: "POST",
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        dataType: "json",
        success: (data) => {
          if (data.result > 0) {
            if (
              article
                .find($(this))
                .hasClass("sprite_full_small_heart_icon_outline")
            ) {
              article
                .find($(this))
                .removeClass("sprite_full_small_heart_icon_outline")
                .addClass("sprite_small_heart_icon_outline");

              let likeCount = $(this)
                .next()
                .children("#md_comment_likeCount")
                .text();
              let count = parseInt(likeCount);

              $(this)
                .next()
                .children("#md_comment_likeCount")
                .text(count - 1);
            }
          }
        },
      });
    }
  );

  $(document).on("click", ".idx_followBtn", function () {
    let body = $(this).closest("body");
    let following = $(this).attr("data-no");

    let jsonData = { following: following };

    $.ajax({
      url: "/Photostagram/follow",
      method: "GET",
      data: jsonData,
      dataType: "json",
      success: (data) => {
        if (data.result > 0) {
          if (body.find(".idxfw").hasClass("idx_followBtn")) {
            body
              .find($(this))
              .removeClass("idx_followBtn")
              .addClass("idx_followingBtn");

            $(this).val("팔로잉");
          }
        }
      },
    });
  });

  // 답글 보기 / 펼치기
  $(".respCommentOpen").click(function (e) {
    e.preventDefault();

    let items = $(this).parent().find("ul");

    if (items.is(":visible")) {
      items.hide();
      items.prev().text("―― 답글 보기");
    } else {
      items.show();
      items.prev().text("―― 답글 숨기기");
    }
  });

  // 댓글 옵션 창 hover
  $(".posting").on({
    mouseover: function () {
      let options = $(this).find(".comment_option");
      options.css({ display: "inline" });
    },
    mouseout: function () {
      let options = $(this).find(".comment_option");
      options.css({ display: "none" });
    },
  });

  // 답글 hover
  $(".resp_comment_section > ul li").on({
    mouseover: function () {
      let options = $(this).find(".comment_option");
      options.css({ display: "inline" });
    },
    mouseout: function () {
      let options = $(this).find(".comment_option");
      options.css({ display: "none" });
    },
  });

  /*
    북마크 (default : -237px -286px, click : -159px -286px)
  */
  $(document).on("click", ".sprite_bookmark_outline", function () {
    if ($(this).hasClass("sprite_bookmark_outline")) {
      $(this)
        .removeClass("sprite_bookmark_outline")
        .addClass("sprite_full_bookmark_outline");
    }

    let value = $(this).attr("data-value");
    let article = $(this).closest("article");
    let post_no = article.attr("data-no");

    let jsonData = {
      user_no: value,
      post_no: post_no,
    };
    $.ajax({
      url: "/Photostagram/insertBook",
      method: "POST",
      data: JSON.stringify(jsonData),
      contentType: "application/json",
      dataType: "json",
      success: function (data) {
        if (data.result > 0) {
          console.log(data);
        }
      },
    });
  });

  $(document).on("click", ".sprite_full_bookmark_outline", function () {
    if ($(this).hasClass("sprite_full_bookmark_outline")) {
      $(this)
        .removeClass("sprite_full_bookmark_outline")
        .addClass("sprite_bookmark_outline");
    }

    let value = $(this).attr("data-value");
    let article = $(this).closest("article");
    let post_no = article.attr("data-no");

    console.log(" value 클릭한 후: " + value);
    console.log(" post_no 클릭한 후 : " + post_no);

    let jsonData = {
      user_no: value,
      post_no: post_no,
    };

    $.ajax({
      url: "/Photostagram/deleteBook",
      method: "POST",
      data: JSON.stringify(jsonData),
      contentType: "application/json",
      dataType: "json",
      success: function (data) {
        if (data.result > 0) {
          console.log(data);
        }
      },
    });
  });

  // 댓글삭제
  $(".post_delete > span").on("click", function () {
    let deleteModal = $(this).parent().parent().parent().parent();
    let modalTop = $(this).closest(".modal-post");
    let modalbxSlider = modalTop.find(".bx-controls-direction");
    let top = $(this).closest("div.top");

    let no = $(this).attr("data-value");

    let jsonData = { comment_no: no };
    console.log("jsonData" + jsonData);

    $.ajax({
      url: "/Photostagram/deleteComment",
      method: "GET",
      data: jsonData,
      dataType: "json",
      success: function (data) {
        console.log(data);
        if (data.result == 0 || data.result == 1) {
          alert("댓글 삭제에 성공했습니다!");
          deleteModal.hide();
          top.hide();
        }
      },
    });
  });
});
