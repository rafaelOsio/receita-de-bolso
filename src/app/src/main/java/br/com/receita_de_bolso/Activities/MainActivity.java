package br.com.receita_de_bolso.Activities;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import br.com.receita_de_bolso.Fragments.CategoriasFragment;
import br.com.receita_de_bolso.Fragments.InicioFragment;
import br.com.receita_de_bolso.Fragments.ReceitasFragment;
import br.com.receita_de_bolso.R;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);

        Fragment inicioFragment = InicioFragment.newInstance();
        openFragment(inicioFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home: {
                //getSupportActionBar().setTitle("Início");
                Fragment inicioFragment = InicioFragment.newInstance();
                openFragment(inicioFragment);
                break;
            }
            case R.id.navigation_recipes: {
                //getSupportActionBar().setTitle("Receitas");
                Fragment receitasFragment = ReceitasFragment.newInstance();
                openFragment(receitasFragment);
                break;
            }
            case R.id.navigation_categories: {
                //getSupportActionBar().setTitle("Categorias");
                Fragment categoriasFragment = CategoriasFragment.newInstance();
                openFragment(categoriasFragment);
                break;
            }
        }

        return true;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void updateStatusBarColor(String color){// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
}
