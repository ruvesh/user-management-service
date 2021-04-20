package in.vruvesh.userManagement.service;

import in.vruvesh.userManagement.dto.UserDTO;
import in.vruvesh.userManagement.entity.User;
import in.vruvesh.userManagement.utils.constants.enums.UserPrivilege;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<UserDTO> getUserById(String email);

    List<UserDTO> getAllUsers();

    UserDTO persistUser(User user) throws Exception;

    void removeUserById(String email);

    UserDTO updateUserPrivilege(String currentUserEmail, String email, UserPrivilege privilege) throws Exception;

    String updatePassword(String email, String oldPassword, String newPassword) throws Exception;
}
