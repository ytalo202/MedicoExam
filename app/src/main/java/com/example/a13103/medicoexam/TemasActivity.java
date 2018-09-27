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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

public class TemasActivity extends AppCompatActivity {



    ListView LSTVW;




    String idCurso,idAlumno,idRelacionAlumnoCurso;
    static final String PREFERENCIAS_COMPARTIDAD= "preferenciaCompartida";

    static final String ID_USUARIO ="IdUsuario";

    static final String ID_CURSO ="IdCurso";

    static final String  ID_TEMA="IdTema";
    static final String  NOMBRE_TEMA="NombreTema";

    static final String CORREO_USUARIO ="CorreoUsuario";

    static final String ID_RELACION_ALUMNO_CURSO ="IdRelacionAlumnoCurso";

    // static final  String FLAG = "CAMPEONATADO";


    ArrayList<RelacionTemaAlumno> elementos = new ArrayList<>();;

    AdaptadorTemas adaptadorTemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temas);


        LSTVW = findViewById(R.id.LSTVTEMAS);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                SharedPreferences configuracion = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);

                idCurso = configuracion.getString(ID_CURSO,"0");

                idAlumno = configuracion.getString(ID_USUARIO,"0");

                idRelacionAlumnoCurso = configuracion.getString(ID_RELACION_ALUMNO_CURSO,"0");


                new CargarListTemas().execute();
            }
        },1000);



    }





    public void irPrici(View view) {

        Intent p = new Intent(this,MainActivity.class);
        startActivity(p);
        finish();
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


    private class CargarListTemas extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {





                elementos.clear();

                String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoTemaControlador.php?op=7&codCurso="+idCurso+"&idAlumno="+idAlumno+
                        "&idReAluCurso="+idRelacionAlumnoCurso;
                // String URL = "http://192.168.1.3/FUTBOL/CONTROLADOR/CampeonatoControlador.php?op=1";
                RequestQueue queue = Volley.newRequestQueue(getApplication());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            RelacionTemaAlumno pb;
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i=0; i<jsonArray.length(); i++) {

                                pb = new RelacionTemaAlumno();

                                String estado = jsonArray.getJSONObject(0).getString("estado");

                                if (estado.equals("success")) {

                                    pb.setIdTema(jsonArray.getJSONObject(i).getString("Id"));
                                    pb.setNombreTema(jsonArray.getJSONObject(i).getString("Nombre"));
                                    pb.setNota(jsonArray.getJSONObject(i).getString("Nota"));
                                    pb.setC(jsonArray.getJSONObject(i).getInt("c"));



                                    elementos.add(pb);

                                }
                                else {
                                    Toast.makeText(TemasActivity.this,

                                            "Este Curso no cuenta aun con temas"
                                            , Toast.LENGTH_SHORT).show();
                                }


                            }

                            adaptadorTemas = new AdaptadorTemas(elementos, getApplication());


                            LSTVW.setAdapter(adaptadorTemas);


                            LSTVW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {




                                    Intent intent = new Intent(TemasActivity.this, TemaDetalleActivity.class);



                                    //accedo o creo el archivo de preferencias comprartidas
                                    SharedPreferences configuracion = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);
                                    //modificar el archivo utiliso un editor
                                    SharedPreferences.Editor editor = configuracion.edit();

                                    //ingreso la informacion
                                    editor.putString(ID_TEMA,elementos.get(i).getIdTema().toString());

                                    editor.putString(NOMBRE_TEMA,elementos.get(i).getNombreTema().toString());

                                    //editor.putBoolean(FLAG,true);

                                    //confirmo el ingreso de informacion
                                    editor.commit();


                                    // intent.putExtra("IdCampeonato", elementos.get(i).getId().toString());
                                    //intent.putExtra("NombreCampeonato", elementos.get(i).getNombre().toString());




                                    startActivity(intent);

                                    finish();




                                }
                            });



                        }catch (Exception ex)
                        {
                            Toast.makeText(TemasActivity.this, "ERROR: "+ex.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TemasActivity.this, "ERROR DE CODIGO", Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(stringRequest);

            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(TemasActivity.this, "Diálogo en espera",
                    "En espera por " + 3 + " segundos");
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
