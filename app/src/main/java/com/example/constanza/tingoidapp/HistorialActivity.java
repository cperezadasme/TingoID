package com.example.constanza.tingoidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.constanza.tingoidapp.api.TingoApi;
import com.example.constanza.tingoidapp.api.model.EntradasBody;
import com.example.constanza.tingoidapp.api.model.Tinket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistorialActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Retrofit mRestAdapter;
    private TingoApi mTingoApi;
    private ArrayList<Tinket> lista_tinkets = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        //conexion al servicio REST
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(TingoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //conexion a la api
        mTingoApi = mRestAdapter.create(TingoApi.class);

        String usuario = getIntent().getStringExtra("usuario");

        Call<ResponseBody> utilizadasCall = mTingoApi.entradasUtilizadas(new EntradasBody(usuario));
        utilizadasCall.enqueue(new Callback<ResponseBody>() {
            String json;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (response.isSuccessful()){
                        json = response.body().string();

                        JSONArray jsonArray = new JSONArray(json);

                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonElement = jsonArray.getJSONObject(i);

                            String id = jsonElement.getString("id");
                            String fecha_emision = jsonElement.getString("fecha_emision");
                            String fecha_utilizacion = jsonElement.getString("fecha_utilizacion");
                            String fecha_expiracion = jsonElement.getString("fecha_expiracion");
                            String valido = jsonElement.getString("valido");
                            String empresa = jsonElement.getString("empresa");
                            String detalle = jsonElement.getString("tipo");

                            Tinket tinket = new Tinket(id,fecha_emision,fecha_utilizacion,fecha_expiracion,valido,empresa, detalle);

                            lista_tinkets.add(tinket);
                        }

                        if (lista_tinkets.size()>0){
                            listView = (ListView)findViewById(R.id.lista_entradas_utilizadas);
                            adapter = new UtilizadasAdapter(getApplicationContext(),R.layout.item_entrada_utilizada,lista_tinkets);
                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    Tinket tinket_seleccionado = (Tinket) adapter.getItem(position);
                                    Intent intent = new Intent(HistorialActivity.this,DetalleActivity.class);
                                    intent.putExtra("id_tinket", tinket_seleccionado.getId());
                                    startActivity(intent);
                                }
                            });
                        }

                        else {
                            TextView usadas = (TextView)findViewById(R.id.entradas_utilizadas);
                            usadas.setText("No tienes entradas utilizadas");
                        }

                    }
                }

                catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
