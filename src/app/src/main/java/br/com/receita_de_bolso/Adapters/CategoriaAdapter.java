package br.com.receita_de_bolso.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.R;
import br.com.receita_de_bolso.ViewHolders.CategoriaViewHolder;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaViewHolder> {

    private Context context;
    private ArrayList<Categoria> categorias;

    public CategoriaAdapter(Context context, ArrayList<Categoria> categorias) {
        this.context = context;
        this.categorias = categorias;
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
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }
}
