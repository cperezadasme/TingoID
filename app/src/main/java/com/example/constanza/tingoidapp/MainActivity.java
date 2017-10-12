package com.example.constanza.tingoidapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.constanza.tingoidapp.api.TingoApi;
import com.example.constanza.tingoidapp.api.model.TinketBody;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.example.constanza.tingoidapp.api.model.qrBody;

public class MainActivity extends AppCompatActivity {
   // Typeface face =Typeface.createFromAsset(getAssets(),"fonts/Druchilla.ttf");
    //txtV.setTypeface(face);
   // private ZXingScannerView scannerView;
    //private String user_email;
    //private String user_name;

    private TingoApi mTingoApi;
    private Retrofit mRestAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //conexion a la api
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(TingoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mTingoApi = mRestAdapter.create(TingoApi.class);

        ImageView mImage = (ImageView) findViewById(R.id.buttonAvatar);
        Button mScanner = (Button) findViewById(R.id.buttonScanner);
        Button mTinketsDisponibles = (Button) findViewById(R.id.buttonDisponibles);
        Button mTinketsUtilizados = (Button) findViewById(R.id.buttonHistorial);

        //obtengo usuario con el que inicia sesión
        String usuario = getIntent().getStringExtra("usuario");
/*
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_email = getIntent().getStringExtra("userEmail");

                Intent nuevo_intent = new Intent(MainActivity.this, UserProfile.class);
                nuevo_intent.putExtra("user_email", user_email);
                startActivity(nuevo_intent);
                finish();
            }
        });
*/
        mScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                /*
                Intent intent = new Intent(MainActivity.this,ScannerActivity.class);
                startActivity(intent);
                finish();
                */
            }
        });

        mTinketsDisponibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline()){
                    String usuario = getIntent().getStringExtra("usuario");
                    Intent intent = new Intent(MainActivity.this, EntradasActivity.class);
                    intent.putExtra("usuario",usuario);
                    startActivity(intent);
                }
            }
        });

        mTinketsUtilizados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline()){
                    String usuario = getIntent().getStringExtra("usuario");
                    Intent intent = new Intent(MainActivity.this, HistorialActivity.class);
                    intent.putExtra("usuario",usuario);
                    startActivity(intent);
                }
            }
        });


      /*
        if (!SessionPrefs.get(this).isloggedIn()){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return;
        }
        */


        //findViewById(R.id.buttonScanner).setOnClickListener(this);
        //findViewById(R.id.buttonGenerate).setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String usuario = getIntent().getStringExtra("usuario");
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            if(intentResult.getContents()==null){
                Toast.makeText(this, "Has cancelado el scaner",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,intentResult.getContents(),Toast.LENGTH_LONG).show();
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
                        showLoginError(t.getMessage());
                    }
                });

            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //Mostrar mensaje de error
    private void showLoginError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }

    private boolean isOnline(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }


    /* SCANNER

    public void ScannerQR(View view){
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado del escaner");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        scannerView.resumeCameraPreview(this);
    }
    */
}
