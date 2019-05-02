package br.com.receita_de_bolso.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.receita_de_bolso.DAO.CategoriaDAO;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "ReceitaDeBolsoDB";

    public DBHelper(Context context) {
        super(context, NOME_BANCO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Tabela categorias
        db.execSQL("create table " + CategoriaDAO.TABELA + " ( " + CategoriaDAO.ID + " integer primary key autoincrement, "  + CategoriaDAO.NOME + " varchar(100) not null, " + CategoriaDAO.DESCRICAO + " varchar(100), " + CategoriaDAO.NOME + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
