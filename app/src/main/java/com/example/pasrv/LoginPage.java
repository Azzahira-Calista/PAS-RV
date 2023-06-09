package com.example.pasrv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.health.ProcessHealthStats;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Priority;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;

    TextView tvSignUp;
    EditText etUsername, etPassword;
    ProgressBar pbLoadBar;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

//        pbLoadBar = findViewById(R.id.pbLoadingBar);
//        pbLoadBar.setVisibility(View.GONE);

        tvSignUp = findViewById(R.id.tvSignUp);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
//                pbLoadBar.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);

                //api bwt login
                AndroidNetworking.post("https://mediadwi.com/api/latihan/login")
                        .addBodyParameter("username", username)
                        .addBodyParameter("password", password)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    boolean status = response.getBoolean("status");
                                    String message = response.getString("message");
                                    Toast.makeText(LoginPage.this, message, Toast.LENGTH_SHORT).show();
                                    if (status){
                                        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(EMAIL_KEY, "admin");
                                        editor.putString(PASSWORD_KEY, "admin");

                                        editor.apply();
                                        Intent login = new Intent(LoginPage.this, MainActivity.class);
                                        startActivity(login);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

//                                pbLoadBar.setVisibility(view.GONE);
                                btnLogin.setEnabled(true);
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(LoginPage.this, "Failed Login", Toast.LENGTH_SHORT).show();

//                                pbLoadBar.setVisibility(view.GONE);
                                btnLogin.setEnabled(true);
                            }
                        });

            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(LoginPage.this, SignUpPage.class);
                startActivity(signUp);
            }
        });
    }
}