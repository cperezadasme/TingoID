package com.example.constanza.tingoidapp.api;

import com.example.constanza.tingoidapp.api.model.LoginBody;
import com.example.constanza.tingoidapp.api.model.SignUpBody;
import com.example.constanza.tingoidapp.api.model.User;
import com.example.constanza.tingoidapp.api.model.qrBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface TingoApi {
    // TODO: Cambiar host por "10.0.3.2" para Genymotion.
    // TODO: Cambiar host por "10.0.2.2" para AVD.
    // TODO: Cambiar host por IP de tu PC para dispositivo real.
    public static final String BASE_URL = "http://10.0.2.2:3001/api/";

    @POST("usuarios/sesion")
    Call <User> login (@Body LoginBody loginBody);

    @POST("usuarios/crear")
    Call <User> signup (@Body SignUpBody signUpBody);

    @GET("user/:email")
    Call<User> getNameUser ();

    @POST("users/request")
    Call<User> requestQR (@Body qrBody qrbody);

    //@POST("usuarios/Entrada/Detalle")


}
