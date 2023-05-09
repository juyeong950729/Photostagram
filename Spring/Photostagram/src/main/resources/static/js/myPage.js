
/*** 선택 버튼으로 체크박스 열기 ***/
$(function(){
    const labels = $("label");
    $('.mySortSelect').on('click', function(){
        if (labels.hasClass("myCheckOff")){
            labels.removeClass("myCheckOff");
            labels.addClass("myCheckOn");
            $(".activityPop").show();
        } else {
            labels.removeClass("myCheckOn");
            labels.addClass("myCheckOff");
            $("input[name=photo]").prop("checked", false);
            $("#deleteCount").text("0");
            $(".activityPop").hide();
        }
    });
});

/*** 선택 갯수 카운트 ***/
function getCheckedCnt(){
  // 선택된 목록 가져오기
      const query = 'input[name="photo"]:checked';
      const selectedElements =
          document.querySelectorAll(query);

  // 선택된 목록의 갯수 세기
  const selectedElementsCnt =
        selectedElements.length;

  // 출력
  document.getElementById('deleteCount').innerText
    = selectedElementsCnt;
}

/*** ajax로 데이터 삭제 처리 ***/
$(function(){
    $('.mySortDelete').click(function(){
        let tags = $(this);
        let tagType = $(this).attr('data-type');
        let checkArray = [];
        let length = $('input:checked').length;
        //console.log ($('input:checked').length);

        $("input[type=checkbox]:checked").each(function(){
            let no = $(this).attr("data-value");
            checkArray.push(no);
        });

        console.log(checkArray);
        console.log(tagType);

        $.ajax({
            url: "/Photostagram/my/delete",
            type: "POST",
            traditional: true,
            data: { checkArray:checkArray, type:tagType },
            success: function(data){
                console.log("success");
                console.log(data.result);
                $("input[type=checkbox]:checked").parent().remove();
                $("input[type=checkbox]:checked").remove();
                $("label").removeClass("myCheckOn");
                $("label").addClass("myCheckOff");
                $(".activityPop").hide();
                alert('정상적으로 삭제되었습니다.');
            }
        });
    });
});


/*** 경과 시간 표시 ***/

$(function(){

    $(".myTimeStamp").each(function(){
        let times = getElapsedTime($(this).attr("data-value"));
        $(this).text(times);
    });

    function getElapsedTime (createdDateString){
        let now = moment();
        let createdDate = moment(createdDateString, 'YYYY-MM-DD HH:mm:ss');
        // 경과시간 정보
        let duration = moment.duration(now.diff(createdDate));
        let durationOptions = [
            {"dur" : duration.asYears(), "option" : "년 전"},
			{"dur" : duration.asMonths(), "option" : "개월 전"},
			{"dur" : duration.asWeeks(), "option" : "주 전"},
			{"dur" : duration.asDays(), "option" : "일 전"},
			{"dur" : duration.asHours(), "option" : "시간 전"},
			{"dur" : duration.asMinutes(), "option" : "분 전"},
        ];

        // 반복문으로 duration의 값을 확인해 어떤 단위로 반환할지 결정한다.
		// ex) 0.8년전이면 "8개월 전" 반환
		for (let durOption of durationOptions) {
			if (durOption.dur >= 1) {
				return Math.round(durOption.dur) + durOption.option;
			}
		}
		// 분 단위로 검사해도 1 이상이 아니면(반복문에서 함수가 종료되지 않으면) "방금 전" 반환
		return "방금 전"
    }
});


/*** 년 / 월별 동적으로 날짜 추가 ***/

$(function(){

    const yBtn1 = document.getElementById("startYearBox");
    const mBtn1 = document.getElementById("startMonthBox");
    const yBtn2 = document.getElementById("endYearBox");
    const mBtn2 = document.getElementById("endMonthBox");

    yBtn1.addEventListener("change", addStartDay);
    mBtn1.addEventListener("change", addStartDay);
    yBtn2.addEventListener("change", addEndDay);
    mBtn2.addEventListener("change", addEndDay);

    function addStartDay(){
        const day31 = []; const day30 = [];

        for (var i=1; i<=30; i++){
            day31.push(i); day30.push(i);
        }
        day31.push(31);

        //console.log (day31);
        //console.log (day30);

        let change;
        let month = $("#startMonthBox").val();
        let year = $("#startYearBox").val();

        switch (month) {
            case '1': case '3': case '5': case '7': case '8': case '10': case '12':
                change = day31;
                break;
            case '4': case '6': case '9': case '11':
                change = day30;
                break;
            case '2':
                change = day30;
                if (year % 4 == 0) {
                    change.pop();
                } else {
                    change.pop(); change.pop();
                }
        }

        console.log (change);

        $("#startDayBox").empty();
        let option;
        for (let i=1; i<=change.length; i++){
            option = $("<option>" + i + "</option>");
            $("#startDayBox").append(option);
        }
    }

    function addEndDay(){
        const day31 = []; const day30 = [];

        for (var i=1; i<=30; i++){
            day31.push(i); day30.push(i);
        }
        day31.push(31);

        //console.log (day31);
        //console.log (day30);

        let change;
        let month = $("#endMonthBox").val();
        let year = $("#endYearBox").val();

        switch (month) {
            case '1': case '3': case '5': case '7': case '8': case '10': case '12':
                change = day31;
                break;
            case '4': case '6': case '9': case '11':
                change = day30;
                break;
            case '2':
                change = day30;
                if (year % 4 == 0) {
                    change.pop();
                } else {
                    change.pop(); change.pop();
                }
        }

        console.log (change);

        $("#endDayBox").empty();
        let option;
        for (let i=1; i<=change.length; i++){
            option = $("<option>" + i + "</option>");
            $("#endDayBox").append(option);
        }
    }
});

/*** modal 최신순, 오래된순 ***/
$(function(){
    $("#recentBtn").on("click", function(){
        $("#recentBtn").addClass("buttonOn");
        $("#oldestBtn").removeClass("buttonOn");
    });
    $("#oldestBtn").on("click", function(){
        $("#oldestBtn").addClass("buttonOn");
        $("#recentBtn").removeClass("buttonOn");
    });
});