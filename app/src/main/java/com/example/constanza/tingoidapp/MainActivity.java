package com.example.constanza.tingoidapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.example.constanza.tingoidapp.api.TingoApi;
import com.example.constanza.tingoidapp.api.model.EntradasBody;
import com.example.constanza.tingoidapp.api.model.Tinket;
import com.example.constanza.tingoidapp.api.model.TinketBody;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EntradasFragment.OnFragmentInteractionListener,
                UtilizadasFragment.OnFragmentInteractionListener, TingoQRFragment.OnFragmentInteractionListener{

    public static ArrayList<Tinket> lista_tinkets;
    public static ArrayList<Tinket> lista_utilizados;
    private TingoApi mTingoApi;
    private Retrofit mRestAdapter;

    public static String usuario;
    public static String id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        TextView user_name = (TextView) findViewById(R.id.user_name);
        TextView user_email = (TextView) findViewById(R.id.user_email);

        //datos de la activity login
        usuario = getIntent().getStringExtra("usuario");
        id_usuario = getIntent().getStringExtra("id_usuario");

        //user_name.setText(usuario);
        //user_email.setText(usuario);

        mRestAdapter = new Retrofit.Builder()
                .baseUrl(TingoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mTingoApi = mRestAdapter.create(TingoApi.class);

        //busca entradas disponibles CAMBIAR POR USUARIO
        Call<ResponseBody> entradasCall = mTingoApi.entradasDisponibles(new EntradasBody(usuario));
        entradasCall.enqueue(new Callback<ResponseBody>() {

            String json;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (response.isSuccessful()) {
                        lista_tinkets = new ArrayList<>();
                        json = response.body().string();

                        JSONArray jsonArray = new JSONArray(json);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonElement = jsonArray.getJSONObject(i);

                            String id = jsonElement.getString("id");
                            String fecha_emision = jsonElement.getString("fecha_emision");
                            String fecha_utilizacion = jsonElement.getString("fecha_utilizacion");
                            String fecha_expiracion = jsonElement.getString("fecha_expiracion");
                            String valido = jsonElement.getString("valido");
                            String empresa = jsonElement.getString("empresa");

                            Tinket tinket = new Tinket(id, fecha_emision, fecha_utilizacion, fecha_expiracion, valido, empresa);

                            lista_tinkets.add(tinket);

                        }

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });





        //busca entradas utilizadas CAMBIAR POR USUARIO

        Call<ResponseBody> utilizadasCall = mTingoApi.entradasUtilizadas(new EntradasBody(usuario));
        utilizadasCall.enqueue(new Callback<ResponseBody>() {
            String json;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (response.isSuccessful()) {
                        lista_utilizados = new ArrayList<>();
                        json = response.body().string();

                        JSONArray jsonArray = new JSONArray(json);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonElement = jsonArray.getJSONObject(i);

                            String id = jsonElement.getString("id");
                            String fecha_emision = jsonElement.getString("fecha_emision");
                            String fecha_utilizacion = jsonElement.getString("fecha_utilizacion");
                            String fecha_expiracion = jsonElement.getString("fecha_expiracion");
                            String valido = jsonElement.getString("valido");
                            String empresa = jsonElement.getString("empresa");

                            Tinket tinket = new Tinket(id, fecha_emision, fecha_utilizacion, fecha_expiracion, valido, empresa);

                            lista_utilizados.add(tinket);
                        }
                    }
                } catch (IOException e) {
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Boolean fragmento_seleccionado = false;
        Fragment fragment = null;

        if (id == R.id.nav_mi_tingo_id) {
            fragment = new TingoQRFragment();
            fragmento_seleccionado = true;
        } else if (id == R.id.nav_tinkets_disponibles) {
            fragment = new EntradasFragment();
            fragmento_seleccionado = true;

        } else if (id == R.id.nav_tinkets_utilizados) {
            fragment = new UtilizadasFragment();
            fragmento_seleccionado = true;
        } else if (id == R.id.nav_promociones) {


        } else if (id == R.id.nav_almacenar_compra) {
            IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
            fragment = new Fragment();
            fragmento_seleccionado = true;


        }

        if (fragmento_seleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragment).commit();
        }

        getSupportActionBar().setTitle(item.getTitle());
        item.setChecked(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //String usuario = getIntent().getStringExtra("usuario");
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            if(intentResult.getContents()==null){
                Toast.makeText(this, "Has cancelado el scaner",Toast.LENGTH_LONG).show();
            }
            else {
                //Toast.makeText(this,intentResult.getContents(),Toast.LENGTH_LONG).show();
                //intentResult.getContents(): indica el contenido de los escaneado
                //CONEXION A LA API
                String [] result = intentResult.getContents().split(" ");
                String empresa, id;
                empresa = result[0];
                id = result[1];

                Call<ResponseBody> almacenarTinket = mTingoApi.almacenarTinket(new TinketBody(id,empresa,usuario));
                almacenarTinket.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()){
                                String json = null;
                                try {
                                    json = response.body().string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                JSONObject response_json = new JSONObject(json);
                                String almacenar = response_json.getString("almacenar");
                                if (almacenar.equals("True")){
                                    Toast.makeText(getApplicationContext(), (String) response_json.get("mensaje"), Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), (String) response_json.get("mensaje"), Toast.LENGTH_LONG).show();
                                }
                            }
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
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
