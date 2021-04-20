package in.vruvesh.userManagement.entity;

import in.vruvesh.userManagement.utils.constants.enums.UserPrivilege;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "privilege")
    private UserPrivilege privilege = UserPrivilege.REGULAR;
}
