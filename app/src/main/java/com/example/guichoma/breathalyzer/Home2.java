package com.example.guichoma.breathalyzer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by GuiChoma on 16/03/2016.
 */
public class Home2 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_2, null);

        Button botaoParear = (Button) view.findViewById(R.id.botaoParear);

        return view;
    }
}
