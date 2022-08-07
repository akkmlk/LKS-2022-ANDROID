package com.lks.rumahsakit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText etNamaLengkap, etUsername, etAlamat, etPassword, etConfirmPassword;
    Button btnRegister;
    ImageButton btnMata, btnMataCp;
    String namaLengkap, username, alamat, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = findViewById(R.id.tvTitle);
        title.setText("MENU REGISTER");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNamaLengkap = findViewById(R.id.etNameLengkap);
        etUsername = findViewById(R.id.etUsername);
        etAlamat = findViewById(R.id.etAlamat);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnMata = findViewById(R.id.btnMata);
        btnMataCp = findViewById(R.id.btnMataCp);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCheck();
            }
        });

    }

    private void registerCheck() {
        namaLengkap = etNamaLengkap.getText().toString();
        username = etUsername.getText().toString();
        alamat = etAlamat.getText().toString();
        password = etPassword.getText().toString();
        confirmPassword = etConfirmPassword.getText().toString();

        if(namaLengkap.isEmpty() || username.isEmpty() || alamat.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            pesanGagal("Isi semua kolom!!");
        } else if (username.length() < 5) {
            pesanGagal("Username harus lebih dari 4 karakter!!");
        } else if (password.length() < 6) {
            pesanGagal("Password harus lebih dari 5 karakter!!");
        } else if(!confirmPassword.equals(password)){
            pesanGagal("Password dan Confirm Password tidak sama!!");
        }  else {
            registerSend();
        }
    }

    private void registerSend() {
        String url = getString(R.string.url)+"/register";
        String data = "nama_lengkap="+namaLengkap+"&username="+username+"&alamat="+alamat+"&password="+password+"&password_confirmation="+confirmPassword;
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
                            pesanSuccess("Register Berhasil, Silahkan login!!");
                        } else {
                            pesanGagal("Harap periksa koneksi internet!!");
                        }
                    }
                });

            }
        }).start();
    }

    private void pesanSuccess(String msg) {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(RegisterActivity.this);
        alertBuild.setTitle("Registrasi Berhasil")
                .setMessage(msg)
                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });
        AlertDialog alert = alertBuild.create();
        alert.show();
    }

    private void pesanGagal(String msg) {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(RegisterActivity.this);
        alertBuild.setTitle("Gagal Register")
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