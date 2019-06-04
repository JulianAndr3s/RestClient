package com.example.restclient.client;

import android.widget.ListView;

import com.example.restclient.adapter.PersonaAdapter;
import com.example.restclient.model.Persona;

public interface PersonaSource {
    void listAll(ListView listViewPersonas);

    void insert(Persona persona);

    PersonaAdapter getPersonaAdapter();

}
