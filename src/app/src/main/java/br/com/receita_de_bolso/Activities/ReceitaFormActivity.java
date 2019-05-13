package br.com.receita_de_bolso.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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

    private static final int RESULT_LOAD_IMAGE = 1;

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
    @BindView(R.id.image_recipe)
    ImageView imageRecipe;
    private Categoria selectedCategory;
    private long Id = -1;
    private ReceitaDAO receitaDAO;
    private CategoriaDAO categoriaDAO;
    private ArrayList<Categoria> categorias;
    private CategorySpinnerAdapter categoryAdapter;
    private Receita receita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita_form);

        unbinder = ButterKnife.bind(this);
        this.receita = new Receita();

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

//            String d = Environment.getExternalStorageDirectory() + File.separator + "images" + File.separator + receita.getImageName();
//            System.out.println(d);

            File imgFile = new  File(Environment.getExternalStorageDirectory() + File.separator + "images" + File.separator + receita.getImageName());
            if(imgFile.exists())
            {
                imageRecipe.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked() {
        super.onBackPressed();
    }

    @OnClick(R.id.set_img_button)
    public void onSetImgButtonClicked() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
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

        receita.setNome(name);
        receita.setIngredientes(ingredients);
        receita.setModoPreparo(methodOfPreparation);
        receita.setRendimento(Integer.parseInt(portions));
        receita.setTempoPreparo(Integer.parseInt(preparationTime));
        receita.setCategoria(categoria);
        receita.setCategoriaId(categoria.getId());
        receita.setFav(false);
        receita.setUltimoAcesso(new Date());

        if(receita.getImageBitmap() != null) {
            receita.setImageName(UUID.randomUUID().toString() + "." + receita.getImageExtension());

            OutputStream fOut = null;
            Uri outputFileUri;
            try {
                File root = new File(Environment.getExternalStorageDirectory() + File.separator + "images" + File.separator);
                root.mkdirs();

                File sdImageMainDirectory = new File(root, receita.getImageName());

                String t = Environment.getExternalStorageDirectory() + File.separator + "images" + File.separator + receita.getImageName();
                System.out.println(t);

                outputFileUri = Uri.fromFile(sdImageMainDirectory);
                fOut = new FileOutputStream(sdImageMainDirectory);
            } catch (Exception e) {
                Toast.makeText(this, "Não foi possível salvar a imagem da sua receita.", Toast.LENGTH_LONG).show();
            }

            try {
                this.receita.getImageBitmap().compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
                Toast.makeText(this, "Não foi possível salvar a imagem da sua receita.", Toast.LENGTH_LONG).show();
            }
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            this.receita.setImageExtension(picturePath.substring(picturePath.lastIndexOf(".")));

            cursor.close();

            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                this.receita.setImageBitmap(bmp);
                imageRecipe.setImageBitmap(bmp);
            } catch (IOException e) {
                Toast.makeText(this, "Não foi possível abrir a sua imgem.", Toast.LENGTH_LONG).show();
            }
        }

    }
}
