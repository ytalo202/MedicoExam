package com.example.a13103.medicoexam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {


    static final String PREFERENCIAS_COMPARTIDAD= "preferenciaCompartida";

    static final String ID_USUARIO ="IdUsuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences configuracion = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences configuracion = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);
                String idUsuario = configuracion.getString(ID_USUARIO,"0");
                if (idUsuario.equals("0")){

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);

    }
}
