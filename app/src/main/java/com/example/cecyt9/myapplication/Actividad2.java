package com.example.cecyt9.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Actividad2 extends Activity {

    String nombre = "", fecha = "", hora = "", apellido = "", edad = "", eMail = "", tarjetaCredito = "", codigo = "";
    int personas = 0;
    TextView muestraDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad2);

        muestraDatos = (TextView) findViewById(R.id.muestraDatos);

        Bundle recibe = new Bundle();
        recibe = this.getIntent().getExtras();

        nombre = recibe.getString("nombre");
        personas = recibe.getInt("personas");
        fecha = recibe.getString("fecha");
        hora = recibe.getString("hora");
        apellido = recibe.getString("apellido");
        edad = recibe.getString("edad");
        eMail = recibe.getString("eMail");
        tarjetaCredito = recibe.getString("tarjetaCredito");
        codigo = recibe.getString("codigo");

        muestraDatos.setText("RESERVACIÓN A:\n" +
                "Nombre completo:\n" + nombre +" "+apellido+ "\n" +
                "Edad: "+edad+" años \n"+
                "Email:"+ eMail +"\n"+
                "Tarjeta Credito: "+tarjetaCredito+" "+codigo+"\n"+
                "Personas: "+personas+"\n" +
                "Fecha: " + fecha + "\n" +
                "Hora: " + hora + "\n");
    }

    public void hacerOtraReserva(View v) {
        Intent envia = new Intent(this, MainActivity.class);
        Bundle datos = new Bundle();
        datos.putString("nombre", nombre);
        datos.putString("apellido", apellido);
        datos.putString("edad", edad);
        datos.putString("eMail", eMail);
        datos.putString("tarjetaCredito", tarjetaCredito);
        datos.putString("codigo", codigo);
        datos.putInt("personas", personas);
        datos.putString("fecha", fecha);
        datos.putString("hora", hora);
        envia.putExtras(datos);
        finish();
        startActivity(envia);
    }

}