package kr.co.photostagram.dao;

import kr.co.photostagram.vo.ImageVO;
import kr.co.photostagram.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestDAO {
    public List<PostVO> selectAllImg();
}
