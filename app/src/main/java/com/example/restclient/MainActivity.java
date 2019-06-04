package com.example.restclient;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.restclient.adapter.PersonaAdapter;
import com.example.restclient.client.PersonaSource;
import com.example.restclient.client.impl.PersonaClient;
import com.example.restclient.model.Persona;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Persona> personas;
    private PersonaAdapter personaAdapter;
    private Button btnCargar;
    private ListView listViewPersonas;
    private EditText buscarTxt;
    private FloatingActionButton btnRegistrarPersona;
    private PersonaSource personaSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        buscarTxtWatcher();
    }

    private void buscarTxtWatcher() {
        buscarTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(personaAdapter != null){
                    personaAdapter.getFilter().filter(s);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void initComponents() {
        personas = new ArrayList<>();
        btnCargar = findViewById(R.id.btnCargar);
        listViewPersonas = findViewById(R.id.listViewPersonas);
        buscarTxt = findViewById(R.id.buscarTxt);
        btnRegistrarPersona = findViewById(R.id.btnRegistrarPersona);
        personaSource = new PersonaClient(this);
    }

    public void loadInfo(View view) {
        personaSource.listAll(listViewPersonas);
    }

    public void goToRegistro(View view) {
        Intent intent = new Intent(MainActivity.this,RegistroActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        personaSource.listAll(listViewPersonas);
    }
}
