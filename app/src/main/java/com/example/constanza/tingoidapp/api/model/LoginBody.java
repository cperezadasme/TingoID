package com.example.constanza.tingoidapp.api.model;

import com.google.gson.annotations.SerializedName;

public class LoginBody {
    private String correo;
    private String pass;

    public LoginBody(String email, String password) {
        this.correo = email;
        this.pass = password;
    }

    public String getEmail() {
        return correo;
    }

    public void setEmail(String email) {
        this.correo = email;
    }

    public String getPassword() {
        return pass;
    }

    public void setPassword(String password) {
        this.pass = password;
    }
}
