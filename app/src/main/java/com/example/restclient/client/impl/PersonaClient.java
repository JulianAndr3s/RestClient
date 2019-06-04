package com.example.restclient.client.impl;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.restclient.adapter.PersonaAdapter;
import com.example.restclient.client.PersonaSource;
import com.example.restclient.model.Persona;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.Header;

public class PersonaClient implements PersonaSource {

    public static final String ENDPOINT = "http://192.168.137.1:8080/api";
    private Context context;
    private PersonaAdapter personaAdapter;

    public PersonaClient(Context context){
        this.context = context;
    }

    @Override
    public void listAll(final ListView listViewPersonas) {
        final AsyncHttpClient client = new AsyncHttpClient();
        String url = ENDPOINT.concat("/listar");
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == 200) {
                    try {
                        Gson gson = new Gson();
                        Persona[] personas = gson.fromJson(response.toString(), Persona[].class);
                        List<Persona> listaPersonas = Arrays.asList(personas);
                        PersonaAdapter personaAdapter = new PersonaAdapter(context,listaPersonas);
                        listViewPersonas.setAdapter(personaAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error", throwable.toString());
            }
        });
    }

    @Override
    public void insert(Persona persona) {
        try {
            final AsyncHttpClient client = new AsyncHttpClient();
            String url = ENDPOINT.concat("/insertar");
            Gson gson = new GsonBuilder().create();
            final JsonObject jsonObject = gson.toJsonTree(persona).getAsJsonObject();

            JSONObject jsonExportacion = new JSONObject(jsonObject.toString());
            StringEntity entity = new StringEntity(jsonExportacion.toString(), "UTF-8");
            client.setTimeout(300000);
            client.post(context, url, entity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (statusCode == 201) {
                        Toast.makeText(context, "La información se ha guardado con éxito", Toast.LENGTH_LONG).show();
                        Activity activity = (Activity) context;
                        activity.finish();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("", throwable.toString());
                }

            });

        } catch (JSONException e) {

        }
    }

    @Override
    public PersonaAdapter getPersonaAdapter() {
        return personaAdapter;
    }
}
