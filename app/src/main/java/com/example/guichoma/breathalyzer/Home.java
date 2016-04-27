package com.example.guichoma.breathalyzer;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gc.materialdesign.views.ButtonRectangle;

public class Home extends AppCompatActivity {

    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
    static String TAG = "pilha";
    static String Verb = "Verb";
    ButtonRectangle parear;
    ButtonRectangle iniciar;
    ButtonRectangle grafico;
    Pareamento pareamento = new Pareamento();
    static BluetoothAdapter btAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        doDiscovery();
        if(!btAdapter.isEnabled()){

            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 300);
        }

        if(savedInstanceState == null) {
          /*  Home2 frag1 = new Home2();

            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.fragment, frag1, "tag1");
            fragmentTransaction.addToBackStack("pilha");
            fragmentTransaction.commit();*/
        }

        parear = (ButtonRectangle)findViewById(R.id.botaoParear);
        parear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Pareamento frag1 = new Pareamento();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, frag1, "tag2");
                fragmentTransaction.addToBackStack("pilha");
                fragmentTransaction.commit();
            }
        });

        iniciar = (ButtonRectangle)findViewById(R.id.botaoIniciar);
        iniciar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Iniciar frag1 = new Iniciar();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, frag1, "tag3");
                fragmentTransaction.addToBackStack("pilha");
                fragmentTransaction.commit();
            }
        });

        grafico = (ButtonRectangle)findViewById(R.id.botaoGraficos);
        grafico.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Pareamento frag1 = new Pareamento();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, frag1, "tag4");
                fragmentTransaction.addToBackStack("pilha");
                fragmentTransaction.commit();
            }
        });


    }

    public void doDiscovery() {
        int hasPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            //continueDoDiscovery();
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[]{
                        android.Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
    }


}
