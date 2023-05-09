package kr.co.photostagram.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchListVO {
    private int no;
    private int user_no;
    private String searchItem;
    private String rdate;
    private int cate;
    private int searchResult;
    private String result;
    private String img;
    private String text;

    // 추가 필드(검색 결과 요청시)
    private String username;
    private String profileImg;
    private String profileText;
    private String hashtag;
    private int hashtag_post_count;
}
