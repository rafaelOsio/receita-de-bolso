package br.com.receita_de_bolso.ViewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import br.com.receita_de_bolso.R;

public class CategoriaViewHolder extends RecyclerView.ViewHolder {

    public TextView nome;
    public TextView descricao;
    public SwipeRevealLayout swipeRevealLayout;

    public CategoriaViewHolder(@NonNull View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.category_name);
        descricao = itemView.findViewById(R.id.category_description);
        swipeRevealLayout = itemView.findViewById(R.id.swipe_layout);
    }
}
