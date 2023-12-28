package com.irembo.accountmanagement.controller;

import com.irembo.accountmanagement.dto.LoginDto;
import com.irembo.accountmanagement.dto.LoginResponseDto;
import com.irembo.accountmanagement.entity.UserEntity;
import com.irembo.accountmanagement.service.JwtLoginService;
import com.irembo.accountmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private final JwtLoginService jwtLoginService;
    private final UserService userService;

    public UserController(JwtLoginService jwtLoginService, UserService userService) {
        this.jwtLoginService = jwtLoginService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> uploadUser(
            @RequestParam("firstName") String firstname,
            @RequestParam("lastName") String lastname,
            @RequestParam("userName") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("gender") String gender,
            @RequestParam("age") Integer age,
            @RequestParam("dateOfBirth") String dob,
            @RequestParam("maritalStatus") String maritalStatus,
            @RequestParam("nationality") String nationality,
            @RequestParam("file") MultipartFile profilePicture,
            @RequestParam("document") MultipartFile document) {

        UserEntity savedUser = userService.saveUser(
                firstname,
                lastname,
                username,
                email,
                password,
                gender,
                age,
                dob,
                maritalStatus,
                nationality,
                document,
                profilePicture
        );

        if (savedUser != null) {
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value="/login")
    @ResponseBody
    public LoginResponseDto login(@RequestBody LoginDto loginDto, HttpServletRequest request) throws Exception {

        LoginResponseDto response =jwtLoginService.login(loginDto);
        return response;
    }

    @GetMapping("")
    public List<UserEntity> getAllUsers(){
        return userService.getUsers();
    }
    @GetMapping("/{id}")
    public Optional<UserEntity> getUserById(@PathVariable Integer id){
        return userService.getUser(id);
    }
    @GetMapping("/status/{status}")
    public List<UserEntity> getUserByStatus(@PathVariable Integer status){
        return userService.getUserByStatus(status);
    }
    @PutMapping("/verifyUser/{id}")
public ResponseEntity<UserEntity> verifyUser(@PathVariable Integer id){
        UserEntity updatedUser = userService.updateStatusToOne(id);
        if(updatedUser != null){
            return ResponseEntity.ok(updatedUser);
        }else{
            return ResponseEntity.badRequest().build();
        }
}
    @PutMapping("/rejectVerification/{id}")
    public ResponseEntity<UserEntity> rejectUserVerification(@PathVariable Integer id){
        UserEntity updatedUser = userService.updateStatusToTwo(id);
        if(updatedUser != null){
            return ResponseEntity.ok(updatedUser);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
