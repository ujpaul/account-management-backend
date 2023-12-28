package com.irembo.accountmanagement.dto;


import java.util.Date;

public class LoginResponseDto {

  private Date expiryDate;
  private Integer responseCode;
  private String token;
  private String username;
  private String firstName;
  private String lastName;
  private String message;
  private String profilePhoto;

  public LoginResponseDto(String message,Date expiryDate, Integer responseCode, String token, String username,String firstname, String lastname,String profilePhoto) {
    this.message = message;
    this.expiryDate = expiryDate;
    this.responseCode = responseCode;
    this.token = token;
    this.username = username;
    this.firstName = firstname;
    this.lastName = lastname;
    this.profilePhoto = profilePhoto;
  }

  public LoginResponseDto() {
  }

  public LoginResponseDto(Date expiryDate, int responseCode, String token, String username){
    this.expiryDate = expiryDate;
    this.responseCode =  responseCode;
    this.token = token;
    this.username  = username;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }

  public Integer getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(Integer responseCode) {
    this.responseCode = responseCode;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getProfilePhoto() {
    return profilePhoto;
  }

  public void setProfilePhoto(String profilePhoto) {
    this.profilePhoto = profilePhoto;
  }

  public String getUsername() {
    return username;
  }
  public String getFirstName() {
    return firstName;
  }
  public String getLastName() {
    return lastName;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  public void setFirstName(String firstname) {
    this.firstName = firstname;
  }
  public void setLastName(String lastname) {
    this.lastName = lastname;
  }
}
