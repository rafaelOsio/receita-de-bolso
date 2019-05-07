package br.com.receita_de_bolso.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.receita_de_bolso.R;

public class ReceitaViewHolder extends RecyclerView.ViewHolder {

    public TextView nome;

    public ReceitaViewHolder(@NonNull View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.recipe_name);
    }
}
