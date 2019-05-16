package br.com.receita_de_bolso.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.receita_de_bolso.Activities.MainActivity;
import br.com.receita_de_bolso.Activities.ReceitaFormActivity;
import br.com.receita_de_bolso.Adapters.CategoriaMainAdapter;
import br.com.receita_de_bolso.Adapters.ReceitaMainAdapter;
import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InicioFragment extends Fragment {
    @BindView(R.id.receitas_recyclerview)
    RecyclerView receitasRecyclerview;
    @BindView(R.id.categorias_recyclerview)
    RecyclerView categoriasRecyclerview;
    @BindView(R.id.tutorial)
    ConstraintLayout tutorial;
    @BindView(R.id.recipe_title)
    TextView recipeTitle;
    @BindView(R.id.category_background_image)
    ImageView categoryBackgroundImage;
    @BindView(R.id.categories_title)
    TextView categoriesTitle;
    @BindView(R.id.add_recipe_tutorial_button)
    ImageView addRecipeTutorialButton;
    private ReceitaDAO receitaDAO;
    private CategoriaDAO categoriaDAO;
    private Unbinder unbinder;
    ArrayList<Receita> receitas;
    ArrayList<Categoria> categorias;
    private ReceitaMainAdapter receitaMainAdapter;
    private CategoriaMainAdapter categoriaMainAdapter;

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).updateStatusBarColor("#FF972F");
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        receitaMainAdapter = new ReceitaMainAdapter(getContext(), new ArrayList<>());
        categoriaMainAdapter = new CategoriaMainAdapter(getContext(), new ArrayList<>());

        this.receitaDAO = new ReceitaDAO(getContext());
        this.categoriaDAO = new CategoriaDAO(getContext());

        receitas = receitaDAO.getAll();
        categorias = categoriaDAO.getAll();

        setVisibility();

        setupRecyclerViewReceitas();
        setupRecyclerViewCategorias();
    }

    private void setVisibility() {
        if (receitas.isEmpty() && categorias.isEmpty()) {
            addRecipeTutorialButton.setVisibility(View.GONE);
            tutorial.setVisibility(View.VISIBLE);
            receitasRecyclerview.setVisibility(View.GONE);
            categoriasRecyclerview.setVisibility(View.GONE);
            recipeTitle.setVisibility(View.GONE);
            categoriesTitle.setVisibility(View.GONE);
            categoryBackgroundImage.setVisibility(View.GONE);
        } else if (receitas.isEmpty() && !categorias.isEmpty()) {
            addRecipeTutorialButton.setVisibility(View.VISIBLE);
        } else {
            tutorial.setVisibility(View.GONE);
            addRecipeTutorialButton.setVisibility(View.GONE);
            receitasRecyclerview.setVisibility(View.VISIBLE);
            categoriasRecyclerview.setVisibility(View.VISIBLE);
            recipeTitle.setVisibility(View.VISIBLE);
            categoriesTitle.setVisibility(View.VISIBLE);
            categoryBackgroundImage.setVisibility(View.VISIBLE);
        }
    }

    private void setupRecyclerViewReceitas() {

        RecyclerView recyclerView = getActivity().findViewById(R.id.receitas_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(receitaMainAdapter);

        receitaMainAdapter.setItems(receitas);
    }

    private void setupRecyclerViewCategorias() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.categorias_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(categoriaMainAdapter);

        categoriaMainAdapter.setItems(categorias);
    }

    @OnClick(R.id.add_recipe_tutorial_button)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), ReceitaFormActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        receitas = receitaDAO.getAllRecentes();
        categorias = categoriaDAO.getAll();
        receitaMainAdapter.setItems(receitas);
        categoriaMainAdapter.setItems(categorias);
        setVisibility();
    }
}
