package in.vruvesh.userManagement.service.impl;

import in.vruvesh.userManagement.dto.UserDTO;
import in.vruvesh.userManagement.entity.User;
import in.vruvesh.userManagement.repository.UserRepository;
import in.vruvesh.userManagement.service.AuthenticationService;
import in.vruvesh.userManagement.utils.auth.AuthToken;
import in.vruvesh.userManagement.utils.constants.enums.ServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    UserRepository userRepository;

    @Override
    public AuthToken login(String username, String password) throws Exception{
        Optional<User> userOptional = userRepository.findById(username);
        if(userOptional.isPresent()){
            if(userOptional.get().getPassword().equals(password)){
                return new AuthToken(new UserDTO(userOptional.get()), ServiceConstants.AUTH_SUCCESS,
                        HttpStatus.OK.value());
            }
            else{
                throw new Exception(ServiceConstants.ACCESS_DENIED);
            }
        }
        else{
            throw new Exception(ServiceConstants.NO_SUCH_USER_EXISTS);
        }
    }

    @Override
    public String checkPrivilege(String email) throws Exception{
        String response = userRepository.getPrivilegeByEmail(email);
        if(response == null){
            throw new Exception(ServiceConstants.NO_SUCH_USER_EXISTS);
        }
        return response;
    }
}
