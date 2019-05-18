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

import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.io.File;
import java.util.ArrayList;

import br.com.receita_de_bolso.Activities.ReceitaFormActivity;
import br.com.receita_de_bolso.Activities.RecipeGetByIdActivity;
import br.com.receita_de_bolso.Activities.RecipeWebGetByIdActivity;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.Interfaces.IReceitaOnClickListener;
import br.com.receita_de_bolso.R;
import br.com.receita_de_bolso.ViewHolders.ReceitaViewHolder;

public class ReceitaAdapter extends RecyclerView.Adapter<ReceitaViewHolder> {

    private Context context;
    private ArrayList<Receita> receitas;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private ReceitaDAO receitaDAO;
    private IReceitaOnClickListener receitaOnClickListener;

    public ReceitaAdapter(Context context, ArrayList<Receita> receitas, IReceitaOnClickListener receitaOnClickListener) {
        this.context = context;
        this.receitas = receitas;
        this.receitaDAO = new ReceitaDAO(context);
        this.viewBinderHelper.setOpenOnlyOne(true);
        this.receitaOnClickListener = receitaOnClickListener;
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
        Receita receita = this.receitas.get(i);

        receitaViewHolder.nome.setText(this.receitas.get(i).getNome());

        File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "images" + File.separator + receita.getImageName());
        if (imgFile.exists()) {
            receitaViewHolder.image.setImageURI(Uri.fromFile(imgFile));
            receitaViewHolder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            receitaViewHolder.image.setImageResource(R.drawable.ic_logo);
        }

        Boolean isFav = this.receitas.get(i).getFav();

        if (isFav)
            receitaViewHolder.favButton.setBackgroundResource(R.drawable.ic_heart);
        else
            receitaViewHolder.favButton.setBackgroundResource(R.drawable.ic_heart_outline);

        receitaViewHolder.container.setOnClickListener(v -> {
            openGetById(receita, i);
        });

        receitaViewHolder.image.setOnClickListener(v1 -> {
            openGetById(receita, i);
        });

        receitaViewHolder.favButton.setOnClickListener(v2 -> {
            this.receitas.get(i).setFav(!isFav);
            this.receitaOnClickListener.favoritoOnClick(this.receitas.get(i));
        });

        receitaViewHolder.shareButton.setOnClickListener(v3 -> {
            String textoReceita;

            if (isWeb(receita)) {
                textoReceita = receita.getNome() + "\n\nLink para acessar:\n" + receita.getUrl();
            } else {
                textoReceita = receita.getNome() + "\n\nIngredientes:\n" + receita.getIngredientes() + "\n\nModo de preparo:\n" + receita.getModoPreparo() + "\n\nTempo de preparo:\n" + receita.getTempoPreparo() + "\n\nRendimento:\n" + receita.getRendimento();
            }

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, textoReceita);

            if(receita.getImageName() != null) {
                Uri imageUri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "images" + File.separator + receita.getImageName());
                sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                sharingIntent.setType("image/*");
            }
            else {
                sharingIntent.setType("text/plain");
            }

            context.startActivity(Intent.createChooser(sharingIntent, "Compartilhar via"));

        });
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }

    public Boolean isWeb(Receita receita) {
        if (receita.getUrl() != null && !receita.getUrl().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void openGetById(Receita receita, Integer i) {
        if (isWeb(receita)) {
            Intent intent = new Intent(context, RecipeWebGetByIdActivity.class);
            intent.putExtra("id", Long.valueOf(receita.getId()));
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, RecipeGetByIdActivity.class);
            intent.putExtra("id", this.receitas.get(i).getId());
            Log.e("idAntes", String.valueOf(intent.getExtras().getLong("id")));
            context.startActivity(intent);
        }
    }
}
