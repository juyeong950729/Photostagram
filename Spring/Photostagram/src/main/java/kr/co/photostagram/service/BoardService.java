package kr.co.photostagram.service;

import kr.co.photostagram.dao.BoardDAO;
import kr.co.photostagram.dao.MainDAO;
import kr.co.photostagram.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class BoardService {

    @Autowired
    private BoardDAO dao;

    @Autowired
    private MainDAO mainDAO;

    public int detailPostLikeAdd(PostVO vo) { return dao.detailPostLikeAdd(vo); }
    public int detailPostDelAdd(PostVO vo) { return dao.detailPostDelAdd(vo); }

    public int detailPostCommentLikeAdd(Comment_likeVO vo){
        return dao.detailPostCommentLikeAdd(vo);
    };
    public int detailPostCommentLikeDel(Comment_likeVO vo){
        return dao.detailPostCommentLikeDel(vo);
    }
    // Post-like Update
    public void detailPostLikeUpdate(PostVO vo) { dao.detailPostLikeUpdate(vo); }
    public void detailPostDelLikeUpdate(PostVO vo) { dao.detailPostDelLikeUpdate(vo);}

    public BoardVO selectMember(String username){return dao.selectMember(username);}

    public BoardVO selectPost(int no) {return dao.selectPost(no);}
    public BoardVO selectContent(int no) {return dao.selectContent(no);}
    public Post_likeVO selectPostLike(int no , int user_no) {return dao.selectPostLike(no, user_no);}

    public List<Board1VO> selectPostHashTag(int no) {return dao.selectPostHashTag(no);}
    public List<CommentVO> selectcomments(int no) {return dao.selectcomments(no);}
    public List<ImageVO> selectimages(int no) {return dao.selectimages(no);}
    public List<Board2VO> selectcommentlist(int no) {return dao.selectcommentlist(no);}
    public List<NoticeVO> selectNoticesTime(int no) {return dao.selectNoticesTime(no);}

    public PostVO selectContentLikeTime(int no) {return dao.selectContentLikeTime(no);}
    public List<PostVO> selectPlusImg(int no) {return dao.selectPlusImg(no);}

    public int insertComment(CommentVO vo) {
        return dao.insertComment(vo);
    }
    public int insertRespComment(CommentVO vo) {
        return dao.insertRespComment(vo);
    }

    @Transactional
    public int deletePost(int post_no){
        List<CommentVO> comments = dao.selectComment(post_no);
        for(CommentVO comment: comments){
            dao.deleteCommentLike(comment.getNo());
        }
        dao.deletePosts(post_no);
        int result = dao.deletePost(post_no);

        return result;
    }

    public List<UserTagVO> selectUserTags(int post_no){
        return dao.selectUserTags(post_no);
    }

    @Transactional
    public int modifyPost(ModifyPostVO vo){
        dao.deleteUserTags(vo.getPost_no());
        for(int i=0; i<vo.getTags().size(); i++){
            dao.insertTagNewUser(vo.getPost_no(), vo.getTags().get(i), vo.getTops().get(i), vo.getLefts().get(i), vo.getPages().get(i));
        }
        // 태그 리스트 선별
        List<String> tagList = findHashtag(vo.getContent());
        log.info("tagList : "+tagList);

        if(!tagList.isEmpty()){
            // 게시글 내용 수정(태그 제외한 글)
            String newContent = replaceContent(vo.getContent());
            log.info("content : "+newContent);
            vo.setContent(newContent);
        }

        // 게시글 DB 넣기
        int result1 = dao.updatePost(vo.getContent(), vo.getPost_no());
        log.info("result1 :"+result1);

        if(!tagList.isEmpty()){
            // 해시태그 DB 넣기
            saveTag(tagList, vo.getPost_no());
        }

        return result1;
    }

    public List<String> findHashtag(String content){
        Pattern mypattern = Pattern.compile("#(\\S+)");
        Matcher mat = mypattern.matcher(content);
        List<String> tagList = new ArrayList<>();

        while(mat.find()){
            tagList.add(mat.group(1));
        }
        return tagList;
    }

    public String replaceContent(String content){
        int contentI = content.indexOf("#");
        log.info("contentIndex : "+contentI);
        String newContent = content.substring(0, contentI);
        log.info(newContent);
        return newContent;
    }

    @Transactional
    public void saveTag(List<String> tagList, int post_no){

        for(String tag : tagList){
            int findResult = mainDAO.findTagByContent(tag);

            HashTagVO vos = new HashTagVO();
            vos.setHashtag(tag);

            Post_hashtagVO vo = new Post_hashtagVO();

            if(findResult == 0){
                // 등록된 태그가 아니라면 태그부터 추가
                mainDAO.saveTag(vos);
                vo.setPost_no(post_no);
                vo.setHashtag_no(vos.getNo());
            }else{
                // 등록된 태그라면 태그번호 조회
                int hashtag_no = mainDAO.selectHashTagNo(tag);
                vo.setPost_no(post_no);
                vo.setHashtag_no(hashtag_no);
            }

            // 태그-포스트 매핑 테이블에 데이터 추가
            dao.deleteHashes(post_no);
            mainDAO.saveTagAndPost(vo);
        }
    }
}
