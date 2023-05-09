package kr.co.photostagram.dao;

import kr.co.photostagram.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoardDAO {
   public int detailPostLikeAdd(PostVO vo);
   public int detailPostDelAdd(PostVO vo);
   // `post` like Update
   public void detailPostLikeUpdate(PostVO vo);
   public void detailPostDelLikeUpdate(PostVO vo);

   public int detailPostCommentLikeAdd(Comment_likeVO vo);
   public int detailPostCommentLikeDel(Comment_likeVO vo);

   public BoardVO selectMember(String username);
   public BoardVO selectPost(int no);
   public BoardVO selectContent(int no);

   Post_likeVO selectPostLike(@Param("no") int no, @Param("user_no") int user_no);

   public List<Board1VO> selectPostHashTag(int no);
   public List<CommentVO> selectcomments(int no);
   public List<ImageVO> selectimages(int no);
   public List<NoticeVO> selectNoticesTime(int no);

   public List<Board2VO> selectcommentlist(@Param("postNo") int postNo);

   public PostVO selectContentLikeTime(int no);
   public List<PostVO> selectPlusImg(int no);


   public int insertComment(CommentVO vo);
   public int insertRespComment(CommentVO vo);

   public List<CommentVO> selectComment(int post_no);
   public int deleteCommentLike(int comment_no);
   public int deletePost(int post_no);
   public int deletePosts(int post_no);

   public List<UserTagVO> selectUserTags(int post_no);
   public int deleteUserTags(int post_no);
   public int insertTagNewUser(@Param("post_no") int post_no, @Param("tag_no") int tag_no, @Param("top") int top, @Param("left") int left, @Param("page") int page);
   public int deleteHashes(int post_no);
   public int updatePost(@Param("content") String content, @Param("post_no") int post_no);

}
