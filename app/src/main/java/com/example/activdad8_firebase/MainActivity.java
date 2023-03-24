package com.example.activdad8_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.activdad8_firebase.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding enlace;
    private EditText TextEmail, TextPassword;
    private Button btnRegistrar, btnLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enlace = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(enlace.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        TextEmail = enlace.TxtEmail;
        TextPassword = enlace.TxtPassword;
        btnRegistrar = enlace.botonRegistrar;
        btnLogin = enlace.botonLogin;



        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loguearUsuario();
            }
        });
    }

    private void loguearUsuario() {
        String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Se debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                                int pos = email.indexOf("@");
                                String user = email.substring(0,pos);
                            Toast.makeText(MainActivity.this, "Sesión iniciada", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplication(),WellcomeActivity.class);
                            intent.putExtra(WellcomeActivity.user,user);
                            startActivity(intent);


                        }else{
                                Toast.makeText(MainActivity.this, "No se iniciado la sesion", Toast.LENGTH_SHORT).show();
                            }
                        }
                });
    }



    private void registrarUsuario(){

        String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Se debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Se ha registrado el usuario", Toast.LENGTH_SHORT).show();
                        }else{
                            if(task.getException()instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(MainActivity.this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });



    }



}