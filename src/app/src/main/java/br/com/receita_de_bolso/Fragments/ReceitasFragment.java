package br.com.receita_de_bolso.Fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.receita_de_bolso.Activities.MainActivity;
import br.com.receita_de_bolso.Activities.ReceitaFormActivity;
import br.com.receita_de_bolso.Adapters.ReceitaAdapterAdapter;
import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.Interfaces.IReceitaOnClickListener;
import br.com.receita_de_bolso.R;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ReceitasFragment extends Fragment implements IReceitaOnClickListener {

    private Unbinder unbinder;
    private ReceitaAdapterAdapter receitasAdapter;
    private CategoriaDAO categoriaDAO;
    private ArrayList<Categoria> categorias;

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
        categoriaDAO = new CategoriaDAO(getContext());
        categorias = categoriaDAO.getAll();

        if (categorias.isEmpty()) {
            AlertDialog.Builder simpleMessageDialog = new AlertDialog.Builder(getActivity());
            View simpleMessageView = LayoutInflater.from(getActivity()).inflate(R.layout.simple_message_dialog, null);

            TextView title = simpleMessageView.findViewById(R.id.txt_message);
            Button btnCancel = simpleMessageView.findViewById(R.id.btn_close);

            title.setText("Adicione uma categoria antes de adicionar uma receita!");

            simpleMessageDialog.setView(simpleMessageView);
            AlertDialog dialog = simpleMessageDialog.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            btnCancel.setOnClickListener(v1 -> {
                dialog.hide();
            });

            dialog.show();

            return;
        } else {
            startActivity(new Intent(getActivity(), ReceitaFormActivity.class));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.receitasAdapter = new ReceitaAdapterAdapter(getContext(), new ArrayList<>(), this);
        setupRecyclerViewReceitas();
    }

    private void setupRecyclerViewReceitas() {

        RecyclerView recyclerViewReceitas = getActivity().findViewById(R.id.recipe_recyclerview);
        LinearLayoutManager linearLayoutManagerCategorias = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerViewReceitas.setLayoutManager(linearLayoutManagerCategorias);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewReceitas.setLayoutManager(mLayoutManager);
        recyclerViewReceitas.setAdapter(receitasAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        getAllReceitas();
    }

    private void getAllReceitas() {
        ReceitaDAO receitaDAO = new ReceitaDAO(getContext());
        receitasAdapter.setItems(receitaDAO.getAll());
    }

    public void favoritoOnClick(Receita receita) {
        ReceitaDAO receitaDAO = new ReceitaDAO(getContext());
        receitaDAO.update(receita);
        this.getAllReceitas();
    }
}
