package br.com.receita_de_bolso.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.R;
import br.com.receita_de_bolso.ViewHolders.CategoriaViewHolder;
import br.com.receita_de_bolso.ViewHolders.GetAllRecipeViewHolder;

public class GetAllRecipeAdapter extends RecyclerView.Adapter<GetAllRecipeViewHolder> {

    private Context context;
    private ArrayList<Receita> receitas;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    public ReceitaDAO receitaDAO;

    public GetAllRecipeAdapter(Context context, ArrayList<Receita> receitas) {
        this.context = context;
        this.receitas = receitas;
        viewBinderHelper.setOpenOnlyOne(true);
        this.receitaDAO = new ReceitaDAO(context);
    }

    public void setItems(ArrayList<Receita> receitas) {
        this.receitas = receitas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GetAllRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, viewGroup, false);
        return new GetAllRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllRecipeViewHolder receitaViewHolder, int i) {
        receitaViewHolder.nome.setText(this.receitas.get(i).getNome());
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }
}
