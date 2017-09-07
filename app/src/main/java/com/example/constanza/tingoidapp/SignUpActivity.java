package com.example.constanza.tingoidapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private Retrofit mRestAdapter;
    private TingoApi mTingoApi;

    private ImageView mLogo;
    private TextView mlinkLogin;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPass;
    private EditText mName;

    private TextInputLayout mFloatEmail;
    private TextInputLayout mFloatName;
    private TextInputLayout mFloatPassword;
    private TextInputLayout mFloatConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //conexion al servicio REST
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(TingoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //conexion a la api
        mTingoApi = mRestAdapter.create(TingoApi.class);

        mFloatEmail = (TextInputLayout)findViewById(R.id.float_email);
        mFloatName = (TextInputLayout)findViewById(R.id.float_name);
        mFloatPassword = (TextInputLayout)findViewById(R.id.float_password);
        mFloatConfirmPass = (TextInputLayout)findViewById(R.id.float_confirm_password);

        mLogo = (ImageView)findViewById(R.id.logo);
        mEmail = (EditText)findViewById(R.id.email_sign_up);
        mName = (EditText)findViewById(R.id.name);
        mPassword = (EditText)findViewById(R.id.password_sign_up);
        mConfirmPass = (EditText)findViewById(R.id.confirm_password);
        mlinkLogin = (TextView)findViewById(R.id.login);
        Button mSignUpButton = (Button)findViewById(R.id.button_confirm_sign_up);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                attemptSignUp();
            }
        });



        mlinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogincreen();
            }
        });
    }

    private void attemptSignUp() {
        // Reset errors.
        mFloatEmail.setError(null);
        mFloatName.setError(null);
        mFloatPassword.setError(null);
        mFloatConfirmPass.setError(null);

        //Store values
        String email = mEmail.getText().toString();
        String name = mName.getText().toString();
        String password = mPassword.getText().toString();
        String confirmPass = mConfirmPass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mFloatPassword.setError(getString(R.string.error_field_required));
            focusView = mFloatPassword;
            cancel = true;
        } else if (!isPasswordValid(password, confirmPass)) {
            mFloatPassword.setError(getString(R.string.error_different_password));
            focusView = mFloatPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mFloatEmail.setError(getString(R.string.error_field_required));
            focusView = mFloatEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mFloatEmail.setError(getString(R.string.error_invalid_email));
            focusView = mFloatEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            mFloatName.setError(getString(R.string.error_field_required));
            focusView = mFloatName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);

            //hasta aqui corregido
            Call<User> signupCall = mTingoApi.signup(new SignUpBody(email,name, password));
            signupCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    //mostrar progreso
                    //showProgress(false);

                    //precesar errores
                    if (!response.isSuccessful()) {
                        String error;
                        if (response.errorBody()
                                .contentType()
                                .subtype()
                                .equals("application/json")) {
                            ApiError apiError = ApiError.fromResponseBody(response.errorBody());
                            error = apiError.getMessage();
                            Log.d("SignUpActivity", apiError.getDeveloperMessage());
                        } else {
                            error = response.message();
                        }
                        showLoginError(error);
                        return;
                    }

                    SessionPrefs.get(SignUpActivity.this).saveUser(response.body());

                    showMainScreen();
                }


                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    //showProgress(false);
                    showLoginError(t.getMessage());
                }
            });


        }
    }



    private void showLoginError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }

    private void showLogincreen(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void showMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private boolean isPasswordValid(String password, String confirmPass) {
        //TODO: Replace this with your own logic
        return password.length() >= 4 && password.equals(confirmPass);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    //Verificar conexion
    private boolean isOnline(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }


}
