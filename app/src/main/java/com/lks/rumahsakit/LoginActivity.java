package com.lks.rumahsakit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnRegister;
    ImageButton btnMata;
    String username, password;

    LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        TextView title = findViewById(R.id.tvTitle);
        title.setText("LOGIN");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnMata = findViewById(R.id.btnMata);

        localStorage = new LocalStorage(LoginActivity.this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginCheck();
            }
        });

    }

    private void loginCheck() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if(username.isEmpty() || password.isEmpty()){
            pesanGagal("Harap isi semua kolom!!");
        } else {
            loginSend();
        }
    }

    private void loginSend() {
        String url = getString(R.string.url)+"/login";
        String data = "username="+username+"&password="+password;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http();
                http.setUrl(url);
                http.setMethod("POST");
                http.setData(data);
                String response = http.getResponse();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response != null){
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                String token = jsonObject.getString("access_token");
                                localStorage.setToken(token);
                                etUsername.setText("");
                                etPassword.setText("");
                                Intent intent = new Intent(LoginActivity.this, TransaksiActivity.class);
                                startActivity(intent);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        } else {
                            pesanGagal("Gagal, harap periksa koneksi!");
                        }
                    }
                });
            }
        }).start();
    }

    private void pesanGagal(String msg) {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(LoginActivity.this);
        alertBuild.setTitle("Gagal Login")
                .setMessage(msg)
                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alert = alertBuild.create();
        alert.show();
    }
}