package com.example.constanza.tingoidapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.constanza.tingoidapp.api.TingoApi;
import com.example.constanza.tingoidapp.api.model.ApiError;
import com.example.constanza.tingoidapp.api.model.User;
import com.example.constanza.tingoidapp.api.model.qrBody;
import com.example.constanza.tingoidapp.prefs.SessionPrefs;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
   // Typeface face =Typeface.createFromAsset(getAssets(),"fonts/Druchilla.ttf");
    //txtV.setTypeface(face);
   // private ZXingScannerView scannerView;
    private String user_email;
    private String user_name;

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
        Button mTinket = (Button) findViewById(R.id.buttonScanner);
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
        mTinket.setOnClickListener(new View.OnClickListener() {
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
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            if(intentResult.getContents()==null){
                Toast.makeText(this, "Has cancelado el scaner",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,intentResult.getContents(),Toast.LENGTH_LONG).show();
                //intentResult.getContents(): indica el contenido de los escaneado
                /* CONEXION A LA API
                String [] result = intentResult.getContents().split(" ");
                String empresa, id;
                empresa = result[0];
                id = result[1];
                Call<User> requestCall = mTingoApi.requestQR(new qrBody(empresa,id));
                requestCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (!response.isSuccessful()) {
                            String error;
                            if (response.errorBody()
                                    .contentType()
                                    .subtype()
                                    .equals("application/json")) {
                                ApiError apiError = ApiError.fromResponseBody(response.errorBody());
                                error = apiError.getMessage();
                                //Log.d("LoginActivity",  error);
                            } else {
                                error = response.message();
                            }
                            showLoginError(error);
                            return;
                        }
                        else{
                            Toast.makeText(this,"Tu tinket ha sido guardada exitosamente", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        showLoginError(t.getMessage());
                    }
                });
                */
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
