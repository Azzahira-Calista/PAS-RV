package com.example.pasrv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUpPage extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String USERNAME_KEY = "username_key";
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;

    TextView tvLogin;
    EditText etEmail, etUsername2, etPassword2, full_name;
    ProgressBar LoadingBar;
    Button btnSignUp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        LoadingBar = findViewById(R.id.LoadingBar);
        LoadingBar.setVisibility(View.GONE);

        tvLogin = findViewById(R.id.tvLogin);
        etEmail = findViewById(R.id.etEmail);
        etUsername2 = findViewById(R.id.etUsername2);
        etPassword2 = findViewById(R.id.etPassword2);
        full_name = findViewById(R.id.full_name);


        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            //Pas di click btn APInya bakal nge load dan ngambil value dari teks field
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String username = etUsername2.getText().toString();
                String password = etPassword2.getText().toString();
                String fullName = full_name.getText().toString();
                LoadingBar.setVisibility(View.VISIBLE);
                btnSignUp.setVisibility(View.GONE);

                //Sebenernya ini cuma ngecek apakah teksnya udah keisi semua apa belom jadi mau pake gpp gak juga gpp
                if (!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                    //Ngambil API dari pak Dwi
                    AndroidNetworking.post("https://mediadwi.com/api/latihan/register-user")
                            .addBodyParameter("full_name", fullName)
                            .addBodyParameter("username", username)
                            .addBodyParameter("email", email)
                            .addBodyParameter("password", password)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                //Respon
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("TAG", "onResponse: " + response);

                                    LoadingBar.setVisibility(view.GONE);
                                    tvLogin.setVisibility(View.VISIBLE);

                                    //Ngeintent ke Login
                                    Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                @Override
                                public void onError(ANError anError) {

                                }
                            });

                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(login);
            }
        });
    }
}