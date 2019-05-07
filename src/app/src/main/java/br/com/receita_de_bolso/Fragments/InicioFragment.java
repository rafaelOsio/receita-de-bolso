package br.com.receita_de_bolso.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.receita_de_bolso.Activities.MainActivity;
import br.com.receita_de_bolso.Adapters.CategoriaMainAdapter;
import br.com.receita_de_bolso.Adapters.ReceitaMainAdapter;
import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.R;

public class InicioFragment extends Fragment {

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).updateStatusBarColor("#FF972F");
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerViewReceitas();
        setupRecyclerViewCategorias();
    }

    private void setupRecyclerViewReceitas() {

        RecyclerView recyclerView = getActivity().findViewById(R.id.receitas_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ReceitaMainAdapter adapter = new ReceitaMainAdapter(getContext(), new ArrayList<>());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        ReceitaDAO receitaDAO = new ReceitaDAO(getContext());
        adapter.setItems(receitaDAO.getAllRecentes());
    }

    private void setupRecyclerViewCategorias() {

        RecyclerView recyclerView = getActivity().findViewById(R.id.categorias_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        CategoriaMainAdapter adapter = new CategoriaMainAdapter(getContext(), new ArrayList<>());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        CategoriaDAO categoriaDAO = new CategoriaDAO(getContext());
        adapter.setItems(categoriaDAO.getAll());
    }
}
