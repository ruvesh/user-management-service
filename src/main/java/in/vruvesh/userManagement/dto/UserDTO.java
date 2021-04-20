package in.vruvesh.userManagement.dto;

import in.vruvesh.userManagement.entity.User;
import in.vruvesh.userManagement.utils.constants.enums.UserPrivilege;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String email;
    private UserPrivilege privilege;

    public UserDTO(User user){
        this.email = user.getEmail();
        this.privilege = user.getPrivilege();
    }

}
