package com.example.constanza.tingoidapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface TingoApi {
    // TODO: Cambiar host por "10.0.3.2" para Genymotion.
    // TODO: Cambiar host por "10.0.2.2" para AVD.
    // TODO: Cambiar host por IP de tu PC para dispositivo real.
    public static final String BASE_URL = "http://10.0.2.2:3001/api/";

    @POST("users/signin")
    Call <User> login (@Body LoginBody loginBody);

    @POST("users/signup")
    Call <User> signup (@Body SignUpBody signUpBody);
}
