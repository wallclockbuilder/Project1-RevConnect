
package com.revature.Project1.Security;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.Project1.DTO.UserDto;
import com.revature.Project1.Models.User;

public class LoginResponseDto {

    private String token;
    private UserDto user;

    public LoginResponseDto(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    @JsonProperty("Authentication")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    @JsonProperty("user")
    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
