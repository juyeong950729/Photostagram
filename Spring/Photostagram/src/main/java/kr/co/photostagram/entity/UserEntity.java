package kr.co.photostagram.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="user")
public class UserEntity {

    @Id
    private int no;
    private String username;
    private String name;
    private String password;
    private String email;
    private String profileImg;
    private String profilePhone;
    private String gender;
    private String birth;
}
