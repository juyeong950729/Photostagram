$(function(){
  let modal = $("#myModalId");
  let btn = $(".mySort");
  let close = $(".myModalClose");

  btn.click(function(e){
    e.preventDefault();
    modal.show();
  });

  close.click(function(e){
     e.preventDefault();
     modal.hide();
  });
});