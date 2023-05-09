package kr.co.photostagram.vo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostVO {

    private int no;
    private String content;
    private int user_no;
    private int like;
    private String rdate;
    private List<MultipartFile> files;
    // 추가필드
    private int commentCount;
    private List<CommentVO> comments;

    private String firstThumb;
    private int thumbs;
    private int hashtags;
    private String username;
    private String profileImg;

    private Post_likeVO post_likeVO;
    private List<ImageVO> imageVO;
    private MemberVO memberVO;
    private List<HashTagVO> hashTagVO;

    private String thumb;
    private List<Integer> tags;
    private List<Integer> tops;
    private List<Integer> lefts;
    private List<Integer> pages;

    private Post_saveVO post_saveVO;
    private int post_no;

}
