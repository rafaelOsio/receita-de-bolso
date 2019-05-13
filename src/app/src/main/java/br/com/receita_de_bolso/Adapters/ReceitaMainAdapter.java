package br.com.receita_de_bolso.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.receita_de_bolso.Activities.RecipeGetByIdActivity;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.R;
import br.com.receita_de_bolso.ViewHolders.ReceitaMainViewHolder;

public class ReceitaMainAdapter extends RecyclerView.Adapter<ReceitaMainViewHolder> {

    private Context context;
    private ArrayList<Receita> receitas;

    public ReceitaMainAdapter(Context context, ArrayList<Receita> receitas) {
        this.context = context;
        this.receitas = receitas;
    }

    public void setItems(ArrayList<Receita> receitas) {
        this.receitas = receitas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReceitaMainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe_main, viewGroup, false);
        return new ReceitaMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceitaMainViewHolder receitaMainViewHolder, int i) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDataUltimoAcesso = dateFormat.format(this.receitas.get(i).getUltimoAcesso());

        receitaMainViewHolder.recipeCard.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeGetByIdActivity.class);
            intent.putExtra("id", this.receitas.get(i).getId());
            context.startActivity(intent);
        });

        receitaMainViewHolder.nome.setText(this.receitas.get(i).getNome());

    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }

}