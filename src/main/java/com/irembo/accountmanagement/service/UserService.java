package com.irembo.accountmanagement.service;

import com.irembo.accountmanagement.entity.UserEntity;
import com.irembo.accountmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadDirectory;
    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }
    public Optional<UserEntity> getUser(Integer id){
        return userRepository.findByUserId(id);
    }
    public List<UserEntity> getUserByStatus(Integer staus){
        return userRepository.findByStatus(staus);
    }
public UserEntity saveUser(String firstname, String lastname, String username, String email, String password, String gender, Integer age, String dob, String maritalStatus, String nationality,MultipartFile document, MultipartFile profilePicture) {
    try {
        UserEntity user = new UserEntity();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);

        // Encode password in Base64
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        user.setPassword(encodedPassword);

        user.setUserName(username);
        user.setNationality(nationality);
        user.setGender(gender);
        user.setAge(age);
        user.setDateOfBirth(dob);
        user.setMaritalStatus(maritalStatus);
        user.setStatus(0);

        // Save profile picture to the specified directory
        Path uploadPath = Paths.get(System.getProperty("user.dir") + uploadDirectory);
        if (profilePicture != null && !profilePicture.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(profilePicture.getOriginalFilename());
            String fileExtension = StringUtils.getFilenameExtension(originalFileName);
            String uniqueFileName = generateUniqueFileName(fileExtension,"profile_picture_");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try {
                Path filePath = uploadPath.resolve(uniqueFileName);
                Files.copy(profilePicture.getInputStream(), filePath);
                user.setProfilePhoto(uniqueFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (document != null && !document.isEmpty()) {
            String documentFileName = StringUtils.cleanPath(document.getOriginalFilename());
            String fileExtension = StringUtils.getFilenameExtension(documentFileName);
            String uniqueFileName = generateUniqueFileName(fileExtension,"document_");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try {
                Path documentFilePath = uploadPath.resolve(documentFileName);
                Files.copy(document.getInputStream(), documentFilePath);
                user.setDocument(uniqueFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userRepository.save(user);
    } catch (Exception e) {
        // Handle exception
        e.printStackTrace();
        return null;
    }
}
    private String generateUniqueFileName(String fileExtension,String prefix) {
        String uniqueID = UUID.randomUUID().toString();
        return prefix + uniqueID + "." + fileExtension;
    }
    public UserEntity updateStatusToOne(Integer id) {
        Optional<UserEntity> optionalUser = userRepository.findByUserId(id);
        UserEntity user = new UserEntity();
        if (optionalUser.isPresent()) {
             user = optionalUser.get();
            user.setStatus(1);
        }
        return userRepository.save(user);
    }
    public UserEntity updateStatusToTwo(Integer id) {
        Optional<UserEntity> optionalUser = userRepository.findByUserId(id);
        UserEntity user = new UserEntity();
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            user.setStatus(2);
        }
        return userRepository.save(user);
    }
}
