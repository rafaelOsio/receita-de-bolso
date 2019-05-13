package br.com.receita_de_bolso.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.receita_de_bolso.R;

public class ReceitaMainViewHolder extends RecyclerView.ViewHolder {

    public TextView nome;
    public View recipeCard;

    public ReceitaMainViewHolder(@NonNull View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.receita_nome);
        recipeCard = itemView.findViewById(R.id.recipe_card);
    }
}
