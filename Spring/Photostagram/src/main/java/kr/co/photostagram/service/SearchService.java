package kr.co.photostagram.service;

import kr.co.photostagram.dao.MainDAO;
import kr.co.photostagram.dao.SearchDAO;
import kr.co.photostagram.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchDAO dao;

    @Autowired
    private MainDAO mainDAO;

    @Transactional
    public List<PostVO> selectPostGroupbyHashTag(int hashtag_no){
        int postnums[] = dao.selectPostNums(hashtag_no);
        List<PostVO> posts = new ArrayList<>();
        for(int num : postnums){
            PostVO vo = dao.selectPostsByHashTag(num);
            posts.add(vo);
        }
        return posts;
    }

    public int selectCountHashTag(int no){
        return mainDAO.selectCountHashTag(no);
    }

    public String selectHashTagName(int no){
        return dao.selectHashTagName(no);
    }

    public List<PostVO> selectPostsByLike(int no){
        return dao.selectPostsByLike(no);
    }

    public int searchHashFollow(int tagNo, int userNo) {return dao.searchHashFollow(tagNo, userNo);}
    public int insertHashFollow(int tagNo, int userNo) {return dao.insertHashFollow(tagNo, userNo);}
    public int deleteHashFollow(int tagNo, int userNo) {return dao.deleteHashFollow(tagNo, userNo);}
}
