package main.controllers;

import main.model.entities.User;
import main.model.repositories.UserRepository;
import org.apache.catalina.filters.ExpiresFilter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/reg")
    public ResponseEntity<?> Registration(@RequestBody String user){
        JSONParser parser = new JSONParser();
        HttpStatus status = HttpStatus.OK;
        try {
            JSONObject accountData = (JSONObject) parser.parse(user);
            if(userRepository.existsByEmail(accountData.get("email").toString())){
                status = HttpStatus.CONFLICT;
            }else if (!CheckMail(accountData.get("email").toString())){
                status = HttpStatus.UNPROCESSABLE_ENTITY;
            }else{
                User newUser = new User();
                newUser.setFirstname(accountData.get("firstname").toString());
                newUser.setLastname(accountData.get("lastname").toString());
                newUser.setEmail(accountData.get("email").toString());
                newUser.setPassword(accountData.get("password").toString());
                userRepository.save(newUser);
                System.out.println(accountData.get("firstname"));
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
        return new ResponseEntity<String>(status);
    }

    public static boolean CheckMail(String mail){
        return mail.matches("^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$");
    }
}
