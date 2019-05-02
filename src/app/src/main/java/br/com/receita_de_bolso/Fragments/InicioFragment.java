package br.com.receita_de_bolso.Fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.receita_de_bolso.R;

public class InicioFragment extends Fragment {


    public InicioFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

}
