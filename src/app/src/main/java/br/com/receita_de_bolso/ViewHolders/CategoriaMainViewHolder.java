package br.com.receita_de_bolso.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.receita_de_bolso.R;

public class CategoriaMainViewHolder extends RecyclerView.ViewHolder {
    public TextView nome;

    public CategoriaMainViewHolder (@NonNull View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.categoria_nome);
    }
}
