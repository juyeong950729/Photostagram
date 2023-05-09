


$(function(){

    /*** 글자수 세기 ***/
    $('#textBox').keyup(function(e) {
        let content = $(this).val();

        if (content.length == 0 || content == '') {
            $('.textCount').text('0');
        } else {
            $('.textCount').text(content.length);
        }

        if (content.length > 150) {
            $(this).val($(this).val().substring(0, 150));
            alert('글자 수는 150자까지 입력 가능합니다.');
        };
    });


    /*** 스크롤 없이 늘어나기 ***/
    const textarea = document.querySelector('#textBox');

    const changeLength = () => {
    	textarea.current.style.height = 'auto';
    	textarea.current.style.height = textarea.current.scrollHeight + 'px';
    };

});
