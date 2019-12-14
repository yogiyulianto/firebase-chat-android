package com.yogiyulianto.pam.firebase.models;

public class Chat {
    private String username;
    private String message;

    public Chat() {
        // Default Constructor kosong
    }

    public Chat(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }


    public String getMessage() {
        return message;
    }
}
