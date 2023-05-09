package kr.co.photostagram.dao;

import kr.co.photostagram.vo.HashTagVO;
import kr.co.photostagram.vo.ImageVO;
import kr.co.photostagram.vo.MemberVO;
import kr.co.photostagram.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 날짜 : 2023/03/08
 * 이름 : 조주영
 * 내용 : profileDAO
 */

@Mapper
@Repository
public interface ProfileDAO {

    public void insertMember();
    public MemberVO selectMember(String username);
    public void selectMembers();
    public int updateMember(MemberVO vo);
    public int searchUserName(String username);
    public int deleteMember(int no);
    public int deleteMemberData(int no);

    public int updatePassword(@Param("newPass") String newPass, @Param("no") int no);

    public List<PostVO> selectPosts(@Param("no") int no, @Param("pg") int pg);

    public PostVO selectThumb(@Param("pageNo") int pageNo, @Param("postNo") int postNo);

    public PostVO selectTaggedPosts(int no);
    public int[] selectTaggedPostsNo (@Param("no") int no, @Param("pg") int pg);

    public List<MemberVO> selectFollowers (@Param("pageNo") int pageNo, @Param("pg") int pg);
    public List<MemberVO> selectFollowings (@Param("pageNo") int pageNo, @Param("pg") int pg);

    public List<HashTagVO> selectFollowTags (@Param("pageNo") int pageNo, @Param("pg") int pg);

    /*** 게시물, 팔로워, 팔로잉 수 count ***/

    public int selectCountPost(int no);
    public int selectCountFollower(int no);
    public int selectCountFollowing(int no);
    public int selectCountFollowingTags(int no);


    /*** 팔로워, 팔로잉 ***/

    public int insertFollow (@Param("follower") int follower, @Param("following") int following);
    public int deleteFollow (@Param("follower") int follower, @Param("following") int following);

    public int insertTagFollow (@Param("no") int no, @Param("userNo") int userNo);
    public int deleteTagFollow (@Param("no") int no, @Param("userNo") int userNo);

    public int searchFollowing (@Param("follower") int follower, @Param("following") int following);
    public int searchFollowingTag (@Param("pageNo") int pageNo, @Param("tagNo") int tagNo);

    /*** 프로필 사진 업로드 ***/
    public int updateProfilePhoto(@Param("newName") String newName, @Param("no") int no);

    public int deleteProfilePhoto(int no);

}
