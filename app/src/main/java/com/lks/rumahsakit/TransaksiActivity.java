package com.lks.rumahsakit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TransaksiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = findViewById(R.id.tvTitle);
        title.setText("MENU TRANSAKSI");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
    }
}