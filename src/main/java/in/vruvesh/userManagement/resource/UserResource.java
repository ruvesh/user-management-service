package in.vruvesh.userManagement.resource;

import in.vruvesh.userManagement.dto.PasswordResetRequest;
import in.vruvesh.userManagement.dto.UserDTO;
import in.vruvesh.userManagement.entity.User;
import in.vruvesh.userManagement.service.UserService;
import in.vruvesh.userManagement.utils.constants.enums.ServiceConstants;
import in.vruvesh.userManagement.utils.constants.enums.UserPrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Optional<UserDTO>> getUserById(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserById(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception{
        try{
            return new ResponseEntity<>(userService.persistUser(user), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{email}")
    public void removeUser(@PathVariable String email){
        userService.removeUserById(email);
    }

    @PutMapping("/{email}/{privilege}")
    public ResponseEntity<?> updateUserPrivilege(
            @RequestHeader String currentUserEmail,
            @PathVariable String email,
            @PathVariable UserPrivilege privilege){
        try{
            return new ResponseEntity<>(userService.updateUserPrivilege(currentUserEmail, email, privilege),
                    HttpStatus.OK);
        }
        catch (Exception e){
            HttpStatus code = e.getMessage().equals(ServiceConstants.USER_PRIVILEGE_ERROR) ? HttpStatus.FORBIDDEN
                    : HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(e.getMessage(), code);
        }
    }

    @PostMapping("/{email}/reset-password")
    public ResponseEntity<String> updatePassword(@PathVariable String email, @RequestBody PasswordResetRequest request){
        try {
            return new ResponseEntity<>(userService.updatePassword(email, request.getOldPassword(), request.getNewPassword()), HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus code = e.getMessage().equals(ServiceConstants.ACCESS_DENIED) ? HttpStatus.FORBIDDEN
                    : HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(e.getMessage(), code);
        }
    }

}
