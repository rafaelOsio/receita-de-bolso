package br.com.receita_de_bolso.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.receita_de_bolso.Adapters.ReceitaAdapterAdapter;
import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.Interfaces.IReceitaOnClickListener;
import br.com.receita_de_bolso.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetRecipeByCategory extends AppCompatActivity implements IReceitaOnClickListener {

    @BindView(R.id.activity_title)
    TextView activityTitle;
    @BindView(R.id.activity_subtitle)
    TextView activitySubtitle;
    private ReceitaAdapterAdapter receitasAdapter;
    private ReceitaDAO receitaDAO;
    private CategoriaDAO categoryDAO;
    private Long Id;
    private ArrayList<Receita> receitas;
    private Categoria category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recipe_by_category);
        ButterKnife.bind(this);
        this.categoryDAO = new CategoriaDAO(this);

        if (getIntent().getExtras() != null) {
            this.Id = getIntent().getExtras().getLong("id");
            this.category = categoryDAO.getById(this.Id);
        }

        this.activityTitle.setText(this.category.getNome());
        this.activitySubtitle.setText(this.category.getDescricao());

        this.receitasAdapter = new ReceitaAdapterAdapter(this, new ArrayList<>(), this);
        setupRecyclerViewReceitas();
    }

    @OnClick(R.id.back_button)
    public void onViewClicked() {
        super.onBackPressed();
    }

    private void setupRecyclerViewReceitas() {

        RecyclerView recyclerViewReceitas = findViewById(R.id.recipe_recyclerview);
        LinearLayoutManager linearLayoutManagerCategorias = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewReceitas.setLayoutManager(linearLayoutManagerCategorias);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewReceitas.setLayoutManager(mLayoutManager);
        recyclerViewReceitas.setAdapter(receitasAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        getAllReceitas();
    }

    private void getAllReceitas() {
        ReceitaDAO receitaDAO = new ReceitaDAO(this);
        receitasAdapter.setItems(receitaDAO.getByCategoryId(this.Id));
    }

    public void favoritoOnClick(Receita receita) {
        ReceitaDAO receitaDAO = new ReceitaDAO(getBaseContext());
        receitaDAO.update(receita);
        getAllReceitas();
    }
}
