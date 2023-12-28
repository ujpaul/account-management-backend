package com.irembo.accountmanagement.service;

import com.irembo.accountmanagement.dto.LoginDto;
import com.irembo.accountmanagement.dto.LoginResponseDto;
import com.irembo.accountmanagement.entity.UserEntity;
import com.irembo.accountmanagement.exception.InvalidCredentialsException;
import com.irembo.accountmanagement.repository.UserRepository;
import com.irembo.accountmanagement.utilities.GeneralLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;


@Service
public class JwtLoginService  {
    private static Logger logger = LoggerFactory.getLogger(GeneralLogger.class);

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    private String userName;
    private String password;
    private UserEntity user;

    public LoginResponseDto login(LoginDto request) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();

        if (request.getUsername() != null && request.getPassword() != null &&
                request.getUsername().length() > 0 &&
                request.getPassword().length() > 0) {

            userName = request.getUsername();
            password = request.getPassword();

            boolean isCorrectCredentials = isUserAllowed(userName, password);

            Optional<UserEntity> optionalUsers = userRepository.findByUserName(userName);
            if(optionalUsers.isPresent()){
                 user = optionalUsers.get();
            }
            if (isCorrectCredentials) {
                String token = jwtProvider.createJwtToken(userName);
                loginResponseDto = new LoginResponseDto("Login successfully",jwtProvider.getExpiryDate(token), 200, token, userName,user.getFirstName(),user.getLastName(),user.getProfilePhoto());
            } else {
                loginResponseDto = new LoginResponseDto(
                        "Invalid username or password",
                        null,
                        400,
                        null,
                        null,
                        null,
                        null,
                        null
                );
            }
        } else {
            throw new InvalidCredentialsException("Invalid username or password.");
        }

        return loginResponseDto;
    }

    /**
     * @param username
    //     * @param password
     * @return
     */


    public Boolean isUserAllowed(String username) {


        Optional<UserEntity> optionalUsers = userRepository.findByUserName( username);

        if(optionalUsers.isPresent())
            return true;
        else
            return false;

    }


    public Boolean isUserAllowed(String username, String password) {
        password = encodePasswd(password);
        Optional<UserEntity> userToLogin = userRepository.findByUserName(userName);
        if(userToLogin.isPresent()){
            UserEntity user = userToLogin.get();
        }
        Optional<UserEntity> optionalUsers = userRepository.findByUserNameAndPassword(username, password);

        if (optionalUsers.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param encodedPasswd
     * @return
     */
    public String decodePasswd(String encodedPasswd) {
        Base64.Decoder decoder = Base64.getDecoder();
        String decodedPassword = new String(decoder.decode(encodedPasswd));
        return decodedPassword;
    }

    /**
     * @param passwordToEncode
     * @return
     */
    public String encodePasswd(String passwordToEncode) {
        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(passwordToEncode.getBytes()));
    }

    public String getStackString(Exception ex) {
        String errorStackTrace = "";
        StackTraceElement[] stack = ex.getStackTrace();

        for (int i = 0; i < stack.length; i++) {
            if (errorStackTrace.length() == 0)
                errorStackTrace = errorStackTrace + stack[i].toString();
            else
                errorStackTrace = errorStackTrace + "|" + stack[i].toString();
        }

        return errorStackTrace;
    }

}
