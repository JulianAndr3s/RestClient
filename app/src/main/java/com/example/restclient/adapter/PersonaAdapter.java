package com.example.restclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.restclient.R;
import com.example.restclient.model.Persona;

import java.util.ArrayList;
import java.util.List;

public class PersonaAdapter extends BaseAdapter implements Filterable {

    private List<Persona> personasIn;
    private List<Persona> personasOut;
    private LayoutInflater inflater;

    public PersonaAdapter(Context context, List<Persona> personasIn){
        this.personasIn = personasIn;
        personasOut = personasIn;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return personasOut.size();
    }

    @Override
    public Object getItem(int position) {
        return personasOut.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(personasOut.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.persona_cell_item,null);
            holder.nombreTxt = convertView.findViewById(R.id.nombreTxt);
            holder.apellidoTxt = convertView.findViewById(R.id.apellidoTxt);
            holder.telefonoTxt = convertView.findViewById(R.id.telefonoTxt);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nombreTxt.setText(getNombre(position));
        holder.apellidoTxt.setText(getApellido(position));
        holder.telefonoTxt.setText(getTelefono(position));

        return convertView;
    }

    private String getTelefono(int position) {
        return personasOut.get(position).getTelefono();
    }

    private String getApellido(int position) {
        return personasOut.get(position).getApellido();
    }

    private String getNombre(int position) {
        return personasOut.get(position).getNombre();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Persona> filteredArrList = new ArrayList<>();
                if (personasIn == null) {
                    personasIn = new ArrayList<>(personasOut);
                }

                if (constraint == null || constraint.length() == 0) {
                    results.count = personasIn.size();
                    results.values = personasIn;
                } else {
                    constraint = constraint.toString().toLowerCase();

                    for (int i = 0; i < personasIn.size(); i++) {
                        String data = personasIn.get(i).getNombre();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            Persona persona = personasIn.get(i);
                            filteredArrList.add(persona);
                        }
                    }
                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                personasOut = (List<Persona>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }


    class ViewHolder{
        TextView nombreTxt,apellidoTxt,telefonoTxt;
        ImageButton btnEdit,btnDelete;
    }
}
