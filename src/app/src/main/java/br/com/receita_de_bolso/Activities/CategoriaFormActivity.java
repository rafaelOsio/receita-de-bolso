package br.com.receita_de_bolso.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.Domain.Categoria;
import br.com.receita_de_bolso.R;

public class CategoriaFormActivity extends AppCompatActivity {

    private long Id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_form);
    }

    private void Salvar() {

        Categoria categoria = new Categoria();

        //Aqui fazer a busca dos dados na tela pra alimentar o objeto

        //Se id == -1, então deverá cadastrar
        if(this.Id == -1) {

            //Se vai cadastrar, tem que verificar se já tem categoria com o mesmo nome cadastrada
            if(GetByNome(categoria.getNome()) != null) {
                this.Post(categoria);
            }
            else {
                Toast.makeText(getBaseContext(), "Não é possível cadastrar mais de uma categoria com o mesmo nome.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            this.Put(categoria);
        }
    }

    private Categoria GetByNome(String nome) {
        CategoriaDAO categoriaDAO = new CategoriaDAO(getBaseContext());
        return categoriaDAO.GetByNome(nome);
    }

    private void Post(Categoria categoria) {
        CategoriaDAO categoriaDAO = new CategoriaDAO(getBaseContext());
        categoriaDAO.Post(categoria);
    }

    private void Put(Categoria categoria) {
        CategoriaDAO categoriaDAO = new CategoriaDAO(getBaseContext());
        categoriaDAO.Put(categoria);
    }
}
