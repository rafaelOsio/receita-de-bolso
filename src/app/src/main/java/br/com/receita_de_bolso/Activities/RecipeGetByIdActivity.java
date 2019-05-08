package br.com.receita_de_bolso.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import br.com.receita_de_bolso.Adapters.TabsAdapter;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.Fragments.TabFragment;
import br.com.receita_de_bolso.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeGetByIdActivity extends FragmentActivity {
    @BindView(R.id.recipe_name)
    TextView recipeName;
    @BindView(R.id.recipe_preparation_time)
    TextView recipePreparationTime;
    @BindView(R.id.recipe_portions)
    TextView recipePortions;
    private Long Id;
    private ReceitaDAO receitaDAO;
    private Receita receita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_by_id_recipe);
        ButterKnife.bind(this);
        receitaDAO = new ReceitaDAO(getBaseContext());

        getData();

        Resources resources = getResources();
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());

        TabFragment fragmentIngredientes = new TabFragment(this.receita.getIngredientes());
        TabFragment fragmentModoPreparo = new TabFragment(this.receita.getModoPreparo());

        adapter.add(fragmentIngredientes, "Ingredientes");
        adapter.add(fragmentModoPreparo, "Modo de preparo");

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked() {
        super.onBackPressed();
    }

    public void getData() {
        if (getIntent().getExtras() != null) {
            this.Id = getIntent().getExtras().getLong("id");
            this.receita = receitaDAO.getById(this.Id);
        }

        recipeName.setText(this.receita.getNome());
        recipePortions.setText(this.receita.getRendimento() + " por.");
        recipePreparationTime.setText(this.receita.getTempoPreparo() + " min.");
    }

    @OnClick(R.id.remove_button)
    public void onRemoveButtonClicked() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @OnClick(R.id.edit_button)
    public void onEditButtonClicked() {
        Intent intent = new Intent(getBaseContext(), ReceitaFormActivity.class);
        intent.putExtra("id", this.receita.getId());
        startActivity(intent);
    }

    @OnClick(R.id.favorite_button)
    public void onFavoriteButtonClicked() {
    }
}
