package com.example.constanza.tingoidapp.api.model;

public class User {
    private String email;
    private String id_tinket;
    //private String name;
    //private String token;


    public String getId_tinket() {
        return id_tinket;
    }

    public void setId_tinket(String id_tinket) {
        this.id_tinket = id_tinket;
    }

    public User(String email, String id_tinket) {
        this.email = email;
        this.id_tinket = id_tinket;

    }

    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }



}
