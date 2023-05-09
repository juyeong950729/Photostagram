package kr.co.photostagram.dao;

import kr.co.photostagram.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MainDAO {

    public int insertPost(PostVO vo);
    public int insertImage(ImageVO vo);

    public int findTagByContent(String hashtag);
    public int saveTag(HashTagVO vo);
    public int saveTagAndPost(Post_hashtagVO vo);
    public int selectHashTagNo(String hashtag);
    public List<HashTagVO> selectHashTag(String searchItem);
    public List<MemberVO> selectUser(String searchItem);
    public int insertSearchItem(SearchListVO vo);
    public int selectCountHashTag(int hashtag_no);
    public int selectSearchResult(@Param("user_no") int user_no, @Param("cate") int cate, @Param("searchResult") int searchResult);
    public List<SearchListVO> selectSearchItemRecentUser(int user_no);
    public int deleteSearch(int no);
    public int deleteSearchAll(int user_no);

    public List<NoticeVO> selectNotices(int user_no);
    public int selectFollowingStatus(@Param("my_no") int my_no, @Param("user_no") int user_no);
    public int insertFollow(@Param("my_no") int my_no, @Param("user_no") int user_no);
    public int deleteFollow(@Param("my_no") int my_no, @Param("user_no") int user_no);

    public List<MemberVO> selectTagUsers(String search);
    public int insertTagUser(@Param("post_no") int post_no, @Param("tag_no") int tag_no, @Param("top") int top, @Param("left") int left, @Param("page") int page);
}
