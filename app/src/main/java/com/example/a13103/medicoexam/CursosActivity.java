package com.example.a13103.medicoexam;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class CursosActivity extends AppCompatActivity {



    ListView LSTVW;


    String idUsuario;
    static final String PREFERENCIAS_COMPARTIDAD= "preferenciaCompartida";

    static final String ID_USUARIO ="IdUsuario";

    static final String ID_RELACION_ALUMNO_CURSO ="IdRelacionAlumnoCurso";

    static final String ID_CURSO ="IdCurso";
    static final String CORREO_USUARIO ="CorreoUsuario";
String idRelacion="0";


    // static final  String FLAG = "CAMPEONATADO";


    ArrayList<RelacionCursoAlumno> elementos = new ArrayList<>();;

    AdaptadorCursos adaptadorCursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);


        SharedPreferences configuracion = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);
        idUsuario = configuracion.getString(ID_USUARIO,"0");

        LSTVW = findViewById(R.id.LSTVW);

        CargarListView(idUsuario);
    }




    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Quieres Salir de la aplicación");
        builder.setTitle("Esto es el titulo");
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


    public void CargarListView(String id)
    {


        elementos.clear();

        String URL = "http://192.168.56.1/medico/CONTROLADOR/RelacionAlumnoCursosControlador.php?op=1&codAlu="+id;
        // String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoCursosControlador.php?op=1&codAlu="+id;
        // String URL = "http://192.168.1.3/FUTBOL/CONTROLADOR/CampeonatoControlador.php?op=1";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    RelacionCursoAlumno pb;
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0; i<jsonArray.length(); i++) {

                        pb = new RelacionCursoAlumno();

                        String estado = jsonArray.getJSONObject(0).getString("estado");

                        if (estado.equals("success")) {

                            pb.setIdRelacion(jsonArray.getJSONObject(i).getString("IdRelacion"));
                            pb.setIdCurso(jsonArray.getJSONObject(i).getString("Id"));
                            pb.setNombreCurso(jsonArray.getJSONObject(i).getString("Nombre"));
                            pb.setResumen(jsonArray.getJSONObject(i).getInt("Resumen"));
                            pb.setPromedio(jsonArray.getJSONObject(i).getDouble("Promedio"));


                            elementos.add(pb);

                        }
                        else {
                            Toast.makeText(CursosActivity.this,

                                   "Usted no esta matriculado en Ningun Curso"
                                    , Toast.LENGTH_SHORT).show();
                        }


                    }

                    adaptadorCursos = new AdaptadorCursos(elementos, getApplication());


                    LSTVW.setAdapter(adaptadorCursos);


                    LSTVW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                            Toast.makeText(CursosActivity.this,

                                    //  elementos.get(i).getNombre()
                                    elementos.get(i).getIdCurso()
                                    , Toast.LENGTH_SHORT).show();





                            Intent intent = new Intent(CursosActivity.this, TemasActivity.class);



                            //accedo o creo el archivo de preferencias comprartidas
                            SharedPreferences configuracion = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);
                            //modificar el archivo utiliso un editor
                            SharedPreferences.Editor editor = configuracion.edit();

                            //ingreso la informacion
                            editor.putString(ID_CURSO,elementos.get(i).getIdCurso().toString());

                            editor.putString(ID_RELACION_ALUMNO_CURSO,elementos.get(i).getIdRelacion().toString());

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
                    Toast.makeText(CursosActivity.this, "ERROR: "+ex.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CursosActivity.this, "ERROR DE CODIGO", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }

    public void SesionCurso(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Quieres cerrar su cuenta");
        builder.setTitle("Esto es el titulo");
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
}
