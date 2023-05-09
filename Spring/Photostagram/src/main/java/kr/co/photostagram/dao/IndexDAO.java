package kr.co.photostagram.dao;

import kr.co.photostagram.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndexDAO {

    public int insertPostSave(Post_saveVO vo);

    // 게시글 좋아요, 좋아요 삭제
    public int insertArticleLikeAdd(PostVO vo);
    public int deleteArticleLike(PostVO vo);

    // `post` like Update
    public void postLikeAddUpdate(PostVO vo);
    public void postLikeDelUpdate(PostVO vo);

    // 댓글 좋아요, 좋아요 삭제
    public int insertCommentLikeAdd(Comment_likeVO vo);
    public int deleteCommentLike(Comment_likeVO vo);

    // 댓글 등록
    public void insertComment(CommentVO vo);
    // 답글 등록
    public void insertRespComment(CommentVO vo);

    public List<PostVO> selectArticles(@Param("usersNo") List<Integer> usersNo);
    public List<CommentVO> selectComment();

    public List<MemberVO> selectUser();
    public List<MemberVO> selectFollowing(int no);
    // 댓글 개수
    public int selectCommentCountNum(int post_no);

    // modal 댓글 좋아요 수
    public int selectModalCommentlikeCount(int comment_no);

    public int deleteComment(int comment_no);
    public int deleteBookmark(Post_saveVO vo);
}
