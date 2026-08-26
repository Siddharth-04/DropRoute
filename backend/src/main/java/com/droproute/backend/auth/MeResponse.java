package com.droproute.backend.auth;

public class MeResponse {

    private String id;
    private String fullName;
    private String email;

    public MeResponse(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }
}