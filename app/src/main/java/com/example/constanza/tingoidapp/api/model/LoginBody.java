package com.example.constanza.tingoidapp.api.model;

import com.google.gson.annotations.SerializedName;

public class LoginBody {
    //atributo que sera interpretado
    @SerializedName("email")
    private String email;
    private String password;

    public LoginBody(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
