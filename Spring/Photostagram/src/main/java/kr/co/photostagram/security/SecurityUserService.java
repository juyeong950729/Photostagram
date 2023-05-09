package kr.co.photostagram.security;

import kr.co.photostagram.entity.UserEntity;
import kr.co.photostagram.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecurityUserService implements UserDetailsService{

	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 스프링 시큐리티 인증 동작방식은 아이디/패스워드를 한 번에 조회하는 방식이 아닌
		// 아이디만 이용해서 사용자 정보를 로딩하고 나중에 패스워드를 검증하는 방식

		log.info("SecurityUserService...0 : " + username);

		UserEntity user = repo.findByUsername(username);

		if(user == null){
			throw new UsernameNotFoundException(username);
		}

		UserDetails myUser = MyUserDetails.builder().user(user).build();

		log.info("here5");
		return myUser;

	}

}
