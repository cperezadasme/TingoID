package com.example.constanza.tingoidapp.api.model;

public class User {
    private String email;
    private String id;
    //private String name;
    //private String token;

    public User(String email, String id_tinket) {
        this.email = email;
        this.id = id_tinket;

    }

    public String getId() {
        return id;
    }

    public void setId_user(String id_tinket) {
        this.id = id_tinket;
    }



    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }



}
