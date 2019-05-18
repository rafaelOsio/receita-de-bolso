package br.com.receita_de_bolso.Filters;

import android.widget.Filter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import br.com.receita_de_bolso.Adapters.ReceitaAdapter;
import br.com.receita_de_bolso.Domain.Receita;

public class ReceitaFilter extends Filter {

    private ReceitaAdapter adapter;
    private ArrayList<Receita> receitas;

    public void setItems(ArrayList<Receita> receitas) {
        this.receitas = receitas;
    }

    public ReceitaFilter(ReceitaAdapter adapter, ArrayList<Receita> receitas) {
        super();

        this.adapter = adapter;
        this.receitas = receitas;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults filterResults = new FilterResults();

        if (charSequence == null || charSequence.length() == 0) {
            filterResults.values = this.receitas;
            filterResults.count = this.receitas.size();
        } else {
            //val filtrados: MutableList<Departamento> = mutableListOf()
            ArrayList<Receita> filtrados = new ArrayList<>();

            for(Receita receita: receitas) {
                if(StringUtils.stripAccents(receita.getNome()).toUpperCase().contains(StringUtils.stripAccents(charSequence.toString()).toUpperCase())) {
                    filtrados.add(receita);
                }
            }

            filterResults.values = filtrados;
            filterResults.count = filtrados.size();
        }

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        this.adapter.setItems((ArrayList<Receita>) filterResults.values);
    }

    @Override
    public CharSequence convertResultToString(Object resultValue) {
        return super.convertResultToString(resultValue);
    }
}
