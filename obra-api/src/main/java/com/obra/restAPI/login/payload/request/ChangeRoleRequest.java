package com.obra.restAPI.login.payload.request;

import javax.validation.constraints.NotBlank;

public class ChangeRoleRequest {

    @NotBlank
    private String role;

    private LoginRequest loginRequest;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }
}
