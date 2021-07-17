package main.controllers;
import main.model.dtos.UserAuthentificationDTO;
import main.model.dtos.UserDTO;
import main.model.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorisationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/auth")
    public ResponseEntity<?> Authorisation (@RequestBody UserAuthentificationDTO authData){
        HttpStatus status = HttpStatus.OK;
        if(!userRepository.existsByEmail(authData.getLogin())){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<String>(status);
        }else{
            if(authData.getPassword().equals(userRepository.findByEmail(authData.getLogin()).getPassword())){
                UserDTO userDTO = new UserDTO(
                        userRepository.findByEmail(authData.getLogin()).getId(),
                        userRepository.findByEmail(authData.getLogin()).getEmail(),
                        userRepository.findByEmail(authData.getLogin()).getFirstname(),
                        userRepository.findByEmail(authData.getLogin()).getLastname(),
                        userRepository.findByEmail(authData.getLogin()).getAvatar()
                );
                return new ResponseEntity<UserDTO>(userDTO, status);
            }else{
                status = HttpStatus.CONFLICT;
                System.out.println(authData.getPassword());
                System.out.println(userRepository.findByEmail(authData.getLogin()).getPassword());
                return new ResponseEntity<String>(status);
            }
        }
    }
}
