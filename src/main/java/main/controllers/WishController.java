package main.controllers;
import main.model.entities.Wish;
import main.model.repositories.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WishController {

    @Autowired
    private WishRepository wishRepository;

    @PostMapping("/addwish")
    public ResponseEntity<?> createWish (@RequestBody Wish wish){
        HttpStatus status = HttpStatus.OK;
        try{
            wishRepository.save(wish);
        }catch (Exception ex){
            System.out.println(ex);
        }
        return new ResponseEntity<String>(status);
    }

    @GetMapping("/getwishes")
    public ResponseEntity<?> getWishes(){
        List <Wish> allWish = (List<Wish>) wishRepository.findAll();
        allWish.forEach(wish -> System.out.println(wish.getName()));
        return new ResponseEntity<>(allWish, HttpStatus.OK);
    }
}
