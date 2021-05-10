package com.listofusers.withspring.controller;

import com.listofusers.withspring.Requests.userPostRequestBody;
import com.listofusers.withspring.domain.user;
import com.listofusers.withspring.service.userService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/users")
@Log4j2
@AllArgsConstructor
public class userController {
    private userService userService;


    @GetMapping("/ListOfAllUsers")
    public ResponseEntity<List<user>> ListOfAllUsers(){
        return new ResponseEntity<>(userService.listNonPageable()
        ,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<user> save(@RequestBody userPostRequestBody userPostRequestBody){
        return new ResponseEntity<>(userService.save(userPostRequestBody)
        ,HttpStatus.OK);
    }





}
