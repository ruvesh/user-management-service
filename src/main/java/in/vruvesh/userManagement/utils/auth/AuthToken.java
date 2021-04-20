package in.vruvesh.userManagement.utils.auth;

import in.vruvesh.userManagement.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {

    private UserDTO user;

    private String message;

    private int code;

}
