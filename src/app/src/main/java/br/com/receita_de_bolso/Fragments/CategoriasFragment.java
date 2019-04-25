package br.com.receita_de_bolso.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.receita_de_bolso.Adapters.CategoriaAdapter;
import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.R;

public class CategoriasFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categorias, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerViewCategorias();
    }

    private void setupRecyclerViewCategorias() {

        RecyclerView recyclerViewCategorias = getActivity().findViewById(R.id.categorias_recyclerview);
        LinearLayoutManager linearLayoutManagerCategorias = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        CategoriaAdapter categoriaAdapter = new CategoriaAdapter(getContext(), new ArrayList<Categoria>());

        recyclerViewCategorias.setLayoutManager(linearLayoutManagerCategorias);
        recyclerViewCategorias.setAdapter(categoriaAdapter);

        getAllCategorias(categoriaAdapter);
    }

    private void getAllCategorias(CategoriaAdapter categoriaAdapter) {
        CategoriaDAO categoriaDAO = new CategoriaDAO(getContext());
        categoriaAdapter.setItems(categoriaDAO.getAll());
    }


    public static CategoriasFragment newInstance() {
        return new CategoriasFragment();
    }

}
