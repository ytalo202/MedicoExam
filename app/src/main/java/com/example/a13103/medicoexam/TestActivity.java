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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class TestActivity extends AppCompatActivity {



    String idArternativa1,resumen1="I";

    String idArternativa2,resumen2="I";
    String idArternativa3,resumen3="I";

    String idArternativa4,resumen4="I";

    TextView p1,p2,p3,p4;
    TextView titulo;
    ArrayList<PreguntasModel> elementosPreguntas = new ArrayList<>();;
    ArrayList<PreguntasModel> elementosAlternativas = new ArrayList<>();;

    ArrayList<PreguntasModel> elementosAlternativas2 = new ArrayList<>();;
    ArrayList<PreguntasModel> elementosAlternativas3 = new ArrayList<>();
    ArrayList<PreguntasModel> elementosAlternativas4 = new ArrayList<>();
    RadioButton rb1,rb2,rb3,rb4;
    RadioButton rb21,rb22,rb23,rb24;
    RadioButton rb31,rb32,rb33,rb34;
    RadioButton rb41,rb42,rb43,rb44;



    static final String PREFERENCIAS_COMPARTIDAD= "preferenciaCompartida";

    static final String ID_USUARIO ="IdUsuario";

    static final String ID_CURSO ="IdCurso";

    static final String  ID_TEMA="IdTema";
    static final String  NOMBRE_TEMA="NombreTema";
    static final String CORREO_USUARIO ="CorreoUsuario";
    String idTema;
    int nota = 0;
    String idAlumno;
    String idR;

    int cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Bundle mBundle = getIntent().getExtras();
        idR = mBundle.getString("idRelacion");
        cont = Integer.parseInt(mBundle.getString("cont"));



        SharedPreferences configuracion = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);

        idTema = configuracion.getString(ID_TEMA,"0");
        idAlumno = configuracion.getString(ID_USUARIO,"0");
        String nombreTema = configuracion.getString(NOMBRE_TEMA,"0");
        titulo = findViewById(R.id.txtTitulo);

        titulo.setText(nombreTema);
        p1 =  findViewById(R.id.txtP1);
        p2 = findViewById(R.id.txtP2);
        p3 =  findViewById(R.id.txtP3);

        p4 =  findViewById(R.id.txtP4);


        rb1 = findViewById(R.id.radio_one);
        rb2 = findViewById(R.id.radio_two);
        rb3 = findViewById(R.id.radio_three);
        rb4 = findViewById(R.id.radio_four);

        rb21 = findViewById(R.id.radio_one2);
        rb22 = findViewById(R.id.radio_two2);
        rb23 = findViewById(R.id.radio_three2);
        rb24 = findViewById(R.id.radio_four2);

        rb31 = findViewById(R.id.radio_one3);
        rb32 = findViewById(R.id.radio_two3);
        rb33 = findViewById(R.id.radio_three3);
        rb34 = findViewById(R.id.radio_four3);


        rb41 = findViewById(R.id.radio_one4);
        rb42 = findViewById(R.id.radio_two4);
        rb43 = findViewById(R.id.radio_three4);
        rb44 = findViewById(R.id.radio_four4);




        new Handler().post(new Runnable() {
                               @Override
                               public void run() {
                                   new CargarPreguntas().execute();





                                   final RadioGroup group= findViewById(R.id.radioGroup);
                                   group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                       @Override
                                       public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                           int id = group.getCheckedRadioButtonId();

                                           switch (id) {
                                               case R.id.radio_one:
                                                   idArternativa1= elementosAlternativas.get(0).idAlternativa.toString();
                                                   resumen1= elementosAlternativas.get(0).resumen.toString();


                       /* Toast.makeText(TestActivity.this, "p1 Selected Radio Button: " + 1 + ".. "+idArternativa1
                                        +" .."+resumen1,
                                Toast.LENGTH_SHORT).show();*/

                                                   break;
                                               case R.id.radio_two:
                                                   idArternativa1= elementosAlternativas.get(1).idAlternativa.toString();
                                                   resumen1= elementosAlternativas.get(1).resumen.toString();

                      /*    Toast.makeText(TestActivity.this, "p1 Selected Radio Button: " + 2 + ".. "+idArternativa1
                                        +" .."+resumen1,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;
                                               case R.id.radio_three:
                                                   idArternativa1= elementosAlternativas.get(2).idAlternativa.toString();
                                                   resumen1= elementosAlternativas.get(2).resumen.toString();


                      /*    Toast.makeText(TestActivity.this, "p1 Selected Radio Button: " + 3 + ".. "+idArternativa1
                                        +" .."+resumen1,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;
                                               case R.id.radio_four:
                                                   idArternativa1= elementosAlternativas.get(3).idAlternativa.toString();
                                                   resumen1= elementosAlternativas.get(3).resumen.toString();


                         /* Toast.makeText(TestActivity.this, "p1 Selected Radio Button: " + 4+ ".. "+idArternativa1
                                        +" .."+resumen1,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;




                                               default:
                                                   // Your code
                                                   break;
                                           }
                                       }
                                   });




                                   final RadioGroup group2= findViewById(R.id.radioGroup2);
                                   group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                       @Override
                                       public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                           int id = group2.getCheckedRadioButtonId();

                                           switch (id) {
                                               case R.id.radio_one2:

                                                   idArternativa2= elementosAlternativas2.get(0).idAlternativa.toString();
                                                   resumen2= elementosAlternativas2.get(0).resumen.toString();


                        /*  Toast.makeText(TestActivity.this, "p2 Selected Radio Button: " + 1 + ".. "+idArternativa2
                                        +" .."+resumen2,
                                Toast.LENGTH_SHORT).show();*/

                                                   break;
                                               case R.id.radio_two2:

                                                   idArternativa2= elementosAlternativas2.get(1).idAlternativa.toString();
                                                   resumen2= elementosAlternativas2.get(1).resumen.toString();


                        /*  Toast.makeText(TestActivity.this, "p2 Selected Radio Button: " + 2 + ".. "+idArternativa2
                                        +" .."+resumen2,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;
                                               case R.id.radio_three2:
                                                   idArternativa2= elementosAlternativas2.get(2).idAlternativa.toString();
                                                   resumen2= elementosAlternativas2.get(2).resumen.toString();


                       /*   Toast.makeText(TestActivity.this, "p2 Selected Radio Button: " + 3 + ".. "+idArternativa2
                                        +" .."+resumen2,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;
                                               case R.id.radio_four2:
                                                   idArternativa2= elementosAlternativas2.get(3).idAlternativa.toString();
                                                   resumen2= elementosAlternativas2.get(3).resumen.toString();


                       /*   Toast.makeText(TestActivity.this, "p2 Selected Radio Button: " + 4 + ".. "+idArternativa2
                                        +" .."+resumen2,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;
                                               default:
                                                   // Your code
                                                   break;
                                           }
                                       }
                                   });




                                   final RadioGroup group3= findViewById(R.id.radioGroup3);
                                   group3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                       @Override
                                       public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                           int id = group3.getCheckedRadioButtonId();

                                           switch (id) {
                                               case R.id.radio_one3:
                                                   idArternativa3= elementosAlternativas3.get(0).idAlternativa.toString();
                                                   resumen3= elementosAlternativas3.get(0).resumen.toString();


                       /*   Toast.makeText(TestActivity.this, "p3 Selected Radio Button: " + 1 + ".. "+idArternativa3
                                        +" .."+resumen3,
                                Toast.LENGTH_SHORT).show();*/

                                                   break;
                                               case R.id.radio_two3:
                                                   idArternativa3= elementosAlternativas3.get(1).idAlternativa.toString();
                                                   resumen3= elementosAlternativas3.get(1).resumen.toString();


                       /*   Toast.makeText(TestActivity.this, "p3 Selected Radio Button: " + 2 + ".. "+idArternativa3
                                        +" .."+resumen3,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;
                                               case R.id.radio_three3:
                                                   idArternativa3= elementosAlternativas3.get(2).idAlternativa.toString();
                                                   resumen3= elementosAlternativas3.get(2).resumen.toString();


                        /*  Toast.makeText(TestActivity.this, "p3 Selected Radio Button: " + 3 + ".. "+idArternativa3
                                        +" .."+resumen3,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;
                                               case R.id.radio_four3:
                                                   idArternativa3= elementosAlternativas3.get(3).idAlternativa.toString();
                                                   resumen3= elementosAlternativas3.get(3).resumen.toString();

                       /*   Toast.makeText(TestActivity.this, "p3 Selected Radio Button: " + 4+ ".. "+idArternativa3
                                        +" .."+resumen3,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;
                                               default:
                                                   // Your code
                                                   break;
                                           }
                                       }
                                   });





                                   final RadioGroup group4= findViewById(R.id.radioGroup4);
                                   group4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                       @Override
                                       public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                           int id = group4.getCheckedRadioButtonId();

                                           switch (id) {
                                               case R.id.radio_one4:
                                                   idArternativa4= elementosAlternativas4.get(0).idAlternativa.toString();
                                                   resumen4= elementosAlternativas4.get(0).resumen.toString();

                     /*     Toast.makeText(TestActivity.this, "p4 Selected Radio Button: " + 1 + ".. "+idArternativa4
                                        +" .."+resumen4,
                                Toast.LENGTH_SHORT).show();*/

                                                   break;
                                               case R.id.radio_two4:
                                                   idArternativa4= elementosAlternativas4.get(1).idAlternativa.toString();
                                                   resumen4= elementosAlternativas4.get(1).resumen.toString();


                       /*   Toast.makeText(TestActivity.this, "p4 Selected Radio Button: " + 2 + ".. "+idArternativa4
                                        +" .."+resumen4,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;
                                               case R.id.radio_three4:
                                                   idArternativa4= elementosAlternativas4.get(2).idAlternativa.toString();
                                                   resumen4= elementosAlternativas4.get(2).resumen.toString();


                       /*   Toast.makeText(TestActivity.this, "p4 Selected Radio Button: " + 3 + ".. "+idArternativa4
                                        +" .."+resumen4,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;
                                               case R.id.radio_four4:
                                                   idArternativa4= elementosAlternativas4.get(3).idAlternativa.toString();
                                                   resumen4= elementosAlternativas4.get(3).resumen.toString();

                      /*    Toast.makeText(TestActivity.this, "p4 Selected Radio Button: " + 4+ ".. "+idArternativa4
                                        +" .."+resumen4,
                                Toast.LENGTH_SHORT).show();*/
                                                   break;
                                               default:
                                                   // Your code
                                                   break;
                                           }
                                       }
                                   });




                               }
                           }
        );
    }
















    public void CargarPreguntas1(String id)
    {


        elementosAlternativas.clear();

        String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoPreguntaControlador.php?op=2&codPreg="+id;

        // String URL = "http://192.168.56.1/medico/CONTROLADOR/RelacionAlumnoPreguntaControlador.php?op=2&codPreg="+id;
        // String URL = "http://192.168.1.3/FUTBOL/CONTROLADOR/CampeonatoControlador.php?op=1";
        RequestQueue queue = Volley.newRequestQueue(this);
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

                            pb.setIdAlternativa(jsonArray.getJSONObject(i).getString("Id"));
                            pb.setEnunciadoAlternativa(jsonArray.getJSONObject(i).getString("Enunciado"));
                            pb.setResumen(jsonArray.getJSONObject(i).getString("Resumen"));
                            // pb.setNum(i+1);


                            elementosAlternativas.add(pb);

                        }
                        else {


                            Toast.makeText(TestActivity.this,

                                    "Alternticas Cargadas insuficientes Comunicar a su administrador"
                                    , Toast.LENGTH_LONG).show();

                            Intent p = new Intent(getApplication(),TemasActivity.class);

                            startActivity(p);

                            finish();

                        }


                    }



                    rb1.setText(elementosAlternativas.get(0).getEnunciadoAlternativa().toString());
                    rb2.setText(elementosAlternativas.get(1).getEnunciadoAlternativa().toString());
                    rb3.setText(elementosAlternativas.get(2).getEnunciadoAlternativa().toString());
                    rb4.setText(elementosAlternativas.get(3).getEnunciadoAlternativa().toString());


                }catch (Exception ex)
                {
                    //    Toast.makeText(TestActivity.this, "ERROR: "+ex.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(TestActivity.this, "ERROR DE CODIGO", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }



    public void alternativaValidar( int v){


        Toast.makeText(TestActivity.this,

                "Alternticas Cargadas insuficientes Comunicar a su administrador"
                , Toast.LENGTH_LONG).show();

        Intent p = new Intent(getApplication(),TemasActivity.class);

        startActivity(p);

        finish();
    }





    public void CargarPregunta2(String id)
    {


        elementosAlternativas2.clear();
        String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoPreguntaControlador.php?op=2&codPreg="+id;


        //String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoPreguntaControlador.php?op=2&codPreg="+id;
        // String URL = "http://192.168.1.3/FUTBOL/CONTROLADOR/CampeonatoControlador.php?op=1";
        RequestQueue queue = Volley.newRequestQueue(this);
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

                            pb.setIdAlternativa(jsonArray.getJSONObject(i).getString("Id"));
                            pb.setEnunciadoAlternativa(jsonArray.getJSONObject(i).getString("Enunciado"));
                            pb.setResumen(jsonArray.getJSONObject(i).getString("Resumen"));
                            // pb.setNum(i+1);


                            elementosAlternativas2.add(pb);

                        }
                        else {
                            Toast.makeText(TestActivity.this,

                                    "Alternticas Cargadas insuficientes Comunicar a su administrador"
                                    , Toast.LENGTH_LONG).show();

                            Intent p = new Intent(getApplication(),TemasActivity.class);

                            startActivity(p);

                            finish();
                        }


                    }


                    rb21.setText(elementosAlternativas2.get(0).getEnunciadoAlternativa().toString());
                    rb22.setText(elementosAlternativas2.get(1).getEnunciadoAlternativa().toString());
                    rb23.setText(elementosAlternativas2.get(2).getEnunciadoAlternativa().toString());
                    rb24.setText(elementosAlternativas2.get(3).getEnunciadoAlternativa().toString());

                }catch (Exception ex)
                {
                    //   Toast.makeText(TestActivity.this, "ERROR: "+ex.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //    Toast.makeText(TestActivity.this, "ERROR DE CODIGO", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }





    public void CargarPregunta3(String id)
    {


        elementosAlternativas3.clear();
        String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoPreguntaControlador.php?op=2&codPreg="+id;

        // String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoPreguntaControlador.php?op=2&codPreg="+id;
        // String URL = "http://192.168.1.3/FUTBOL/CONTROLADOR/CampeonatoControlador.php?op=1";
        RequestQueue queue = Volley.newRequestQueue(this);
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

                            pb.setIdAlternativa(jsonArray.getJSONObject(i).getString("Id"));
                            pb.setEnunciadoAlternativa(jsonArray.getJSONObject(i).getString("Enunciado"));
                            pb.setResumen(jsonArray.getJSONObject(i).getString("Resumen"));
                            // pb.setNum(i+1);


                            elementosAlternativas3.add(pb);

                        }
                        else {
                            Toast.makeText(TestActivity.this,

                                    "Alternticas Cargadas insuficientes Comunicar a su administrador"
                                    , Toast.LENGTH_LONG).show();

                            Intent p = new Intent(getApplication(),TemasActivity.class);

                            startActivity(p);

                            finish();
                        }


                    }


                    rb31.setText(elementosAlternativas3.get(0).getEnunciadoAlternativa().toString());
                    rb32.setText(elementosAlternativas3.get(1).getEnunciadoAlternativa().toString());
                    rb33.setText(elementosAlternativas3.get(2).getEnunciadoAlternativa().toString());
                    rb34.setText(elementosAlternativas3.get(3).getEnunciadoAlternativa().toString());

                }catch (Exception ex)
                {
                    //     Toast.makeText(TestActivity.this, "ERROR: "+ex.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(TestActivity.this, "ERROR DE CODIGO", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }


    public void CargarPregunta4(String id)
    {


        elementosAlternativas3.clear();
        String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoPreguntaControlador.php?op=2&codPreg="+id;

        //String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoPreguntaControlador.php?op=2&codPreg="+id;
        // String URL = "http://192.168.1.3/FUTBOL/CONTROLADOR/CampeonatoControlador.php?op=1";
        RequestQueue queue = Volley.newRequestQueue(this);
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

                            pb.setIdAlternativa(jsonArray.getJSONObject(i).getString("Id"));
                            pb.setEnunciadoAlternativa(jsonArray.getJSONObject(i).getString("Enunciado"));
                            pb.setResumen(jsonArray.getJSONObject(i).getString("Resumen"));
                            // pb.setNum(i+1);


                            elementosAlternativas4.add(pb);

                        }
                        else {
                            Toast.makeText(TestActivity.this,

                                    "Alternticas Cargadas insuficientes Comunicar a su administrador"
                                    , Toast.LENGTH_LONG).show();

                            Intent p = new Intent(getApplication(),TemasActivity.class);

                            startActivity(p);

                            finish();

                        }


                    }


                    rb41.setText(elementosAlternativas4.get(0).getEnunciadoAlternativa().toString());
                    rb42.setText(elementosAlternativas4.get(1).getEnunciadoAlternativa().toString());
                    rb43.setText(elementosAlternativas4.get(2).getEnunciadoAlternativa().toString());
                    rb44.setText(elementosAlternativas4.get(3).getEnunciadoAlternativa().toString());

                }catch (Exception ex)
                {
                    //   Toast.makeText(TestActivity.this, "ERROR: "+ex.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(TestActivity.this, "ERROR DE CODIGO", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }



    public void EnviarTest(View view) {



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enviar Test");
        builder.setTitle("Mensaje...");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                new UpdateTest().execute();
                Intent p = new Intent(getApplication(),TemasActivity.class);

                startActivity(p);

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
















    public void RealizarTest(String idTema,String idAlumno, int nota)
    {

        String URL = "http://192.168.56.1/medico/CONTROLADOR/RelacionAlumnoTemaControlador.php?op=4&idTema=" +
                idTema+"&idAlumno="+idAlumno+"&nota="+nota;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    String respuesta = String.valueOf(response);

                    if(respuesta.contains("success"))
                    {


                        Intent p = new Intent(getApplication(),TemasActivity.class);


                        Toast.makeText(TestActivity.this, "SE REGISTRO El USURIO CON EXITO :)", Toast.LENGTH_SHORT).show();
                        startActivity(p);

                    }else
                    {
                        Toast.makeText(TestActivity.this, "NO SE PUDO REALIZAR LA OPERACIÓN", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception ex)
                {
                    Toast.makeText(TestActivity.this, "ERROR: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TestActivity.this, "ERROR DE CODIGO", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se enviaran las respuestas marcadas hasta el momento ..");
        builder.setTitle("Mensaje... ");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new UpdateTest().execute();
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






    private class CargarPreguntas extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {



                elementosPreguntas.clear();


                String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoPreguntaControlador.php?op=1&codTema="+idTema;
                // String URL = "http://192.168.1.3/FUTBOL/CONTROLADOR/CampeonatoControlador.php?op=1";
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

                                    pb.setIdPregunta(jsonArray.getJSONObject(i).getString("Id"));
                                    pb.setEnunciadoPregunta(jsonArray.getJSONObject(i).getString("Enunciado"));



                                    elementosPreguntas.add(pb);




                                }
                                else {
                                    Toast.makeText(TestActivity.this,

                                            "Este Teman no Cuenta con preguntas Cargadas"
                                            , Toast.LENGTH_LONG).show();

                                    finish();
                                }


                            }



                            p1.setText("1.- "+elementosPreguntas.get(0).getEnunciadoPregunta().toString());


                            CargarPreguntas1( elementosPreguntas.get(0).getIdPregunta().toString());


                            p2.setText("2.- "+elementosPreguntas.get(1).getEnunciadoPregunta().toString());


                            CargarPregunta2( elementosPreguntas.get(1).getIdPregunta().toString());

                            p3.setText("3.- "+elementosPreguntas.get(2).getEnunciadoPregunta().toString());


                            CargarPregunta3( elementosPreguntas.get(2).getIdPregunta().toString());


                            p4.setText("4.- "+elementosPreguntas.get(3).getEnunciadoPregunta().toString());


                            CargarPregunta4( elementosPreguntas.get(3).getIdPregunta().toString());


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

            progressDialog = ProgressDialog.show(TestActivity.this, "Diálogo en espera",
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



    private class UpdateTest extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {



            int n1=0,n2=0,n3=0,n4=0;

            if (resumen1.equals("C") )
            {

                n1=5;
            }

            if (resumen2.equals("C") )
            {

                n2=5;
            }

            if (resumen3.equals("C") )
            {

                n3=5;
            }

            if (resumen4.equals("C") )
            {

                n4=5;
            }

            nota = n1+n2+n3+n4;


            cont=  cont+1;
                String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoTemaControlador.php?op=6&idRelacion="
                        +idR+"&nota="+nota+"&c="+cont;

                RequestQueue queue = Volley.newRequestQueue(getApplication());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {


                            JSONArray jsonArray = new JSONArray(response);
                            String estado = jsonArray.getJSONObject(0).getString("estado");

                            if (estado.equals("success")) {




                                Toast.makeText(TestActivity.this, "Rindio el examen con Exito", Toast.LENGTH_SHORT).show();


                            }else
                            {
                                Toast.makeText(TestActivity.this, "NO SE PUDO REALIZAR LA OPERACIÓN", Toast.LENGTH_SHORT).show();


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

            progressDialog = ProgressDialog.show(TestActivity.this, "",
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
