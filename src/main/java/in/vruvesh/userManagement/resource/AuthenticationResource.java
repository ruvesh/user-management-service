package in.vruvesh.userManagement.resource;

import in.vruvesh.userManagement.dto.AuthRequest;
import in.vruvesh.userManagement.service.AuthenticationService;
import in.vruvesh.userManagement.utils.auth.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationResource {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<AuthToken> loginUser(@RequestBody AuthRequest request){
        try{
            return new ResponseEntity<>(authenticationService.login(request.getUsername(), request.getPassword()),
                    HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new AuthToken(null , e.getMessage(), HttpStatus.FORBIDDEN.value()),
                    HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("privilege/{email}")
    public ResponseEntity<?> checkPrivilege(@PathVariable String email){
        try {
            return new ResponseEntity<>(authenticationService.checkPrivilege(email), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
