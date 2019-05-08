package br.com.receita_de_bolso.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

import br.com.receita_de_bolso.Activities.GetRecipeByCategory;
import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.R;
import br.com.receita_de_bolso.ViewHolders.CategoriaViewHolder;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaViewHolder> {

    private Context context;
    private ArrayList<Categoria> categorias;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    public CategoriaDAO categoriaDAO;

    public CategoriaAdapter(Context context, ArrayList<Categoria> categorias) {
        this.context = context;
        this.categorias = categorias;
        viewBinderHelper.setOpenOnlyOne(true);
        this.categoriaDAO = new CategoriaDAO(context);
    }

    public void setItems(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categoria, viewGroup, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder categoriaViewHolder, int i) {
        categoriaViewHolder.nome.setText(this.categorias.get(i).getNome());
        categoriaViewHolder.descricao.setText(this.categorias.get(i).getDescricao());
        viewBinderHelper.bind(categoriaViewHolder.swipeRevealLayout, this.categorias.get(i).getNome());

        categoriaViewHolder.card.setOnClickListener(v -> {
            Intent intent = new Intent(context, GetRecipeByCategory.class);
            intent.putExtra("id", this.categorias.get(i).getId());
            context.startActivity(intent);
        });

        categoriaViewHolder.swipeRevealLayout.setSwipeListener( new SwipeRevealLayout.SwipeListener() {
            @Override
            public void onClosed(SwipeRevealLayout view) {
                categoriaViewHolder.arrow.setRotation(0);
            }

            @Override
            public void onOpened(SwipeRevealLayout view) {
                categoriaViewHolder.arrow.setRotation(180);
            }

            @Override
            public void onSlide(SwipeRevealLayout view, float slideOffset) {
                categoriaViewHolder.arrow.setRotation(slideOffset * 180);
            }
        });


        categoriaViewHolder.arrow.setOnClickListener(v -> {
            if (categoriaViewHolder.swipeRevealLayout.isOpened()) {
                categoriaViewHolder.swipeRevealLayout.close(true);
                categoriaViewHolder.arrow.setRotation(180);
            }
            else {
                categoriaViewHolder.swipeRevealLayout.open(true);
                categoriaViewHolder.arrow.setRotation(0);
            }
        });

        categoriaViewHolder.btnDelete.setOnClickListener(v -> {
            categoriaDAO.delete(categorias.get(i).getId());
            categoriaViewHolder.swipeRevealLayout.close(true);
            setItems(categoriaDAO.getAll());
        });

        categoriaViewHolder.btnEdit.setOnClickListener(v -> {
            AlertDialog.Builder addCategoryDialog = new AlertDialog.Builder(context);
            View addCategoryView = LayoutInflater.from(context).inflate(R.layout.add_category_dialog, null);

            TextView title = addCategoryView.findViewById(R.id.txt_view_title);
            EditText categoryName = addCategoryView.findViewById(R.id.category_name_field);
            EditText categoryDescription = addCategoryView.findViewById(R.id.category_description_field);
            Button addCategory = addCategoryView.findViewById(R.id.btn_save_category);
            Button btnCancel = addCategoryView.findViewById(R.id.btn_cancel);

            addCategory.setText("Salvar");
            title.setText("Editar categoria");
            categoryName.setText(categorias.get(i).getNome());
            categoryDescription.setText(categorias.get(i).getDescricao());

            addCategoryDialog.setView(addCategoryView);
            AlertDialog dialog = addCategoryDialog.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            btnCancel.setOnClickListener(v1 -> {
                categoriaViewHolder.swipeRevealLayout.close(true);
                dialog.hide();
            });

            addCategory.setOnClickListener(v2 -> {
                Categoria categoria = new Categoria();
                categoria.setDescricao(categoryDescription.getText().toString());
                categoria.setNome(categoryName.getText().toString());
                categoria.setId(categorias.get(i).getId());
                categoriaDAO.update(categoria);
                categoriaViewHolder.swipeRevealLayout.close(true);
                setItems(categoriaDAO.getAll());
                dialog.hide();
            });

            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }
}
