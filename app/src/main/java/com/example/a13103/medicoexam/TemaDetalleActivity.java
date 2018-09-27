package com.example.a13103.medicoexam;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

public class TemaDetalleActivity extends AppCompatActivity {






    static final String PREFERENCIAS_COMPARTIDAD= "preferenciaCompartida";

    static final String ID_USUARIO ="IdUsuario";

    static final String ID_CURSO ="IdCurso";

    static final String  ID_TEMA="IdTema";

    static final String CORREO_USUARIO ="CorreoUsuario";

    String resul,idRelacion="0",cont="0";

    int RendirTest=0;

    int estado = 1;



    String estadoTest;
    String idTema;
    String idAlumno;
    Button test;
    ListView LSTV;


    AdaptadorVideos adaptadorVideos;

    private ArrayList Disponible  = new ArrayList();

    ArrayList<RelacionTemaAlumno> elementosTemaDetalle = new ArrayList<>();

    ArrayList<RelacionTemaAlumno> elementosTemaVideo = new ArrayList<>();


    TextView nom,descrip,ponen,ponen2,nota,numOp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tema_detalle);

        nom = findViewById(R.id.txtDetNombre);
        descrip = findViewById(R.id.txtDetDescrip);
         ponen= findViewById(R.id.txtDetPonente);
        ponen2 = findViewById(R.id.txtDetPonente2);
        nota = findViewById(R.id.txtDetNota);
        numOp = findViewById(R.id.txtNumOp);
        LSTV = findViewById(R.id.LSTVIDEOS);





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                SharedPreferences configuracion = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);

                idTema = configuracion.getString(ID_TEMA,"0");
                idAlumno = configuracion.getString(ID_USUARIO,"0");
                test= findViewById(R.id.btnTest);



                new CargarListVideo().execute();
                new ValidarTest().execute();



                new  VerificarTest().execute();
                new DetalleEncuentro().execute();
            }
        },1000);





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












    public void StartTest(View view) {

        if (!cont.equals("3"))
        {

        if (RendirTest==1){



            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Empezar Test");
            builder.setTitle("Mensaje");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(TemaDetalleActivity.this, TestActivity.class);
                    intent.putExtra("idRelacion",idRelacion);
                    intent.putExtra("cont",cont);
                    startActivity(intent);
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


        }else {
            Toast.makeText(TemaDetalleActivity.this,

                    "Este Teman no Cuenta con preguntas Cargadas consulte al administrador"
                    , Toast.LENGTH_LONG).show();
        }

        }
        else {
            Toast.makeText(TemaDetalleActivity.this, "Usted Alcanzo el limite de veces!! ", Toast.LENGTH_LONG).show();
        }
    }

















    public void IrPrincipal(View view) {

        Intent p = new Intent(this,MainActivity.class);
        startActivity(p);
        finish();
    }

    public void IrTemarios(View view) {

        Intent p = new Intent(this,TemasActivity.class);
        startActivity(p);
        finish();
    }









    private class DetalleEncuentro extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {


                elementosTemaDetalle.clear();


                //  String URL = "http://192.168.56.1/medico/CONTROLADOR/RelacionAlumnoTemaControlador.php?op=2&codTema="+idTema;
                String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoTemaControlador.php?op=2&codTema="+idTema;


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
                                    pb.setDescripcionCurso(jsonArray.getJSONObject(i).getString("Descripcion"));
                                    pb.setPonente(jsonArray.getJSONObject(i).getString("Ponente"));
                                    pb.setPonenteOp(jsonArray.getJSONObject(i).getString("PonenteOp"));
                                    elementosTemaDetalle.add(pb);

                                }
                                else {
                                    Toast.makeText(TemaDetalleActivity.this,

                                            "Curso Invalido Consulte al Administrador"
                                            , Toast.LENGTH_SHORT).show();
                                }



                                nom.setText(elementosTemaDetalle.get(0).getNombreTema());

                                String Descr = "<b>Descripción: </b>"+elementosTemaDetalle.get(0).getDescripcionCurso();
                                descrip.setText(Html.fromHtml(Descr));

                                String Pone = "<b>Ponente: </b>"+elementosTemaDetalle.get(0).getPonente();

                                ponen.setText(Html.fromHtml(Pone));
                                String Pone2 = "<b>Ponente: </b>"+elementosTemaDetalle.get(0).getPonenteOp();


                                ponen2.setText(Html.fromHtml(Pone2) );

                                if (elementosTemaDetalle.get(0).getPonenteOp().equals("")){
                                    ponen2.setVisibility(View.GONE);
                                }


                            }



                        }
                        catch (Exception ex)
                        {
                            Toast.makeText(TemaDetalleActivity.this, "ERROR: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TemaDetalleActivity.this, "ERROR DE CODIGO", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(stringRequest);



            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(TemaDetalleActivity.this, "",
                    "Cargando...");

        }

        @Override
        protected void onPostExecute(Void s) {
            progressDialog.dismiss();
            //tvResultado.setText(s);
            test.setEnabled(true);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }




    private class VerificarTest extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {





                Disponible.clear();
                //    String URL = "http://192.168.56.1/medico/CONTROLADOR/RelacionAlumnoTemaControlador.php?op=5&idTema=" +
                //            idTema + "&idAlumno=" + idAlumno;
                String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoTemaControlador.php?op=5&idTema=" +
                        idTema + "&idAlumno=" + idAlumno;

                RequestQueue queue = Volley.newRequestQueue(getApplication());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            estadoTest = jsonArray.getJSONObject(0).getString("estado").toString();



                            if (estadoTest.equals("actualizar")) {
                                idRelacion = jsonArray.getJSONObject(0).getString("Id").toString();
                                resul = jsonArray.getJSONObject(0).getString("Nota").toString();
                                cont = jsonArray.getJSONObject(0).getString("cont").toString();

                                estado = 2;


                                String Chance = "<b>Chances: </b>"+(3-Integer.parseInt( cont));


                                numOp.setText(Html.fromHtml(Chance));





                                if (!cont.equals("0")){
                                    test.setText("Re Start Test");
                                    String Nota="";



                                    Nota = "<b>Nota: </b>"+resul;
                                    nota.setText(Html.fromHtml(Nota));
                                    Toast.makeText(TemaDetalleActivity.this, "Usted Ya Tomo El examen", Toast.LENGTH_SHORT).show();

                                }




                                else {

                                    nota.setVisibility(View.GONE);
                                    Toast.makeText(TemaDetalleActivity.this, "Mire los Videos Para tomar el Test", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception ex) {

                            //   Toast.makeText(RegistroActivity.this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(RegistroActivity.this, "Error en el codigo broh ", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(stringRequest);






            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(TemaDetalleActivity.this, "",
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






    private class ValidarTest extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {

            String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoPreguntaControlador.php?op=1&codTema="+idTema;

                RequestQueue queue = Volley.newRequestQueue(getApplication());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            PreguntasModel pb;
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i=0; i<jsonArray.length(); i++) {

                                pb = new PreguntasModel();

                                String estado = jsonArray.getJSONObject(0).getString("estado");

                                if (estado.equals("success")) {
                                    RendirTest=1;
                                }



                            }





                        }catch (Exception ex)
                        {
                            // Toast.makeText(TestActivity.this, "ERROR: "+ex.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(TestActivity.this, "ERROR DE CODIGO", Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(stringRequest);







            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(TemaDetalleActivity.this, "",
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




    private class CargarListVideo extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {




                elementosTemaVideo.clear();

                //  String URL = "http://192.168.56.1/medico/CONTROLADOR/RelacionAlumnoTemaControlador.php?op=3&codTema="+idTema;
                String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoTemaControlador.php?op=3&codTema="+idTema;
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

                                    pb.setNumVideo(i+1);
                                    pb.setIdLink(jsonArray.getJSONObject(i).getString("link"));



                                    elementosTemaVideo.add(pb);

                                }
                                else {
                                    Toast.makeText(TemaDetalleActivity.this,

                                            "Usted no esta matriculado en Ningun Curso"
                                            , Toast.LENGTH_SHORT).show();
                                }


                            }

                            adaptadorVideos = new AdaptadorVideos(elementosTemaVideo, getApplication());


                            LSTV.setAdapter(adaptadorVideos);


                            LSTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    Intent intent = new Intent(TemaDetalleActivity.this, YoutubeActivity.class);

                                    intent.putExtra("Link", elementosTemaVideo.get(i).getIdLink().toString());
                                    startActivity(intent);

                                    finish();






                                }
                            });



                        }catch (Exception ex)
                        {
                            Toast.makeText(TemaDetalleActivity.this, "ERROR: "+ex.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TemaDetalleActivity.this, "ERROR DE CODIGO", Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(stringRequest);








            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(TemaDetalleActivity.this, "",
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

