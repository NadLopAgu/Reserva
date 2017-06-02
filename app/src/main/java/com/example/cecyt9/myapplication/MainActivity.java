package com.example.cecyt9.myapplication;

        import java.io.StringBufferInputStream;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.StringTokenizer;

        import android.os.Bundle;
        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.app.TimePickerDialog;
        import android.app.DatePickerDialog.OnDateSetListener;
        import android.app.TimePickerDialog.OnTimeSetListener;
        import android.content.Intent;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.SeekBar;
        import android.widget.TimePicker;
        import android.widget.SeekBar.OnSeekBarChangeListener;
        import android.widget.TextView;

        import org.w3c.dom.Text;

public class MainActivity extends Activity implements OnSeekBarChangeListener,
        OnClickListener, OnDateSetListener, OnTimeSetListener {

    EditText nombre;
    EditText apellido;
    EditText edad;
    EditText eMail;
    EditText tarjetaCredito;
    EditText codigo;
    TextView cuantasPersonas;
    Button fecha, hora;
    SeekBar barraPersonas;

    SimpleDateFormat horaFormato, fechaFormato;

    String nombreReserva = "";
    String numPersonas = "";
    String fechaSel = "", horaSel = "";
    Date fechaConv;
    String cuantasPersonasFormat = "";
    int personas = 1; // Valor por omision, al menos 1 persona tiene que reservar

    Calendar calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle datos = new Bundle();
        datos = this.getIntent().getExtras();
        if(datos != null)
        {
            TextView anterior;
            String nombre1;
            String apellido1;
            String edad1;
            String eMail1;
            String tarjetaCredito1;
            String codigo1;
            int personas1;
            String fecha1;
            String hora1;

            anterior = (TextView) findViewById(R.id.txtAnterior);
            nombre1 = datos.getString("nombre");
            apellido1 = datos.getString("apellido");
            edad1 = datos.getString("edad");
            eMail1 = datos.getString("eMail");
            tarjetaCredito1 = datos.getString("tarjetaCredito");
            codigo1 = datos.getString("codigo");
            personas1 = datos.getInt("personas");
            fecha1 = datos.getString("fecha");
            hora1 = datos.getString(("hora"));

            anterior.setText("ÚLTIMA RESERVACIÓN REALIZADA: \n" +
                    "Nombre completo:\n" + nombre1 +" "+apellido1+ "\n" +
                    "Edad: "+edad1+" años \n"+
                    "Email:"+ eMail1 +"\n"+
                    "Tarjeta Credito: "+tarjetaCredito1+" "+codigo1+"\n"+
                    "Personas: "+personas1+"\n" +
                    "Fecha: " + fecha1 + "\n" +
                    "Hora: " + hora1 + "\n"
            );
        }
        cuantasPersonas = (TextView) findViewById(R.id.cuantasPersonas);
        barraPersonas = (SeekBar) findViewById(R.id.personas);

        fecha = (Button) findViewById(R.id.fecha);
        hora = (Button) findViewById(R.id.hora);

        barraPersonas.setOnSeekBarChangeListener(this);

        nombre = (EditText) findViewById(R.id.nombre);
        apellido = (EditText) findViewById(R.id.apellido);
        edad = (EditText) findViewById(R.id.edad);
        eMail = (EditText) findViewById(R.id.eMail);
        tarjetaCredito = (EditText) findViewById(R.id.tarjetaCredito);
        codigo = (EditText) findViewById(R.id.codigo);


        cuantasPersonasFormat = cuantasPersonas.getText().toString();
        // cuantasPersonasFormat = "personas: %d";
        cuantasPersonas.setText("Personas: 1"); // condicion inicial

        // Para seleccionar la fecha y la hora
        Calendar fechaSeleccionada = Calendar.getInstance();
        fechaSeleccionada.set(Calendar.HOUR_OF_DAY, 12); // hora inicial
        fechaSeleccionada.clear(Calendar.MINUTE); // 0
        fechaSeleccionada.clear(Calendar.SECOND); // 0

        // formatos de la fecha y hora
        fechaFormato = new SimpleDateFormat(fecha.getText().toString());
        horaFormato = new SimpleDateFormat("HH:mm");
        // horaFormato = new SimpleDateFormat(hora.getText().toString());

        // La primera vez mostramos la fecha actual
        Date fechaReservacion = fechaSeleccionada.getTime();
        fechaSel = fechaFormato.format(fechaReservacion);
        fecha.setText(fechaSel); // fecha en el

        horaSel = horaFormato.format(fechaReservacion);
        // boton
        hora.setText(horaSel); // hora en el boton

        // Otra forma de ocupar los botones
        fecha.setOnClickListener(this);
        hora.setOnClickListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar barra, int progreso,
                                  boolean delUsuario) {

        numPersonas = String.format(cuantasPersonasFormat,
                barraPersonas.getProgress() + 1);
        personas = barraPersonas.getProgress() + 1; // este es el valor que se
        // guardara en la BD
        // Si no se mueve la barra, enviamos el valor personas = 1
        cuantasPersonas.setText(numPersonas);
    }

    @Override
    public void onStartTrackingTouch(SeekBar arg0) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar arg0) {
    }

    @Override
    public void onClick(View v) {
        if (v == fecha) {
            Calendar calendario = parseCalendar(fecha.getText(), fechaFormato);
            new DatePickerDialog(this, this, calendario.get(Calendar.YEAR),
                    calendario.get(Calendar.MONTH),
                    calendario.get(Calendar.DAY_OF_MONTH)).show();
        } else if (v == hora) {
            Calendar calendario = parseCalendar(hora.getText(), horaFormato);
            new TimePickerDialog(this, this,
                    calendario.get(Calendar.HOUR_OF_DAY),
                    calendario.get(Calendar.MINUTE), false) // /true = 24 horas
                    .show();
        }
    }

    private Calendar parseCalendar(CharSequence text,
                                   SimpleDateFormat fechaFormat2) {
        try {
            fechaConv = fechaFormat2.parse(text.toString());
        } catch (ParseException e) { // import java.text.ParsedExc
            throw new RuntimeException(e);
        }
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaConv);
        return calendario;
    }

    @Override
    public void onDateSet(DatePicker picker, int anio, int mes, int dia) {
        calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, anio);
        calendario.set(Calendar.MONTH, mes);
        calendario.set(Calendar.DAY_OF_MONTH, dia);

        fechaSel = fechaFormato.format(calendario.getTime());
        fecha.setText(fechaSel);

    }

    public void onTimeSet(TimePicker picker, int horas, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, horas);
        calendar.set(Calendar.MINUTE, minutos);

        horaSel = horaFormato.format(calendar.getTime());
        hora.setText(horaSel);
    }

    public void reserva(View v) {
        Intent envia = new Intent(this, Actividad2.class);
        Bundle datos = new Bundle();
        datos.putString("nombre", nombre.getText().toString().trim());
        datos.putInt("personas", personas);
        datos.putString("fecha", fechaSel);
        datos.putString("hora", horaSel);
        datos.putString("apellido", apellido.getText().toString().trim());
        datos.putString("edad", edad.getText().toString().trim());
        datos.putString("eMail", eMail.getText().toString().trim());
        datos.putString("tarjetaCredito", tarjetaCredito.getText().toString().trim());
        datos.putString("codigo", codigo.getText().toString().trim());
        envia.putExtras(datos);
        finish();
        startActivity(envia);
    }
}
