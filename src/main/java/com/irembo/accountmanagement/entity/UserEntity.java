package com.irembo.accountmanagement.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String gender;
    private Integer age;
    private String dateOfBirth;
    private String maritalStatus;
    private String nationality;
    private Integer status;
    private String document;
    private Date timeStamp;

    @Lob
    private String profilePhoto;
    public UserEntity() {
    }

    public UserEntity(Integer userId, String firstName, String lastName, String userName, String email, String password, String gender, Integer age, String dateOfBirth, String maritalStatus, String nationality, Integer status, Date timeStamp) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.maritalStatus = maritalStatus;
        this.nationality = nationality;
        this.status = status;
        this.timeStamp = timeStamp;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }
    public Integer getStatus() {
        return status;
    }
    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhotoFile) {
        this.profilePhoto = profilePhotoFile;
    }
    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocument() {
        return document;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", dateOfBirth=" + dateOfBirth +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", nationality='" + nationality + '\'' +
                ", nationality='" + status + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
