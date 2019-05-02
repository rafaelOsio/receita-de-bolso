package br.com.receita_de_bolso.ViewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.receita_de_bolso.R;

public class CategoriaViewHolder extends RecyclerView.ViewHolder {

    public TextView nome;

    public CategoriaViewHolder(@NonNull View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.nome_categoria);
    }
}
