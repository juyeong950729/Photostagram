package kr.co.photostagram.vo;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CommentVO {
    private int no;
    private String comment;
    private int post_no;
    private int user_no;
    private int parent;
    private String rdate;

    private int modal_likeCount;
    private MemberVO memberVO;
    private Comment_likeVO comment_likeVO;

    // 추가필드
    private String username;
    private String profileImg;
    private List<CommentVO> childComment;
    private String content;
    private String counts;


}
