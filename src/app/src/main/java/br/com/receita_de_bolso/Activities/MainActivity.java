package br.com.receita_de_bolso.Activities;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

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

        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home: {
                getSupportActionBar().setTitle("Início");
                Fragment inicioFragment = InicioFragment.newInstance();
                openFragment(inicioFragment);
                break;
            }
            case R.id.navigation_recipes: {
                getSupportActionBar().setTitle("Receitas");
                Fragment receitasFragment = ReceitasFragment.newInstance();
                openFragment(receitasFragment);
                break;
            }
            case R.id.navigation_categories: {
                getSupportActionBar().setTitle("Categorias");
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
}
