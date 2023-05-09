package kr.co.photostagram.service;

import kr.co.photostagram.dao.ProfileDAO;
import kr.co.photostagram.vo.HashTagVO;
import kr.co.photostagram.vo.ImageVO;
import kr.co.photostagram.vo.MemberVO;
import kr.co.photostagram.vo.PostVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 날짜 : 2023/03/08
 * 이름 : 조주영
 * 내용 : 프로필 service
 */

@Slf4j
@Service
public class ProfileService {

    @Autowired
    private ProfileDAO dao;

    public void insertMember(){}
    public MemberVO selectMember(String username){return dao.selectMember(username);}
    public void selectMembers(){}
    public int updateMember(MemberVO vo){return dao.updateMember(vo);}
    public int searchUserName(String username){return dao.searchUserName(username);}
    public int deleteMember(int no){return dao.deleteMember(no);}
    public int deleteMemberData(int no){return dao.deleteMemberData(no);}


    /*** 추가 서비스 로직 ***/

    public int updatePassword (String newPass, int no) {return dao.updatePassword(newPass, no);}

    public List<PostVO> selectPosts(int no, int pg) {return dao.selectPosts(no, pg);}

    public PostVO selectThumb(int pageNo, int postNo) {return dao.selectThumb(pageNo, postNo);}

    public PostVO selectTaggedPosts(int no) {return dao.selectTaggedPosts(no);}
    public int[] selectTaggedPostsNo(int no, int pg) {return dao.selectTaggedPostsNo(no, pg);}

    public List<MemberVO> selectFollowers(int pageNo, int pg) {return dao.selectFollowers(pageNo, pg);}
    public List<MemberVO> selectFollowings(int pageNo, int pg) {return dao.selectFollowings(pageNo, pg);}
    public List<HashTagVO> selectFollowTags(int pageNo, int pg) {return dao.selectFollowTags(pageNo, pg);}

    /*** 게시물, 팔로워, 팔로잉 카운트 ***/

    public int selectCountPost(int no) {return dao.selectCountPost(no);}
    public int selectCountFollower(int no) {return dao.selectCountFollower(no);}
    public int selectCountFollowing(int no) {return dao.selectCountFollowing(no);}
    public int selectCountFollowingTags(int no) {return dao.selectCountFollowingTags(no);}


    /*** 팔로워, 팔로잉 ***/

    public int insertFollowing(int follower, int following) {return dao.insertFollow(follower, following);}
    public int deleteFollowing(int follower, int following) {return dao.deleteFollow(follower, following);}

    public int insertTagFollow (int no, int userNo) {return dao.insertTagFollow(no, userNo);}
    public int deleteTagFollow (int no, int userNo) {return dao.deleteTagFollow(no, userNo);}

    public int searchFollowing(int follower, int following) {return dao.searchFollowing(follower, following);}

    public int searchFollowingTag(int pageNo, int tagNo) {return dao.searchFollowingTag(pageNo, tagNo);}

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    /*** 프로필 사진 업로드 ***/
    public int uploadProfilePhoto(String type, MultipartFile file, int no){

        int result = 0;

        if ("delete".equals(type)){

            result = dao.deleteProfilePhoto(no);

        } else {

            // 경로
            String path = new File(uploadPath).getAbsolutePath();

            String oriName = file.getOriginalFilename();                    // 업로드된 파일의 original name
            String ext = oriName.substring(oriName.lastIndexOf("."));  // 업로드된 파일의 확장자명 찾기
            String newName = UUID.randomUUID().toString() + ext;           // 업로드된 파일명 uuid로 암호화

            // 경로 + uuid 암호화된 파일까지 합친 이미지 파일 경로
            File dest = new File(path, newName);
            try {
                file.transferTo(dest);  // 경로 내 저장
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            result = dao.updateProfilePhoto(newName, no);   // DB 업로드

        }

        return result;
    }


    /*** 수정 전 프로필 사진 삭제 ***/

    public void deleteProfilePhotoFile(String name){
        String path = new File(uploadPath).getAbsolutePath();
        String deletePath = path + "/" + name;

        File file = new File(deletePath);
        if (file.exists() == true){
            file.delete();
        }
    }

}
