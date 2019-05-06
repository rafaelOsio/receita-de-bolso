package br.com.receita_de_bolso.Fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.receita_de_bolso.Adapters.CategoriaAdapter;
import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.R;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CategoriasFragment extends Fragment {

    Unbinder unbinder;
    CategoriaAdapter categoriaAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.categoriaAdapter = new CategoriaAdapter(getContext(), new ArrayList<>());
        setupRecyclerViewCategorias();
    }

    private void setupRecyclerViewCategorias() {

        RecyclerView recyclerViewCategorias = getActivity().findViewById(R.id.categorias_recyclerview);
        LinearLayoutManager linearLayoutManagerCategorias = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_add_category)
    public void onViewClicked() {
        AlertDialog.Builder addCategoryDialog = new AlertDialog.Builder(getActivity());
        View addCategoryView = getLayoutInflater().inflate(R.layout.add_category_dialog, null);

        EditText categoryName = addCategoryView.findViewById(R.id.category_name_field);
        EditText categoryDescription = addCategoryView.findViewById(R.id.category_description_field);
        Button addCategory = addCategoryView.findViewById(R.id.btn_save_category);
        Button btnCancel = addCategoryView.findViewById(R.id.btn_cancel);

        addCategoryDialog.setView(addCategoryView);
        AlertDialog dialog = addCategoryDialog.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnCancel.setOnClickListener(v -> dialog.hide());

        addCategory.setOnClickListener(v -> {

            Categoria categoria = new Categoria();
            categoria.setNome(categoryName.getText().toString());
            categoria.setDescricao(categoryDescription.getText().toString());

            if (!categoria.getNome().isEmpty() && !categoria.getDescricao().isEmpty()) {

                CategoriaDAO categoriaDAO = new CategoriaDAO(getContext());
                Categoria verifyCategory = categoriaDAO.getByNome(categoria.getNome());

                if (verifyCategory == null) {
                    categoriaDAO.insert(categoria);
                    categoriaAdapter.setItems(categoriaDAO.getAll());
                    dialog.hide();
                } else {
                    Toast.makeText(getActivity(), "Essa categoria já existe!", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
    }
}
