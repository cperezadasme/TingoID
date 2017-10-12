package com.example.constanza.tingoidapp.api.model;

/**
 * Created by Constanza on 03-10-17.
 */

public class Handshaking {
    private String csrf_token;

    public Handshaking(String csrf_token) {
        this.csrf_token = csrf_token;
    }

    public String getCsrf_token() {
        return csrf_token;
    }

    public void setCsrf_token(String csrf_token) {
        this.csrf_token = csrf_token;
    }
}
