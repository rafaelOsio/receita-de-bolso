package br.com.receita_de_bolso.Activities;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import br.com.receita_de_bolso.Adapters.TabsAdapter;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.Fragments.TabFragment;
import br.com.receita_de_bolso.R;

public class RecipeGetByIdActivity extends FragmentActivity {
    private Long Id;
    private ReceitaDAO receitaDAO;
    private Receita receita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_by_id_recipe);
        receitaDAO = new ReceitaDAO(getBaseContext());

        if (getIntent().getExtras() != null) {
            this.Id = getIntent().getExtras().getLong("id");
            Log.e("id", this.Id.toString());
            this.receita = receitaDAO.getById(this.Id);
            Log.e("receita", this.receita.getNome());
        }

        Resources resources = getResources();
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
        adapter.add(new TabFragment(), "Ingredientes");
        adapter.add(new TabFragment(), "Modo de preparo");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
