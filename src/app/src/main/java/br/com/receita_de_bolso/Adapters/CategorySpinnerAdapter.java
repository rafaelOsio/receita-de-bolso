package br.com.receita_de_bolso.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.R;


public class CategorySpinnerAdapter extends ArrayAdapter<Categoria> {

    public CategorySpinnerAdapter(Context context, ArrayList<Categoria> categoryList) {
        super(context, 0, categoryList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.category_spinner_item, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.txt_category_name);

        Categoria currentItem = getItem(position);

        if (currentItem != null) {
            textViewName.setText(currentItem.getNome());
        }

        return convertView;
    }
}
