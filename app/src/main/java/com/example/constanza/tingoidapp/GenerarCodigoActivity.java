package com.example.constanza.tingoidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.constanza.tingoidapp.api.TingoApi;
import com.example.constanza.tingoidapp.api.model.PromoDetalleBody;
import com.example.constanza.tingoidapp.api.model.promocionBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenerarCodigoActivity extends AppCompatActivity {
    private Retrofit mRestAdapter;
    private TingoApi mTingoApi;
    private String codigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_codigo);

        final String descripcion, id_avance,fecha;
        Intent intent = getIntent();
        descripcion = intent.getStringExtra("descripcion");
        id_avance = intent.getStringExtra("id_avance");
        fecha = intent.getStringExtra("fecha_vencimiento");

        // agrega boton back
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //conexion al servicio REST
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(TingoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //conexion a la api
        mTingoApi = mRestAdapter.create(TingoApi.class);


        Call <ResponseBody> codigoCall = mTingoApi.generarCodigo(new PromoDetalleBody(id_avance,""));
        codigoCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    String json;

                    try {
                        json = response.body().string();
                        JSONObject response_json = new JSONObject(json);
                        String encontro = response_json.getString("encontro");
                        if (encontro.equals("true")){
                            TextView codigo_tv = (TextView) findViewById(R.id.codigo_promo);
                            codigo = response_json.getString("codigo");
                            codigo_tv.setText(codigo);

                        }

                        TextView descripcion_promo = (TextView) findViewById(R.id.descripcion_promo_codigo);

                        TextView fecha_exp = (TextView) findViewById(R.id.fecha_venciomiento_codigo);


                        descripcion_promo.setText(descripcion);

                        fecha_exp.setText(fecha);


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
