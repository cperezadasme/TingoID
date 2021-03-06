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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.constanza.tingoidapp.api.model.ApiError;
import com.example.constanza.tingoidapp.api.TingoApi;
import com.example.constanza.tingoidapp.api.model.Handshaking;
import com.example.constanza.tingoidapp.api.model.LoginBody;
import com.example.constanza.tingoidapp.api.model.User;
import com.example.constanza.tingoidapp.prefs.SessionPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    //retrofit
    private Retrofit mRestAdapter;
    private TingoApi mTingoApi;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ImageView mLogoView;

    //PARA EL inicio de sesion
    private TextInputLayout mFloatLabelUser;
    private TextInputLayout mFloatLabelPassword;


    private String email; //nombre del usuario (correo)
    private String id_usuario;

    //private String userEmail;
    //private int avatar;
    private String csrf_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //conexion al servicio REST
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(TingoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //conexion a la api
        mTingoApi = mRestAdapter.create(TingoApi.class);


        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        ImageButton mEmailSignInButton = (ImageButton) findViewById(R.id.email_sign_in_button);
        ImageButton mSignUpButton = (ImageButton) findViewById(R.id.sign_up_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mFloatLabelUser = (TextInputLayout) findViewById(R.id.float_label_user);
        mFloatLabelPassword = (TextInputLayout) findViewById(R.id.float_label_password);
        mLogoView = (ImageView)findViewById(R.id.logo_view);


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if (!isOnline()) {
                        showLoginError(getString(R.string.error_network));
                        return false;
                    }
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                attemptLogin();
            }
        });

        //BOTON DE REGISTRO
        mSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                showSignUpScreen();
            }
        });



    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mFloatLabelUser.setError(null);
        mFloatLabelPassword.setError(null);

        // Store values at the time of the login attempt.
        email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mFloatLabelPassword.setError(getString(R.string.error_field_required));
            focusView = mFloatLabelPassword;
            cancel = true;
        }else if (!isPasswordValid(password)){
            mFloatLabelPassword.setError(getString(R.string.error_invalid_password));
            focusView = mFloatLabelPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mFloatLabelUser.setError(getString(R.string.error_field_required));
            focusView = mFloatLabelUser;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mFloatLabelUser.setError(getString(R.string.error_invalid_email));
            focusView = mFloatLabelUser;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
/*
            //handshaking
            Call <Handshaking> handshakingCall = mTingoApi.handshaking();
            handshakingCall.enqueue(new Callback<Handshaking>() {
                @Override
                public void onResponse(Call<Handshaking> call, Response<Handshaking> response) {
                    if (response.isSuccessful()){
                        csrf_token = response.body().getCsrf_token();
                    }
                }

                @Override
                public void onFailure(Call<Handshaking> call, Throwable t) {
                    showLoginError(t.getMessage());
                }
            });
*/
            Call<ResponseBody> loginCall = mTingoApi.login(csrf_token, new LoginBody(email,password));
            loginCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String json = null;
                    showProgress(false);
                    try {
                        try {
                            json = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        JSONObject response_json = new JSONObject(json);
                        String logged = response_json.getString("logged");
                        if (logged.equals("true")){
                            id_usuario = response_json.getString("id");
                            SessionPrefs.get(LoginActivity.this).saveUser(new User(email,id_usuario));
                            showMainScreen();

                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Cuenta incorrecta",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    /*
                    //mostrar progreso
                    showProgress(false);

                    //procesar errores

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

                    showMainScreen();
                    */
                    SessionPrefs.get(LoginActivity.this).saveUser(new User(email,id_usuario));

                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showProgress(false);
                    showLoginError(t.getMessage());
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        int visibility = show ? View.GONE : View.VISIBLE;
        mLogoView.setVisibility(visibility);
        mLoginFormView.setVisibility(visibility);
    }

    //Mostrar mensaje de error
    private void showLoginError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }

    //DONDE SE DIRIGE AL USUARIO UNA VEZ QUE INICIA SESION
    private void showMainScreen() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("usuario", email);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);
        mEmailView.setText(null);
        mPasswordView.setText(null);
        finish();
    }

    private void showSignUpScreen(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.putExtra("csrf_token", csrf_token);
        startActivity(intent);
        finish();

    }

    //Verificar conexion
    private boolean isOnline(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}


