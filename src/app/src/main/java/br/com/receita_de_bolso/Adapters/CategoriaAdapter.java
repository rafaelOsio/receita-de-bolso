package br.com.receita_de_bolso.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
        categoriaViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                categoriaDAO.delete(categorias.get(i).getId());
                categoriaViewHolder.swipeRevealLayout.close(true);
                setItems(categoriaDAO.getAll());
            }
        });

        categoriaViewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder addCategoryDialog = new AlertDialog.Builder(context);
                View addCategoryView = LayoutInflater.from(context).inflate(R.layout.add_category_dialog, null);

                TextView title = (TextView) addCategoryView.findViewById(R.id.txt_view_title);
                EditText categoryName = (EditText) addCategoryView.findViewById(R.id.category_name_field);
                EditText categoryDescription = (EditText) addCategoryView.findViewById(R.id.category_description_field);
                Button addCategory = (Button) addCategoryView.findViewById(R.id.btn_save_category);
                Button btnCancel = (Button) addCategoryView.findViewById(R.id.btn_cancel);

                title.setText("Editar categoria");
                categoryName.setText(categorias.get(i).getNome());
                categoryDescription.setText(categorias.get(i).getDescricao());


                addCategoryDialog.setView(addCategoryView);
                AlertDialog dialog = addCategoryDialog.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        categoriaViewHolder.swipeRevealLayout.close(true);
                        dialog.hide();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }
}
