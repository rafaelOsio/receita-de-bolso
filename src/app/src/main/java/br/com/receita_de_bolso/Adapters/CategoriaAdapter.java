package br.com.receita_de_bolso.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.R;
import br.com.receita_de_bolso.ViewHolders.CategoriaViewHolder;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaViewHolder> {

    private Context context;
    private ArrayList<Categoria> categorias;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

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
        viewBinderHelper.bind(categoriaViewHolder.swipeRevealLayout, this.categorias.get(i).getNome());
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }
}
