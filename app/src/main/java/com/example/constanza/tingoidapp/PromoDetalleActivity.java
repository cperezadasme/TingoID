package com.example.constanza.tingoidapp;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.constanza.tingoidapp.api.TingoApi;
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

import static java.security.AccessController.getContext;

public class PromoDetalleActivity extends AppCompatActivity {
    private Retrofit mRestAdapter;
    private TingoApi mTingoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_detalle);
        setTitle("Detalle Promoción");

        // agrega boton back
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String id_avance = intent.getStringExtra("id_avance");
        String id_promocion = intent.getStringExtra("id_promocion");
        String usuario = intent.getStringExtra("usuario");



        //conexion al servicio REST
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(TingoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //conexion a la api
        mTingoApi = mRestAdapter.create(TingoApi.class);

        Call <ResponseBody> promoCall = mTingoApi.detallePromocion(new promocionBody(id_avance,id_promocion,usuario));
        promoCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    String json;
                    try {
                        json = response.body().string();
                        JSONObject response_json = new JSONObject(json);
                        String encontrado = response_json.getString("encontrado");
                        if (encontrado.equals("true")){
                            String id_promocion = response_json.getString("id_promocion");
                            final String id_avance = response_json.getString("id_avance");
                            String fecha_emision = response_json.getString("fecha_emision");
                            final String fecha_expiracion = response_json.getString("fecha_expiracion");
                            String avance = response_json.getString("avance");
                            String meta = response_json.getString("meta");
                            final String descripcion = response_json.getString("descripcion");
                            String empresa = response_json.getString("empresa");
                            String generar_codigo = response_json.getString("generar_codigo");
                            String imagen = response_json.getString("imagen");

                            TextView textView_descripcion = (TextView) findViewById(R.id.descripcion_promo_codigo);
                            TextView avance_meta = (TextView) findViewById(R.id.mensaje_avance_promo);
                            TextView textView_expiracion = (TextView) findViewById(R.id.fecha_venciomiento_codigo);
                            RelativeLayout layout_codigo = (RelativeLayout) findViewById(R.id.layout_meta);
                            ImageButton generarCodigo = (ImageButton)findViewById(R.id.boton_genarar_codigo);
                            ImageView imageView = (ImageView) findViewById(R.id.image_promo);
                            // Barra de avance
                            LinearLayout barra_progreso = (LinearLayout) findViewById(R.id.layout_avance_promo);

                            if(imagen.equals("postre.png")){
                                imageView.setImageResource(R.drawable.postre);
                            }
                            else if(imagen.equals("jugo.png")){
                                imageView.setImageResource(R.drawable.jugo);
                            }
                            else if(imagen.equals("almuerzo.png")){
                                imageView.setImageResource(R.drawable.almuerzo);
                            }

                            if (generar_codigo.equals("true")){
                                layout_codigo.setVisibility(View.VISIBLE);
                                avance_meta.setVisibility(View.GONE);
                            }

                            else {
                                layout_codigo.setVisibility(View.GONE);
                                
                            }

                            textView_descripcion.setText(descripcion);
                            textView_expiracion.setText(fecha_expiracion);
                            avance_meta.setText(avance);
                            //create_Barra(barra_progreso,Integer.valueOf(promocion.getAvance()),Integer.valueOf(promocion.getMeta()));

                            generarCodigo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent1 = new Intent(PromoDetalleActivity.this, GenerarCodigoActivity.class);
                                    intent1.putExtra("descripcion", descripcion);
                                    intent1.putExtra("id_avance",id_avance);
                                    intent1.putExtra("fecha_vencimiento",fecha_expiracion);
                                    startActivity(intent1);
                                }
                            });
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
    /*
    private void create_Barra(LinearLayout layout, int actual, int meta){
        if(layout!=null)
            layout.removeAllViews();

        ImageView barra[] = new ImageView[102];
        if (actual<meta) {
            for (int i = 0; i < 102; i++) {
                barra[i] = new ImageView(this);
                if (i == 0) {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo_inicio));
                } else if (i == 101) {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo_final));
                } else if (i <= (actual * 100 / meta)) {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo_avance));
                } else {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo_restante));
                }
                //barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.promo_avance));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 0);

                layout.addView(barra[i], params);
            }
        }
        else {
            for (int i = 0; i < 102; i++) {

                barra[i] = new ImageView(this);
                if (i == 0) {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo_inicio_fill));
                } else if (i == 101) {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo_final_fill));
                } else {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo_fill));
                }
                //barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.promo_avance));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 0);

                layout.addView(barra[i], params);
            }
        }
    }*/
}
