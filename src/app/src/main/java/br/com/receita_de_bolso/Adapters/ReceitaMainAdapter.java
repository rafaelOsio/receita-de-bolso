package br.com.receita_de_bolso.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.receita_de_bolso.Activities.RecipeGetByIdActivity;
import br.com.receita_de_bolso.Activities.RecipeWebGetByIdActivity;
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

        File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "images" + File.separator + this.receitas.get(i).getImageName());
        if (imgFile.exists()) {
            receitaMainViewHolder.recipePhoto.setImageURI(Uri.fromFile(imgFile));
            receitaMainViewHolder.recipePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            receitaMainViewHolder.recipePhoto.setImageResource(R.drawable.ic_logo);
        }

        receitaMainViewHolder.recipeCard.setOnClickListener(v -> {
            openGetById(this.receitas.get(i));
        });

        receitaMainViewHolder.nome.setText(this.receitas.get(i).getNome());

    }

    public Boolean isWeb(Receita receita) {
        if (receita.getUrl() != null && !receita.getUrl().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void openGetById(Receita receita) {
        if (isWeb(receita)) {
            Intent intent = new Intent(context, RecipeWebGetByIdActivity.class);
            intent.putExtra("id", Long.valueOf(receita.getId()));
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, RecipeGetByIdActivity.class);
            intent.putExtra("id", receita.getId());
            Log.e("idAntes", String.valueOf(intent.getExtras().getLong("id")));
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }

}