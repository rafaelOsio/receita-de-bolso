package br.com.receita_de_bolso.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.receita_de_bolso.DAO.CategoriaDAO;
import br.com.receita_de_bolso.DAO.ReceitaDAO;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "ReceitaDeBolsoDB";

    public DBHelper(Context context) {
        super(context, NOME_BANCO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //tabela categorias
        db.execSQL("create table " + CategoriaDAO.TABELA + " ( " + CategoriaDAO.ID + " integer primary key autoincrement, "  + CategoriaDAO.NOME + " varchar(100) not null, " + CategoriaDAO.DESCRICAO + " varchar(100));");

        //tabela receitas
        db.execSQL("create table " + ReceitaDAO.TABELA + " ( " + ReceitaDAO.ID + " integer primary key autoincrement, " + ReceitaDAO.CATEGORIA_ID + " integer not null, " + ReceitaDAO.NOME + " varchar(100) not null, " + ReceitaDAO.TEMPO_PREPARO + " int default 0, " + ReceitaDAO.RENDIMENTO + " int default 0, " + ReceitaDAO.INGREDIENTES + " varchar(1000), " + ReceitaDAO.MODO_PREPARO+ " varchar(1000), " + ReceitaDAO.ULTIMO_ACESSO + " varchar(50) not null, " + ReceitaDAO.IS_FAV + " int default 0, " + ReceitaDAO.NOME_IMAGEM + " varchar(200), " + ReceitaDAO.URL + " varchar(2000) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
