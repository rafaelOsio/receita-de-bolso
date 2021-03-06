package br.com.receita_de_bolso.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.Date;

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
    @BindView(R.id.favorite_button)
    Button favoriteButton;
    @BindView(R.id.recipe_photo)
    ImageView recipePhoto;
    private Long Id;
    private ReceitaDAO receitaDAO;
    private Receita receita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.updateStatusBarColor("#FF972F");
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

        this.receita.setUltimoAcesso(new Date());
        receitaDAO.update(this.receita);
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

        File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "images" + File.separator + receita.getImageName());
        if (imgFile.exists()) {
            recipePhoto.setImageURI(Uri.fromFile(imgFile));
        }

        setFavButtonImage();
    }

    @OnClick(R.id.remove_button)
    public void onRemoveButtonClicked() {
        AlertDialog.Builder removeRecipeDialog = new AlertDialog.Builder(this);
        View removeRecipeView = getLayoutInflater().inflate(R.layout.remove_dialog, null);

        Button btnConfirm = removeRecipeView.findViewById(R.id.btn_confirm);
        Button btnCancel = removeRecipeView.findViewById(R.id.btn_cancel);

        removeRecipeDialog.setView(removeRecipeView);
        AlertDialog dialog = removeRecipeDialog.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnCancel.setOnClickListener(v -> {
            dialog.hide();
        });

        btnConfirm.setOnClickListener(v1 -> {
            receitaDAO.delete(this.Id);
            dialog.hide();
            onBackButtonClicked();
            return;
        });

        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void setFavButtonImage() {
        if (this.receita.getFav())
            this.favoriteButton.setBackgroundResource(R.drawable.ic_rounded_heart);
        else
            this.favoriteButton.setBackgroundResource(R.drawable.ic_rounded_heart_outline);
    }

    @OnClick(R.id.edit_button)
    public void onEditButtonClicked() {
        Intent intent = new Intent(getBaseContext(), ReceitaFormActivity.class);
        intent.putExtra("id", this.receita.getId());
        if (this.receita.getUrl() != null && !this.receita.getUrl().equals("")) {
            intent.putExtra("isWeb", true);
        }
        startActivity(intent);
    }

    @OnClick(R.id.favorite_button)
    public void onFavoriteButtonClicked() {
        if (this.receita.getFav())
            this.receita.setFav(false);
        else
            this.receita.setFav(true);

        receitaDAO.update(this.receita);

        setFavButtonImage();
    }

    public void updateStatusBarColor(String color){// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
}
