package br.com.receita_de_bolso.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.receita_de_bolso.R;

public class ReceitaViewHolder extends RecyclerView.ViewHolder {

    public TextView nome;
    public View container;
    public ImageView image;
    public ImageView favButton;
    public ImageView shareButton;

    public ReceitaViewHolder(@NonNull View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.recipe_name);
        container = itemView.findViewById(R.id.recipe_container);
        image = itemView.findViewById(R.id.recipe_photo);
        favButton = itemView.findViewById(R.id.btn_favorite);
        shareButton = itemView.findViewById(R.id.btn_share);
    }
}
