package com.example.restclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.restclient.client.PersonaSource;
import com.example.restclient.client.impl.PersonaClient;
import com.example.restclient.model.Persona;

public class RegistroActivity extends AppCompatActivity {

    private EditText nombreTxt;
    private EditText apellidoTxt;
    private EditText telefonoTxt;
    private Button btnGuardar;
    private Persona persona;
    private PersonaSource personaSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        initComponents();
    }

    private void initComponents() {
        nombreTxt = findViewById(R.id.nombreTxt);
        apellidoTxt = findViewById(R.id.apellidoTxt);
        telefonoTxt = findViewById(R.id.telefonoTxt);
        btnGuardar = findViewById(R.id.btnGuardar);
        persona = new Persona();
        personaSource = new PersonaClient(this);
    }

    public void sendInfo(View view) {
        if(validate()){
            personaSource.insert(persona);
        }
    }

    private boolean validate() {
        boolean isValid = true;
        persona.setNombre(nombreTxt.getText().toString());
        if("".equals(nombreTxt.getText().toString())){
            nombreTxt.setError(getString(R.string.requerido));
            isValid = false;
        }
        persona.setApellido(apellidoTxt.getText().toString());
        if("".equals(apellidoTxt.getText().toString())){
            apellidoTxt.setError(getString(R.string.requerido));
            isValid = false;
        }
        persona.setTelefono(telefonoTxt.getText().toString());
        if("".equals(telefonoTxt.getText().toString())){
            telefonoTxt.setError(getString(R.string.requerido));
            isValid = false;
        }
        return isValid;
    }
}
