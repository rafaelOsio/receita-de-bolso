package br.com.receita_de_bolso.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.receita_de_bolso.R;

public class CategoriasFragment extends Fragment {

    public CategoriasFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categorias, container, false);
    }

    public static CategoriasFragment newInstance() {
        return new CategoriasFragment();
    }
}
