package br.com.receita_de_bolso.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

import br.com.receita_de_bolso.Adapters.CategorySpinnerAdapter;
import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ReceitaFormActivity extends AppCompatActivity {
    Unbinder unbinder;
    @BindView(R.id.recipe_name)
    EditText recipeName;
    @BindView(R.id.recipe_preparation_time)
    EditText recipePreparationTime;
    @BindView(R.id.recipe_portions)
    EditText recipePortions;
    @BindView(R.id.recipe_ingredients)
    EditText recipeIngredients;
    @BindView(R.id.recipe_method_of_preparation)
    EditText recipeMethodOfPreparation;
    @BindView(R.id.category_spinner)
    Spinner categorySpinner;
    private Categoria selectedCategory;
    private long Id = -1;
    private ReceitaDAO receitaDAO;
    private CategoriaDAO categoriaDAO;
    private ArrayList<Categoria> categorias;
    private CategorySpinnerAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita_form);
        unbinder = ButterKnife.bind(this);
        this.receitaDAO = new ReceitaDAO(this);
        this.categoriaDAO = new CategoriaDAO(this);

        if (getIntent().getExtras() != null) {
            Log.e("teste", String.valueOf(getIntent().getExtras().getLong("id")));
            this.Id = getIntent().getExtras().getLong("id");
        }

        categorias = categoriaDAO.getAll();
        if (categorias != null) {
            selectedCategory = categorias.get(0);
        }

        categorySpinner = findViewById(R.id.category_spinner);
        categoryAdapter = new CategorySpinnerAdapter(this, categorias);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Categoria clickedItem = (Categoria) parent.getItemAtPosition(position);
                selectedCategory = clickedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (this.Id != -1) {
            Receita receita = receitaDAO.getById(this.Id);
            Log.e("id", String.valueOf(this.Id));
            if (receita == null)
                 return;
            Log.e("id", String.valueOf(this.Id));
            this.recipeName.setText(receita.getNome());
            this.recipeIngredients.setText(receita.getIngredientes());
            this.recipeMethodOfPreparation.setText(receita.getModoPreparo());
            this.recipePortions.setText(String.valueOf(receita.getRendimento()));
            this.recipePreparationTime.setText(String.valueOf(receita.getTempoPreparo()));
            Categoria categoria = categoriaDAO.getById(receita.getCategoriaId());
            this.categorySpinner.setSelection(categoryAdapter.getPosition(categoria));
        }
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked() {
        super.onBackPressed();
    }

    @OnClick(R.id.set_img_button)
    public void onSetImgButtonClicked() {
    }

    @OnClick(R.id.btn_salvar)
    public void onBtnSalvarClicked() {
        String name = recipeName.getText().toString();
        String ingredients = recipeIngredients.getText().toString();
        String methodOfPreparation = recipeMethodOfPreparation.getText().toString();
        String portions = recipePortions.getText().toString();
        String preparationTime = recipePreparationTime.getText().toString();
        Categoria categoria = selectedCategory;

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ingredients) || TextUtils.isEmpty(methodOfPreparation) || TextUtils.isEmpty(portions) || TextUtils.isEmpty(preparationTime)) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }


        Receita receita = new Receita();
        receita.setNome(name);
        receita.setIngredientes(ingredients);
        receita.setModoPreparo(methodOfPreparation);
        receita.setRendimento(Integer.parseInt(portions));
        receita.setTempoPreparo(Integer.parseInt(preparationTime));
        receita.setCategoria(categoria);
        receita.setCategoriaId(categoria.getId());
        receita.setUltimoAcesso(new Date());

        if (this.Id == -1) {
            Toast.makeText(this, "Receita adicionada com sucesso! :)", Toast.LENGTH_SHORT).show();
            receitaDAO.insert(receita);
        } else {
            Toast.makeText(this, "Receita editada com sucesso! :)", Toast.LENGTH_SHORT).show();
            receita.setId(this.Id);
            receitaDAO.update(receita);
        }

        super.onBackPressed();
    }
}
