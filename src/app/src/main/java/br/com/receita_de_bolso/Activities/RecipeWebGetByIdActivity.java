package br.com.receita_de_bolso.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Date;

import br.com.receita_de_bolso.DAO.ReceitaDAO;
import br.com.receita_de_bolso.Domain.Receita;
import br.com.receita_de_bolso.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeWebGetByIdActivity extends AppCompatActivity {

    @BindView(R.id.webpage)
    WebView webpage;
    @BindView(R.id.favorite_button)
    Button favoriteButton;
    @BindView(R.id.recipe_photo)
    ImageView recipePhoto;
    private Long id;
    private ReceitaDAO receitaDAO;
    private Receita receita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.updateStatusBarColor("#FF972F");
        setContentView(R.layout.activity_recipe_web_get_by_id);
        ButterKnife.bind(this);

        receitaDAO = new ReceitaDAO(this);

        if (getIntent().getExtras() != null) {
            this.id = getIntent().getExtras().getLong("id");
        }

        receita = receitaDAO.getById(this.id);
        webpage.getSettings().setJavaScriptEnabled(true);
        webpage.loadUrl(receita.getUrl());

        receita.setUltimoAcesso(new Date());
        receitaDAO.update(receita);

        File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "images" + File.separator + receita.getImageName());
        if (imgFile.exists()) {
            recipePhoto.setImageURI(Uri.fromFile(imgFile));
        }
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked() {
        onBackPressed();
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
            receitaDAO.delete(this.id);
            dialog.hide();
            onBackButtonClicked();
            return;
        });

        dialog.show();
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

    public void setFavButtonImage() {
        if (this.receita.getFav())
            this.favoriteButton.setBackgroundResource(R.drawable.ic_rounded_heart);
        else
            this.favoriteButton.setBackgroundResource(R.drawable.ic_rounded_heart_outline);
    }

    public void updateStatusBarColor(String color){// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
}
