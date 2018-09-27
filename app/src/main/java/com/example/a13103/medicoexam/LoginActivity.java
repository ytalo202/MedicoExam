package com.example.a13103.medicoexam;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {



    EditText pswd,usrusr;
    TextView sup,lin;

    EditText edtUser, edtPass;
    Button btnIngresar;
    TextView lblmensaje;


    static final String PREFERENCIAS_COMPARTIDAD= "preferenciaCompartida";

    static final String ID_USUARIO ="IdUsuario";

    static final String ID_COMITE ="IdComite";
    static final String CORREO_USUARIO ="CorreoUsuario";

    static final String PASSWORD_USUARIO ="PassWordUsuario";


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Quieres Salir de la aplicación");
        builder.setTitle("Mensaje...");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




       // sup =  findViewById(R.id.sup);

/*
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        lin.setTypeface(custom_font1);
        sup.setTypeface(custom_font);


        usrusr.setTypeface(custom_font);
        pswd.setTypeface(custom_font);

*/


        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);


    }


    public void Login(View view) {
        if (ObtenerUsuario().equals("")){
            Toast.makeText(this, "Digite Correo", Toast.LENGTH_SHORT).show();
        }
        else if(!validarEmail(ObtenerUsuario())){

            Toast.makeText(this, "Email no válido", Toast.LENGTH_SHORT).show();

        }


        else if(ObtenerPassword().equals("")){

            Toast.makeText(this, "Digite Contraseña", Toast.LENGTH_SHORT).show();

        }

        else {







             new TareaDialogo().execute();


        }
    }


    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    public String ObtenerUsuario() {
        return edtUser.getText().toString();
    }

    public String ObtenerPassword() {
        return edtPass.getText().toString();
    }




    public void NewCuenta(View view) {

        Intent p = new Intent(this,RegistroActivity.class);
        startActivity(p);
        finish();
    }

    private class TareaDialogo extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {


                String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/UsuarioControlador.php?op=1&usu=" + ObtenerUsuario() + "&pass=" + ObtenerPassword();
                RequestQueue queue = Volley.newRequestQueue(getApplication());


                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            String estado = jsonArray.getJSONObject(0).getString("estado");


                            if (estado.equals("success")) {


                                String isEmailConfirmed = jsonArray.getJSONObject(0).getString("isEmailConfirmed");

                                if (isEmailConfirmed.equals("1")) {

                                    String IdComite = jsonArray.getJSONObject(0).getString("IdComite");
                                    String idUsuario = jsonArray.getJSONObject(0).getString("Id");

                                    Toast.makeText(LoginActivity.this, "BIENVENIDO :D ", Toast.LENGTH_SHORT).show();



                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);




                                    SharedPreferences configuracion = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);
                                    //modificar el archivo utiliso un editor
                                    SharedPreferences.Editor editor = configuracion.edit();

                                    //ingreso la informacion
                                    editor.putString(ID_USUARIO,idUsuario);
                                    editor.putString(CORREO_USUARIO,ObtenerUsuario());

                                    editor.putString(PASSWORD_USUARIO,ObtenerPassword());
                                    editor.putString(ID_COMITE,IdComite);

                                    editor.commit();


                                    startActivity(intent);

                                    finish();}

                                else {
                                    Toast.makeText(LoginActivity.this, "Correo sin confirmar", Toast.LENGTH_SHORT).show();
                                }

                            }


                            else {
                                Toast.makeText(LoginActivity.this, "Usuario Incorrecto", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            Toast.makeText(LoginActivity.this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error en el codigo broh ", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(stringRequest);

            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(LoginActivity.this, "",
                    "Cargando...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            try {

                Thread.sleep(1 * 1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
