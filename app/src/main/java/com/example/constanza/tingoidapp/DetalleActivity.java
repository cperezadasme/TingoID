package com.example.constanza.tingoidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.constanza.tingoidapp.api.TingoApi;
import com.example.constanza.tingoidapp.api.model.detalleBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleActivity extends AppCompatActivity {
    private Retrofit mRestAdapter;
    private TingoApi mTingoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        // agrega boton back
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String id_tinket = intent.getStringExtra("id_tinket");

        //conexion al servicio REST
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(TingoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //conexion a la api
        mTingoApi = mRestAdapter.create(TingoApi.class);

        Call<ResponseBody> detalleCall = mTingoApi.detalleEntrada(new detalleBody(id_tinket));
        detalleCall.enqueue(new Callback<ResponseBody>() {
            String json;
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        json = response.body().string();
                        JSONObject response_json = new JSONObject(json);
                        String detalle = response_json.getString("detalle");
                        if (detalle.equals("true")){
                            //String id = response_json.getString("id");
                            String fecha_emision = response_json.getString("fecha_emision");
                            String fecha_utilizacion = response_json.getString("fecha_utilizacion");
                            String fecha_expiracion = response_json.getString("fecha_expiracion");
                            //String valido = response_json.getString("valido");
                            String empresa = response_json.getString("empresa");
                            String tipo = response_json.getString("tipo");
                            String valor = response_json.getString("valor");

                            TextView textView_emision = (TextView) findViewById(R.id.detalle_emision);
                            //TextView textView_utilizacion = (TextView) findViewById(R.id.detalle_utilizacion);
                            TextView textView_empresa = (TextView) findViewById(R.id.detalle_empresa);
                            TextView textView_expiracion = (TextView) findViewById(R.id.detalle_expiracion);
                            TextView textView_tipo = (TextView) findViewById(R.id.detalle_tipo);
                            TextView textView_valor = (TextView) findViewById(R.id.detalle_valor);

                            textView_emision.setText(fecha_emision);
                            textView_empresa.setText(empresa);
                            textView_expiracion.setText(fecha_expiracion);
                            textView_tipo.setText(tipo);
                            textView_valor.setText(valor);

                            /*
                            if (fecha_utilizacion.equals("2050-12-12")){
                                textView_utilizacion.setText("Disponible");
                            }

                            else {
                                textView_utilizacion.setText(fecha_utilizacion);
                            }
                            */

                        }


                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
