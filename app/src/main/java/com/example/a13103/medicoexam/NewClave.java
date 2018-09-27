package com.example.a13103.medicoexam;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class NewClave extends AppCompatActivity {

    Button btnCofirmar,btnValidar,btnCancelar;
    EditText edAcClave,ednewClave,ednewClave02;
    View textImput,textImput1,textImput2;
    String claveA="0";

    static final String PREFERENCIAS_COMPARTIDAD= "preferenciaCompartida";
    static final String PASSWORD_USUARIO ="PassWordUsuario";
    static final String ID_USUARIO ="IdUsuario";
    String idUsuario,claveActual,newClave2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_clave);




        btnCofirmar = findViewById(R.id.btnConfirmar);
        btnValidar = findViewById(R.id.btnValidarClave);
        btnCancelar = findViewById(R.id.btnClcancelar);
        edAcClave = findViewById(R.id.etClaveActual);
        ednewClave = findViewById(R.id.etNuevaClave);
        ednewClave02 = findViewById(R.id.etNuevaClave2);
       textImput = findViewById(R.id.etbo);
       textImput1 = findViewById(R.id.etbo1);
       textImput2 = findViewById(R.id.etbo2);


        ednewClave.setVisibility(View.GONE);
        ednewClave02.setVisibility(View.GONE);
        btnCofirmar.setVisibility(View.GONE);
        textImput1.setVisibility(View.GONE);
        textImput2.setVisibility(View.GONE);



        new Handler().post(new Runnable() {
            @Override
            public void run() {

                SharedPreferences configuracion = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);
                idUsuario = configuracion.getString(ID_USUARIO,"0");
                claveActual = configuracion.getString(PASSWORD_USUARIO,"0");
            }
        });


    }

    public String ObtenerPass1() {
        return ednewClave.getText().toString();
    }

    public String ObtenerPass2() {
        return ednewClave02.getText().toString();
    }


    public void ValidarClave(View view) {
   claveA = edAcClave.getText().toString();
        if (claveA.equals(claveActual)){

            ednewClave.setVisibility(View.VISIBLE);
            ednewClave02.setVisibility(View.VISIBLE);
            btnCofirmar.setVisibility(View.VISIBLE);
            textImput1.setVisibility(View.VISIBLE);
            textImput2.setVisibility(View.VISIBLE);


            btnValidar.setVisibility(View.GONE);
            edAcClave.setVisibility(View.GONE);
            textImput.setVisibility(View.GONE);

        }else {
            Toast.makeText(this, "Clave Incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

    public void ClaCancelar(View view) {

        Intent p = new Intent(this,MainActivity.class);
        Toast.makeText(this, "Acción Cancelada", Toast.LENGTH_SHORT).show();
        startActivity(p);

    }


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


    public void ConfirmarClave(View view) {

        if (ObtenerPass1().equals(ObtenerPass2())){


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Confirmar Cambios");
            builder.setTitle("Mensaje");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (claveActual.equals(ObtenerPass1())){

                        Toast.makeText(NewClave.this, "Usar una clave diferente", Toast.LENGTH_SHORT).show();
                    }
                    else {



                                new UpdatePass().execute();

                                Intent p = new Intent(getApplication(), MainActivity.class);
                                startActivity(p);
                                finish();






                    }
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

        else {
            Toast.makeText(this, "Las claves no coinciden", Toast.LENGTH_SHORT).show();
        }
    }









    private class UpdatePass extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {




                //   String URL = "http://192.168.56.1/medico/CONTROLADOR/UsuarioControlador.php?op=6&CodUsu="+idUsuario+"&pass="+ObtenerPass1();
                String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/UsuarioControlador.php?op=6&CodUsu="+idUsuario+"&pass="+ObtenerPass1();



                RequestQueue queue = Volley.newRequestQueue(getApplication());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONArray jsonArray = new JSONArray(response);
                            String estado = jsonArray.getJSONObject(0).getString("estado");

                            if (estado.equals("success")) {




                                SharedPreferences configuracion =getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);
                                //modificar el archivo utiliso un editor
                                SharedPreferences.Editor editor = configuracion.edit();

                                editor.putString(PASSWORD_USUARIO,ObtenerPass1());
                                editor.commit();


                                Toast.makeText(NewClave.this, "Clave cambiada exitosamente", Toast.LENGTH_SHORT).show();


                            }else
                            {
                                Toast.makeText(NewClave.this, "NO SE PUDO REALIZAR LA OPERACIÓN", Toast.LENGTH_SHORT).show();


                            }

                        }catch (Exception ex)
                        {
                            // Toast.makeText(TestActivity.this, "ERROR: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(TestActivity.this, "ERROR DE CODIGO", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(stringRequest);


            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(NewClave.this, "",
                    "Cargando...");
        }

        @Override
        protected void onPostExecute(Void s) {
            progressDialog.dismiss();
            //tvResultado.setText(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
