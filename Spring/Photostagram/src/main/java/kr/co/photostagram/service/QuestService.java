package kr.co.photostagram.service;

import kr.co.photostagram.dao.QuestDAO;
import kr.co.photostagram.vo.ImageVO;
import kr.co.photostagram.vo.PostVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class QuestService {
    @Autowired
    private QuestDAO dao;

    public List<PostVO> selectAllImg() { return dao.selectAllImg();}
}
