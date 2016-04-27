package com.example.guichoma.breathalyzer;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Pareamento extends Fragment implements AdapterView.OnItemClickListener {

    static BluetoothAdapter btAdapter;
    ListView listView;
    protected List<BluetoothDevice> lista;
    private ProgressDialog dialog;
    private String TAG = "Pareamento";
    private static final int REQUEST_ENABLE_BT = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pareamento, null);
        listView = (ListView)view.findViewById(R.id.listView);
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION);
        getActivity().registerReceiver(mReceiver, filter);

        if(btAdapter.isEnabled()){
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(discoverableIntent, REQUEST_ENABLE_BT);
        }

        buscar();

        return view;
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        private int count;

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "intent: " + intent.getAction());

            if(BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
                Log.v(TAG, "ACTION_FOUND.equals(action)");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    lista.add(device);
                    Toast.makeText(context, "Encontrou: " + device.getName(), Toast.LENGTH_SHORT).show();
                    count++;
                }
            }
            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(intent.getAction())) {
                Log.v(TAG, "ACTION_DISCOVERY_STARTED.equals(action)");
                count = 0;
                Toast.makeText(context, "Busca iniciada", Toast.LENGTH_SHORT).show();
            }
            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())) {
                Log.v(TAG, "ACTION_DISCOVERY_FINISHED.equals(action)");
                Toast.makeText(context, "Busca finalizada: " + count + " dispositivos encontrados.",
                        Toast.LENGTH_SHORT).show();
                //dialog.dismiss();
                if(!lista.isEmpty())
                    updateLista();
            }
            if(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE.equals(intent.getAction())){
                buscar();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(btAdapter != null) {
            btAdapter.cancelDiscovery();
        }
        getActivity().unregisterReceiver(mReceiver);
    }

    protected void updateLista() {
        Log.v(TAG, "Entrou no updateLista; Lista:" + lista.get(0));
        List<String> nomes = new ArrayList<String>();
        for(BluetoothDevice device : lista) {
            boolean pareado = device.getBondState() == BluetoothDevice.BOND_BONDED;
            nomes.add(device.getName() + " - " + device.getAddress() + (pareado ? " (Pareado) " : ""));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int idx, long id) {
        BluetoothDevice device = lista.get(idx);
        try {
            createBond(device);
            /*Intent intent = new Intent(Conte.class);
            intent.putExtra(BluetoothDevice.EXTRA_DEVICE, device);
            startActivity(intent);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createBond(BluetoothDevice btDevice) throws Exception {
        Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
        Method createBondMethod = class1.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        returnValue.booleanValue();
    }

    private void buscar() {
        if(btAdapter.isDiscovering())
            btAdapter.cancelDiscovery();

        btAdapter.startDiscovery();
        while(!btAdapter.isDiscovering()){
            Log.v(TAG, "Não Procurando");
        }
        //dialog = ProgressDialog.show(getContext(), "Bluetooth", "Buscando aparelhos bluetooth", false, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(btAdapter != null) {
            lista = new ArrayList<BluetoothDevice>();
            //updateLista();
        }
    }
}
