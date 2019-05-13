package br.com.receita_de_bolso.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

import br.com.receita_de_bolso.Activities.ReceitaFormActivity;
import br.com.receita_de_bolso.Activities.RecipeGetByIdActivity;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.R;
import br.com.receita_de_bolso.ViewHolders.ReceitaViewHolder;

public class ReceitaAdapterAdapter extends RecyclerView.Adapter<ReceitaViewHolder> {

    private Context context;
    private ArrayList<Receita> receitas;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private ReceitaDAO receitaDAO;

    public ReceitaAdapterAdapter(Context context, ArrayList<Receita> receitas) {
        this.context = context;
        this.receitas = receitas;
        this.receitaDAO = new ReceitaDAO(context);
        viewBinderHelper.setOpenOnlyOne(true);
    }

    public void setItems(ArrayList<Receita> receitas) {
        this.receitas = receitas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReceitaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, viewGroup, false);
        return new ReceitaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceitaViewHolder receitaViewHolder, int i) {
        receitaViewHolder.nome.setText(this.receitas.get(i).getNome());

        Boolean isFav = this.receitas.get(i).getFav();

        if (isFav)
            receitaViewHolder.favButton.setBackgroundResource(R.drawable.ic_heart);
        else
            receitaViewHolder.favButton.setBackgroundResource(R.drawable.ic_heart_outline);

        receitaViewHolder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeGetByIdActivity.class);
            intent.putExtra("id", this.receitas.get(i).getId());
            Log.e("idAntes", String.valueOf(intent.getExtras().getLong("id")));
            context.startActivity(intent);
        });

        receitaViewHolder.image.setOnClickListener(v1 -> {
            Intent intent = new Intent(context, RecipeGetByIdActivity.class);
            intent.putExtra("id", this.receitas.get(i).getId());
            context.startActivity(intent);
        });

        receitaViewHolder.favButton.setOnClickListener(v2 -> {
            this.receitas.get(i).setFav(!isFav);
            receitaDAO.update(this.receitas.get(i));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }
}
