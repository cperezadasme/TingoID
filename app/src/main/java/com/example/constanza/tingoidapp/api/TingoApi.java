package com.example.constanza.tingoidapp.api;

import com.example.constanza.tingoidapp.api.model.EntradasBody;
import com.example.constanza.tingoidapp.api.model.Handshaking;
import com.example.constanza.tingoidapp.api.model.LoginBody;
import com.example.constanza.tingoidapp.api.model.SignUpBody;
import com.example.constanza.tingoidapp.api.model.TinketBody;
import com.example.constanza.tingoidapp.api.model.User;
import com.example.constanza.tingoidapp.api.model.detalleBody;
import com.google.gson.JsonObject;
//import com.example.constanza.tingoidapp.api.model.qrBody;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface TingoApi {
    // TODO: Cambiar host por "10.0.3.2" para Genymotion.
    // TODO: Cambiar host por "10.0.2.2" para AVD.
    // TODO: Cambiar host por IP de tu PC para dispositivo real.
    public static final String BASE_URL = "http://10.6.43.212:8000/tingo/";

    @GET ("handshaking/")
    Call <ResponseBody> handshaking();

    @POST("login/")
    Call <ResponseBody> login (@Header("X-CSRFToken") String csrf_token, @Body LoginBody loginBody);

    @POST("almacenarUsuario/")
    Call <ResponseBody> signup (@Header("X-CSRFToken") String csrf_token, @Body SignUpBody signUpBody);

    @POST("almacenarTinket/")
    Call <ResponseBody> almacenarTinket (@Body TinketBody tinketBody);

    @POST("entradasDisponibles/")
    Call <ResponseBody> entradasDisponibles(@Body EntradasBody entradaBody);

    @POST("detalleEntrada/")
    Call <ResponseBody> detalleEntrada (@Body detalleBody detalle);

    @POST("entradasUtilizadas/")
    Call <ResponseBody> entradasUtilizadas(@Body EntradasBody entradasBody);

    @POST("promocionesExistentes/")
    Call <ResponseBody> promocionesExistentes(@Body EntradasBody entradasBody);

    @POST("generarAvance/")
    Call <ResponseBody> generarAvance(@Body EntradasBody entradasBody);

    @POST("mostrarPromociones/")
    Call <ResponseBody> mostrarPromociones(@Body EntradasBody entradasBody);
}
