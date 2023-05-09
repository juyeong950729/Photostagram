package kr.co.photostagram.dao;

import kr.co.photostagram.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SearchDAO {
    public int[] selectPostNums(int hashtag_no);
    public PostVO selectPostsByHashTag(int no);
    public String selectHashTagName(int no);
    public List<PostVO> selectPostsByLike(int no);

    public int searchHashFollow(@Param("tagNo") int tagNo, @Param("userNo") int userNo);

    public int insertHashFollow(@Param("tagNo") int tagNo, @Param("userNo") int userNo);
    public int deleteHashFollow(@Param("tagNo") int tagNo, @Param("userNo") int userNo);

}
