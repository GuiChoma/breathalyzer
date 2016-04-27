package com.example.guichoma.breathalyzer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Iniciar extends Fragment {

    Spinner age;
    Spinner sex;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iniciar, null);

        Button botaoIniciar = (Button) view.findViewById(R.id.botaoIniciar);
        sex = (Spinner) view.findViewById(R.id.sex_spinner);
        age = (Spinner) view.findViewById(R.id.age_spinner);
        List<String> sexList = new ArrayList<String>();
        sexList.add("Homem");
        sexList.add("Mulher");

        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sexList);

        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.v("Iniciar", "sexAdapter: " + sexAdapter);
        sex.setAdapter(sexAdapter);

        List<String> ageList = new ArrayList<String>();
        for(int i = 18; i < 100; i++)
            ageList.add(String.valueOf(i));
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ageList);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(ageAdapter);

        return view;
    }
}
