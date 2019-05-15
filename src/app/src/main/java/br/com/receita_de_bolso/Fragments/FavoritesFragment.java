package br.com.receita_de_bolso.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.receita_de_bolso.Activities.MainActivity;
import br.com.receita_de_bolso.Adapters.ReceitaAdapterAdapter;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.Interfaces.IReceitaOnClickListener;
import br.com.receita_de_bolso.R;

public class FavoritesFragment extends Fragment implements IReceitaOnClickListener {

    private ReceitaAdapterAdapter receitasAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).updateStatusBarColor("#fafafa");
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        return view;
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

        getAllFavorites();
    }

    private void getAllFavorites() {
        ReceitaDAO receitaDAO = new ReceitaDAO(getContext());
        receitasAdapter.setItems(receitaDAO.getAllFav());
    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void favoritoOnClick(Receita receita) {
        ReceitaDAO receitaDAO = new ReceitaDAO(getContext());
        receitaDAO.update(receita);
        receitasAdapter.setItems(receitaDAO.getAllFav());
    }
}
