package kr.co.photostagram.service;

import kr.co.photostagram.dao.MyDAO;
import kr.co.photostagram.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MyService {

    @Autowired
    private MyDAO dao;

    @Autowired
    private ProfileService profileService;

    public int insertType (int no, String type) {return dao.insertType(no, type);}
    public int insertDetail (int no, String type, String detail) {return dao.insertDetail(no, type, detail);}
    public int selectUserRecent () {return dao.selectUserRecent();}
    public PostVO selectPost (int no) {return dao.selectPost(no);}
    public List<PostVO> selectPosts (int no) {return dao.selectPosts(no);}
    public int[] selectLikePostNo (int no) {return dao.selectLikePostNo(no);}
    public int[] selectCommentNo (int no) {return dao.selectCommentNo(no);}
    public List<PostVO> selectMyCommentPosts (int no) {return dao.selectMyCommentPosts(no);}
    public List<CommentVO> selectMyComments (int postNo, int userNo) {return dao.selectMyComments(postNo, userNo);}
    public List<HistoryVO> selectHistory (int no) {return dao.selectHistory(no);}
    public int[] sortSelectLikePostNo (int no, String start, String end, String sort) {return dao.sortSelectLikePostNo(no, start, end, sort);}
    public List<PostVO> sortSelectMyCommentPosts (int no, String start, String end, String sort) {return dao.sortSelectMyCommentPosts(no, start, end, sort);}
    public List<PostVO> sortSelectPosts (int no, String start, String end, String sort) {return dao.sortSelectPosts(no, start, end, sort);}
    public List<HistoryVO> sortSelectHistory (int no, String start, String end, String sort) {return dao.sortSelectHistory(no, start, end, sort);}
    public int updateRemoveLike (int no) {return dao.updateRemoveLike(no);}
    public int deleteComment(int no) {return dao.deleteComment(no);}
    public int deleteCommentLike (int no) {return dao.deleteCommentLike(no);}
    public int deleteLike (int postNo, int userNo) {return dao.deleteLike(postNo, userNo);}
    public int deletePost (int no) {return dao.deletePost(no);}


    /*** 계정 내역 update ***/
    public void updateHistories (MemberVO preUser, MemberVO newUser) {

        //log.info("here1");

        // 소개가 바뀌었을 경우
        if (!(preUser.getProfileText()).equals(newUser.getProfileText())) {
            //log.info("here2");
            insertDetail(preUser.getNo(), "intro", newUser.getProfileText());
        }

        // 핸드폰 번호가 바뀌었을 경우
        if (!(preUser.getProfilePhone()).equals(newUser.getProfilePhone())) {
            //log.info("here3");
            insertDetail(preUser.getNo(), "phone", newUser.getProfilePhone());
        }

        // 이름이 바뀌었을 경우
        if (!(preUser.getName()).equals(newUser.getName())){
            //log.info("here4");
            insertDetail(preUser.getNo(), "name", newUser.getName());
        }
    }

    /*** 가입 년, 월, 일 구하기 ***/
    public int[] selectJoinDate (int no) {

        int[] joinDate = new int[3];
        String date = dao.selectJoinDate(no);

        joinDate[0] = Integer.parseInt(date.substring(0, 4));       // year
        joinDate[1] = Integer.parseInt(date.substring(5, 7));       // month
        joinDate[2] = Integer.parseInt(date.substring(8, 10));      // day

        return joinDate;
    }

    /*** 내 활동 선택 삭제 ***/
    public int deleteCheck (int no, String type, int[] array){
        int result = 0;
        
        // 좋아요 삭제
        if ("like".equals(type)) {
            for (int i=0; i<array.length; i++) {
                updateRemoveLike(array[i]);
                result = deleteLike(array[i], no);
            }

        // 업로드 게시물 삭제
        } else if ("photo".equals(type)){
            for (int i=0; i<array.length; i++) {
                int[] replyNumbers = selectCommentNo(array[i]);
                for (int j = 0; j < replyNumbers.length; j++) {
                    System.out.println(replyNumbers[j]);
                    deleteCommentLike(replyNumbers[j]);
                }
                result = deletePost(array[i]);
            }

        // 댓글 삭제
        } else if ("comment".equals(type)){
            for (int i=0; i<array.length; i++){
                result = deleteComment(array[i]);
            }
        }

        return result;
    }

    /*** 정렬 기한 ***/
    public String[] sortPeriod (PeriodVO vo){

        String[] period = new String[2];
        period[0] = vo.getStartYear() +"-"+ vo.getStartMonth() +"-"+ vo.getStartDay() +" 00:00:00";
        period[1] = vo.getEndYear() +"-"+ vo.getEndMonth() +"-"+ vo.getEndDay() +" 23:59:59";

        return period;
    }

    /*** 각종 sort 요청 ***/
    public Map<Integer, PostVO> mySort (int no, String start, String end, String sort, String type){

        Map<Integer, PostVO> sortMap = new HashMap<>();
        Map<Integer, PostVO> map = new TreeMap<>();


        // photos/posts 에서 보낸 요청
        if ("posts".equals(type)){

            List<PostVO> articles = sortSelectPosts(no, start, end, sort);

            for (int i=0; i<articles.size(); i++){
                int artNo = articles.get(i).getNo();
                PostVO article = profileService.selectThumb(no, artNo);
                sortMap.put(i, article);
                map = new TreeMap<>(sortMap);
            }

        // interaction/like 에서 보낸 요청
        } else if ("like".equals(type)){

            int[] articles = sortSelectLikePostNo(no, start, end, sort);

            for (int i=0; i<articles.length; i++){
                PostVO article = selectPost(articles[i]);
                sortMap.put(i, article);
            }

            map = new TreeMap<>(Comparator.reverseOrder());
            map.putAll(sortMap);

        // interaction/comment 에서 보낸 요청
        } else if ("comment".equals(type)){

            List<PostVO> articles = sortSelectMyCommentPosts(no, start, end, sort);

            for (int i=0; i<articles.size(); i++) {
                List<CommentVO> comments = selectMyComments(articles.get(i).getNo(), no);
                articles.get(i).setComments(comments);
                sortMap.put(i, articles.get(i));
            }

            map = new TreeMap<>(Comparator.reverseOrder());
            map.putAll(sortMap);
        }

        return map;

    }

}
