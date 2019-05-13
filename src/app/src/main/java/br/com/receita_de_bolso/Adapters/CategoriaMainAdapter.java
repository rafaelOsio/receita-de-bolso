package br.com.receita_de_bolso.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.receita_de_bolso.Activities.GetRecipeByCategory;
import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.R;
import br.com.receita_de_bolso.ViewHolders.CategoriaMainViewHolder;

public class CategoriaMainAdapter extends RecyclerView.Adapter<CategoriaMainViewHolder> {

    private Context context;
    private ArrayList<Categoria> categorias;

    public CategoriaMainAdapter(Context context, ArrayList<Categoria> categorias) {
        this.context = context;
        this.categorias = categorias;
    }

    public void setItems(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CategoriaMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categoria_main, parent, false);
        return new CategoriaMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaMainViewHolder holder, int position) {
        holder.nome.setText(this.categorias.get(position).getNome());

        holder.categoryCard.setOnClickListener(v -> {
            Intent intent = new Intent(context, GetRecipeByCategory.class);
            intent.putExtra("id", this.categorias.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }
}
