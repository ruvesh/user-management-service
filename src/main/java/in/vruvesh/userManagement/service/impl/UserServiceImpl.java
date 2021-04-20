package in.vruvesh.userManagement.service.impl;

import in.vruvesh.userManagement.dto.UserDTO;
import in.vruvesh.userManagement.entity.User;
import in.vruvesh.userManagement.repository.UserRepository;
import in.vruvesh.userManagement.service.AuthenticationService;
import in.vruvesh.userManagement.service.UserService;
import in.vruvesh.userManagement.utils.constants.enums.ServiceConstants;
import in.vruvesh.userManagement.utils.constants.enums.UserPrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public Optional<UserDTO> getUserById(String email)  {
        Optional<User> userOptional = userRepository.findById(email);
        if(userOptional.isPresent()){
            return Optional.of(new UserDTO(userOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        users.stream().forEach(user -> {
            userDTOList.add(new UserDTO(user));
        });
        return userDTOList;
    }

    public UserDTO persistUser(User user) throws Exception {
        if(userRepository.findById(user.getEmail()).isPresent()) {
            throw new Exception(ServiceConstants.USER_ALREADY_PRESENT);
        }
        return new UserDTO(userRepository.save(user));
    }

    @Override
    public void removeUserById(String email) {
        userRepository.deleteById(email);
    }

    @Override
    public UserDTO updateUserPrivilege(String currentUserEmail, String email, UserPrivilege privilege) throws Exception{
        if(!authenticationService.checkPrivilege(currentUserEmail).equals(UserPrivilege.ADMIN.name())){
            throw new Exception(ServiceConstants.USER_PRIVILEGE_ERROR);
        }
        Optional<User> userOptional = userRepository.findById(email);
        if(!userOptional.isPresent()){
            throw new Exception(ServiceConstants.NO_SUCH_USER_EXISTS);
        }
        User user = userOptional.get();
        user.setPrivilege(privilege);

        return new UserDTO(userRepository.save(user));
    }

    @Override
    public String updatePassword(String email, String oldPassword, String newPassword) throws Exception {
        try{
            User user = userRepository.findById(email).get();
            if(!user.getPassword().equals(oldPassword)){
                throw new Exception(ServiceConstants.ACCESS_DENIED);
            }
            user.setPassword(newPassword);
            userRepository.save(user);
            return ServiceConstants.PASSWORD_UPDATED;
        }
        catch (Exception e){
            throw e;
        }
    }

}
