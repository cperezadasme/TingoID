package com.example.constanza.tingoidapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   // private ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonScanner).setOnClickListener(this);
        findViewById(R.id.buttonGenerate).setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.buttonScanner:
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonGenerate:
                Intent intent1 = new Intent(MainActivity.this, GenerateActivity.class);
                startActivity(intent1);
                break;
        }
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
