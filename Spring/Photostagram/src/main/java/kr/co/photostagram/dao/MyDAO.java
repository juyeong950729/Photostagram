package kr.co.photostagram.dao;

import kr.co.photostagram.vo.CommentVO;
import kr.co.photostagram.vo.HistoryVO;
import kr.co.photostagram.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 날짜 : 2023/04/05
 * 이름 : 조주영
 * 내용 : myDAO
 */

@Mapper
@Repository
public interface MyDAO {

    /*** History Insert ***/
    public int insertType (@Param("no") int no, @Param("type") String type);
    public int insertDetail (@Param("no") int no, @Param("type") String type, @Param("detail") String detail);

    public int selectUserRecent ();
    public PostVO selectPost (int no);
    public List<PostVO> selectPosts (int no);
    public int[] selectLikePostNo (int no);
    public int[] selectCommentNo (int no);
    public List<PostVO> selectMyCommentPosts (int no);
    public List<CommentVO> selectMyComments (@Param("postNo") int postNo, @Param("userNo") int userNo);
    public List<HistoryVO> selectHistory (int no);

    /*** 날짜 별 정렬 ***/
    public int[] sortSelectLikePostNo (@Param("no") int no, @Param("start") String start, @Param("end") String end, @Param("sort") String sort);
    public List<PostVO> sortSelectMyCommentPosts (@Param("no") int no, @Param("start") String start, @Param("end") String end, @Param("sort") String sort);
    public List<PostVO> sortSelectPosts (@Param("no") int no, @Param("start") String start, @Param("end") String end, @Param("sort") String sort);
    public List<HistoryVO> sortSelectHistory (@Param("no") int no, @Param("start") String start, @Param("end") String end, @Param("sort") String sort);

    public String selectJoinDate (int no);
    public int updateRemoveLike (int no);
    public int deleteComment (int no);
    public int deleteCommentLike (int no);
    public int deleteLike (@Param("postNo") int postNo, @Param("userNo") int userNo);
    public int deletePost (int no);

}
