package br.com.receita_de_bolso.Activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.palette.graphics.Palette;

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
    private static final int TAKE_PICTURE = 2;

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
    @BindView(R.id.activity_title)
    TextView activityTitle;
    @BindView(R.id.url_from_web)
    EditText urlFromWeb;
    private Categoria selectedCategory;
    private long Id = -1;
    private boolean isWeb;
    private ReceitaDAO receitaDAO;
    private CategoriaDAO categoriaDAO;
    private ArrayList<Categoria> categorias;
    private CategorySpinnerAdapter categoryAdapter;
    private Receita receita;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita_form);

        unbinder = ButterKnife.bind(this);
        this.receita = new Receita();

        this.receitaDAO = new ReceitaDAO(this);
        this.categoriaDAO = new CategoriaDAO(this);

        this.getPermissions();

        if (getIntent().getExtras() != null) {
            Log.e("é esse", String.valueOf(getIntent().getExtras().getLong("id")));
            this.Id = getIntent().getExtras().getLong("id");
            this.isWeb = getIntent().getExtras().getBoolean("isWeb");
        }

        handleVisibility();

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
            if (receita == null)
                return;
            this.recipeName.setText(receita.getNome());
            this.recipeIngredients.setText(receita.getIngredientes());
            this.recipeMethodOfPreparation.setText(receita.getModoPreparo());
            this.recipePortions.setText(String.valueOf(receita.getRendimento()));
            this.recipePreparationTime.setText(String.valueOf(receita.getTempoPreparo()));
            Categoria categoria = categoriaDAO.getById(receita.getCategoriaId());
            this.categorySpinner.setSelection(categoryAdapter.getPosition(categoria));

            File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "images" + File.separator + receita.getImageName());
            if (imgFile.exists()) {
                imageRecipe.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }

    public void handleVisibility() {
        if (this.isWeb) {
            urlFromWeb.setVisibility(View.VISIBLE);
            recipePreparationTime.setVisibility(View.GONE);
            recipeMethodOfPreparation.setVisibility(View.GONE);
            recipePortions.setVisibility(View.GONE);
            recipeIngredients.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked() {
        super.onBackPressed();
    }

    @OnClick(R.id.open_camera_btn)
    public void onOpenCameraBtnClicked() {
        abreCamera();
    }

    @OnClick(R.id.open_gallery_btn)
    public void onOpenGalleryBtnClicked() {
        escolheFotoDaGaleria();
    }

    private void escolheFotoDaGaleria() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void abreCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "foto.jpg");

        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(ReceitaFormActivity.this, "br.com.receita_de_bolso.fileprovider", photo));

        imageUri = FileProvider.getUriForFile(ReceitaFormActivity.this, "br.com.receita_de_bolso.fileprovider", photo);

        startActivityForResult(intent, TAKE_PICTURE);
    }

    @OnClick(R.id.btn_salvar)
    public void onBtnSalvarClicked() {
        String name = recipeName.getText().toString();
        Categoria categoria = selectedCategory;

        receita.setNome(name);
        receita.setCategoria(categoria);
        receita.setCategoriaId(categoria.getId());
        receita.setFav(false);
        receita.setUltimoAcesso(new Date());

        if (!isWeb) {
            String ingredients = recipeIngredients.getText().toString();
            String methodOfPreparation = recipeMethodOfPreparation.getText().toString();
            String portions = recipePortions.getText().toString();
            String preparationTime = recipePreparationTime.getText().toString();

            receita.setIngredientes(ingredients);
            receita.setModoPreparo(methodOfPreparation);
            receita.setRendimento(Integer.parseInt(portions));
            receita.setTempoPreparo(Integer.parseInt(preparationTime));

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ingredients) || TextUtils.isEmpty(methodOfPreparation) || TextUtils.isEmpty(portions) || TextUtils.isEmpty(preparationTime)) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            String url = urlFromWeb.getText().toString();

            receita.setUrl(url);

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(url)) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (receita.getImageBitmap() != null) {
            receita.setImageName(UUID.randomUUID().toString() + "." + receita.getImageExtension());

            OutputStream fOut = null;
            Uri outputFileUri;
            try {
                File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "images" + File.separator);
                root.mkdirs();

                File sdImageMainDirectory = new File(root, receita.getImageName());

                outputFileUri = Uri.fromFile(sdImageMainDirectory);
                fOut = new FileOutputStream(sdImageMainDirectory);

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File f = new File(outputFileUri.toString());
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

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
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case RESULT_LOAD_IMAGE: {

                    if (data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

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
                            Palette.from(bmp).maximumColorCount(32).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    Palette.Swatch vibrant = palette.getVibrantSwatch();
                                    if (vibrant != null) {
                                        activityTitle.setTextColor(vibrant.getTitleTextColor());
                                    }
                                }
                            });
                        } catch (IOException e) {
                            Toast.makeText(this, "Não foi possível abrir a sua imgem.", Toast.LENGTH_LONG).show();
                        }
                    }

                    break;
                }

                case TAKE_PICTURE: {

                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);

                        this.receita.setImageBitmap(bitmap);
                        imageRecipe.setImageBitmap(bitmap);
                        Palette.from(bitmap).maximumColorCount(32).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch vibrant = palette.getVibrantSwatch();
                                if (vibrant != null) {
                                    activityTitle.setTextColor(vibrant.getTitleTextColor());
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(this, "Ocorreu um erro ao tirar a foto.", Toast.LENGTH_SHORT).show();
                    }

                    break;
                }
            }
        }
    }

    private void getPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Você precisa fornecer as permissões para cadastrar uma nova receita.", Toast.LENGTH_LONG).show();
                    finish();
                }

                break;
            }
        }
    }


}
