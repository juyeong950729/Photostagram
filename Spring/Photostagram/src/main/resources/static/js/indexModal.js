$(function () {
  // 댓글 모두보기
  $(".commentMore").on("click", function () {
    let article = $(this).closest("article");
    let modalPost = article.find(".modal-post");
    let imgsection = article.find(".img_section");
    let mainPostBx = imgsection.find(".bx-controls-direction");
    let modalPostBx = modalPost.find(".bx-controls-direction");

    modalPost.css("display", "flex");
    mainPostBx.css("display", "none");
    modalPostBx.css("display", "flex");
    $("body").css("overflow-y", "hidden");
  });

  // 버블 아이콘
  $(".sprite_bubble_icon").on("click", function () {
    let article = $(this).closest("article");
    let modalPost = article.find(".modal-post");
    let imgsection = article.find(".img_section");
    let mainPostBx = imgsection.find(".bx-controls-direction");
    let modalPostBx = modalPost.find(".bx-controls-direction");

    modalPost.css("display", "flex");
    mainPostBx.css("display", "none");
    modalPostBx.css("display", "flex");
    $("body").css("overflow-y", "hidden");
  });

  // 모달 post 닫기
  $(".post-close").on("click", function () {
    let article = $(this).closest("article");
    let modalPost = article.find(".modal-post");
    let bxControll = article.find(".bx-controls-direction");

    modalPost.css("display", "none");
    bxControll.css("display", "flex");
    $("body").css("overflow-y", "visible");
  });

  // 게시글 토글버튼
  $(".sprite_more_icon").on("click", function () {
    let article = $(this).closest("article");
    let PostMenu = article.find(".post-menu");
    let bxControll = article.find(".bx-controls-direction");

    PostMenu.css("display", " flex");
    bxControll.css("display", "none");
    $("body").css("overflow-y", "hidden");
  });

  // 게시글 토글버튼 취소클릭
  $(".postmenu-cancel").on("click", function () {
    let article = $(this).closest("article");
    let PostMenu = article.find(".post-menu");
    let bxControll = article.find(".bx-controls-direction");

    PostMenu.css("display", "none");
    bxControll.css("display", "flex");
    $("body").css("overflow-y", "visible");
  });

  // (1)댓글 Modal Open

  $(".comment_option, .modal_postDelete").on("click", function () {
    let rootTop = $(this).closest("div.top");
    let modalTop = $(this).closest(".modal-post");

    let postSelect = rootTop.find("#modal_postSelect");
    let modalbxSlider = modalTop.find(".bx-controls-direction");
    postSelect.show();
    modalbxSlider.hide();
  });

  //(1)에서 삭제버튼 클릭 시
  $(".modal_postDelete").click(function () {
    let rootTop = $(this).closest("div.top");
    rootTop.find("#modal_postSelect").hide();

    // (2)Confirm Modal Show
    rootTop.find("#modal_postSelect_delete").show();
  });

  // modal 취소 관련
  $(".modal_postCancel").on("click", function () {
    let rootTop = $(this).closest("div.top");
    let modalTop = $(this).closest(".modal-post");

    let postSelect = rootTop.find("#modal_postSelect");
    let modalbxSlider = modalTop.find(".bx-controls-direction");
    postSelect.hide();
    modalbxSlider.show();
  });

  $(".post_delete_cancel").on("click", function () {
    let rootTop = $(this).closest("div.top");
    let modalTop = $(this).closest(".modal-post");

    let postSelect_delete = rootTop.find("#modal_postSelect_delete");
    let modalbxSlider = modalTop.find(".bx-controls-direction");

    postSelect_delete.hide();
    modalbxSlider.show();
  });
});
