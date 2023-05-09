package kr.co.photostagram.dao;

import kr.co.photostagram.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/*
    이름 : 김진우
    날짜 : 2023/03/24
    내용 : MemberDAO
 */

@Mapper
@Repository
public interface MemberDAO {
    public int insertMember(MemberVO vo);
    public int chkUserName(String userName);
    public int chkEmail(String email);
    public String searchId(@Param("name") String name, @Param("email") String email);
    public String searchPass(@Param("userName") String userName, @Param("email") String email);
    public int changePass(@Param("userName") String userName, @Param("pass") String pass);
}
