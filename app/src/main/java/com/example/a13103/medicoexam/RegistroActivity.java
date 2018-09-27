package com.example.a13103.medicoexam;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.text.DateFormat;
import java.util.ArrayList;



import java.util.Calendar;
import java.util.regex.Pattern;


public class RegistroActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText edtpass1, edtpass2, email, edtCorreo;

    String fechaNacimiento = "0";
    private int dia, mes, ano;
    TextView efecha;
    private ArrayList IdComite = new ArrayList();
    private ArrayList NombreComite = new ArrayList();

    private ArrayList Disponible = new ArrayList();
    String codigoComite = "0";
    Spinner spnCategoria;
    String estadoDisponible = "no";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        efecha = findViewById(R.id.showDate);
        spnCategoria = findViewById(R.id.spnCategoria);
        edtCorreo = findViewById(R.id.edCorreoR);
        edtpass1 = findViewById(R.id.edPassword);
        edtpass2 = findViewById(R.id.edPassword2);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ObtenerDatosSpinner();
            }
        });
    }


    public String ObtenerCorreo() {
        return edtCorreo.getText().toString();
    }


    public String ObtenerFecha() {
        return efecha.getText().toString();
    }

    public String ObtenerPass1() {
        return edtpass1.getText().toString();
    }

    public String ObtenerPass2() {
        return edtpass2.getText().toString();
    }


    public void ObtenerDatosSpinner() {
        IdComite.clear();
        NombreComite.clear();

        String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/ComiteControlador.php?op=1";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        IdComite.add(jsonArray.getJSONObject(i).getString("Id"));
                        NombreComite.add(jsonArray.getJSONObject(i).getString("Nombre"));


                    }
                    spnCategoria.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, NombreComite));
                    spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            codigoComite = String.valueOf(IdComite.get(i));

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                } catch (Exception ex) {
                    Toast.makeText(RegistroActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistroActivity.this, "ERROR DE CODIGO", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }


    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void Agregar(View view) {


        if (Integer.parseInt(codigoComite) == 0) {
            Toast.makeText(this, "Seleccione algun comite", Toast.LENGTH_SHORT).show();
        } else if (estadoDisponible.equals("no")) {
            Toast.makeText(this, "Verificar Usuario", Toast.LENGTH_SHORT).show();
        } else if (fechaNacimiento.equals("0")) {
            Toast.makeText(this, "Seleccione Una Fecha de Nacimiento", Toast.LENGTH_SHORT).show();
        } else if (ObtenerPass1().equals("")) {
            Toast.makeText(this, "Digite una contraseña", Toast.LENGTH_SHORT).show();
        } else if (!ObtenerPass1().equals(ObtenerPass2())) {
            Toast.makeText(this, "La Contraseña no coincide", Toast.LENGTH_SHORT).show();
        } else {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Confirmar la operación");
            builder.setTitle("Mensaje...");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                         new    Task().execute();


                            ;






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


    public void Fecha(View view) {

        android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }


    public void Verificar(View view) {


        if (ObtenerCorreo().equals("") || ObtenerCorreo().equals(" ")) {
            Toast.makeText(this, "Digite un Correo", Toast.LENGTH_SHORT).show();
        } else if (!validarEmail(ObtenerCorreo())) {

            Toast.makeText(this, "Email no válido", Toast.LENGTH_SHORT).show();

        } else {

            Disponible.clear();
            // String URL = "http://192.168.56.1/medico/CONTROLADOR/UsuarioControlador.php?op=2&usu="+ObtenerCorreo();
            String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/UsuarioControlador.php?op=2&usu=" + ObtenerCorreo();
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        JSONArray jsonArray = new JSONArray(response);

                        estadoDisponible = jsonArray.getJSONObject(0).getString("disponible");


                        if (estadoDisponible.equals("si")) {


                            Toast.makeText(RegistroActivity.this, "Disponible ", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(RegistroActivity.this, "Este Correo esta Siendo Usado", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception ex) {
                        Toast.makeText(RegistroActivity.this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegistroActivity.this, "Error en el codigo broh ", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);


        }


    }


    public void Cambiar(View view) {

        estadoDisponible = "no";
    }






    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);


        String currentDateString = DateFormat.getDateInstance().format(c.getTime());

        fechaNacimiento = year + "-" + twoDigits(month + 1) + "-" + twoDigits(dayOfMonth);


        efecha.setText(currentDateString);
    }

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    public void Cancelar(View view) {
        Intent p = new Intent(this, LoginActivity.class);
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





    public class Task extends AsyncTask<Void,Void,Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(RegistroActivity.this, "","Cargando...");
        }
        String    estado1;
        @Override
        protected Void doInBackground(Void... voids) {

                final String url1 = getString(R.string.base_uri);
                String url2 = "medico/CONTROLADOR/UsuarioControlador.php?op=3&" +
                        "idComite=" + codigoComite + "&fecha=" + fechaNacimiento + "&email=" + ObtenerCorreo() + "&password=" + ObtenerPass1();

                String URL = url1+url2;

                RequestQueue queue = Volley.newRequestQueue(getApplication());



                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {




                    @Override
                    public void onResponse(String response) {





                        try {

                            JSONArray jsonArray = new JSONArray(response);

                                estado1 = jsonArray.getJSONObject(0).getString("estado");



                        } catch (Exception ex) {
                            Toast.makeText(RegistroActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistroActivity.this, "ERROR DE CODIGO", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(stringRequest);




            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();


            if (estado1 == "failed"){

                Toast.makeText(RegistroActivity.this, "Error de Registro", Toast.LENGTH_SHORT).show();


            }
            else if (estado1 == ""){
                Toast.makeText(RegistroActivity.this, "Demoro en coger datos", Toast.LENGTH_SHORT).show();
            }
            else {


                Intent p = new Intent(getApplication(), LoginActivity.class);
                Toast.makeText(RegistroActivity.this, "Se Registro ... Verifique su correo", Toast.LENGTH_SHORT).show();


                startActivity(p);

                finish();
            }

        }
    }

}
