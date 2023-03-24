package com.example.activdad8_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.activdad8_firebase.databinding.ActivityWellcomeBinding;

public class WellcomeActivity extends AppCompatActivity {

    private ActivityWellcomeBinding enlace;
    public static final String user="names";

    TextView txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enlace = ActivityWellcomeBinding.inflate(getLayoutInflater());
        setContentView(enlace.getRoot());

        txtUser = enlace.textser;
        String user = getIntent().getStringExtra("names");
        txtUser.setText("Bienvenido "+ user);

    }
}