package kr.co.photostagram.repository;

import kr.co.photostagram.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    public UserEntity findByUsername(String username);
}
