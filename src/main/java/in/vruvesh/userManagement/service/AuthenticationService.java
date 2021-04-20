package in.vruvesh.userManagement.service;

import in.vruvesh.userManagement.utils.auth.AuthToken;
import org.springframework.stereotype.Service;


@Service
public interface AuthenticationService {

    AuthToken login(String username, String password) throws Exception;

    String checkPrivilege(String email) throws Exception;

}
