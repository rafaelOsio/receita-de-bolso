package br.com.receita_de_bolso.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.receita_de_bolso.Activities.CategoriaFormActivity;
import br.com.receita_de_bolso.Activities.MainActivity;
import br.com.receita_de_bolso.Activities.ReceitaFormActivity;
import br.com.receita_de_bolso.Adapters.CategoriaAdapter;
import br.com.receita_de_bolso.Adapters.GetAllRecipeAdapter;
import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.R;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ReceitasFragment extends Fragment {

    Unbinder unbinder;
    GetAllRecipeAdapter receitasAdapter;

    public ReceitasFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).updateStatusBarColor("#fafafa");
        View view = inflater.inflate(R.layout.fragment_receitas, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public static ReceitasFragment newInstance() {
        return new ReceitasFragment();
    }

    @OnClick(R.id.btn_add_recipe)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), ReceitaFormActivity.class));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.receitasAdapter = new GetAllRecipeAdapter(getContext(), new ArrayList<>());
        setupRecyclerViewReceitas();
    }

    private void setupRecyclerViewReceitas() {

        RecyclerView recyclerViewReceitas = getActivity().findViewById(R.id.recipe_recyclerview);
        LinearLayoutManager linearLayoutManagerCategorias = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerViewReceitas.setLayoutManager(linearLayoutManagerCategorias);
        recyclerViewReceitas.setAdapter(receitasAdapter);

        getAllReceitas(receitasAdapter);
    }

    private void getAllReceitas(GetAllRecipeAdapter receitaAdapter) {
        ReceitaDAO receitaDAO = new ReceitaDAO(getContext());
        receitaAdapter.setItems(receitaDAO.getAll());
    }

}
