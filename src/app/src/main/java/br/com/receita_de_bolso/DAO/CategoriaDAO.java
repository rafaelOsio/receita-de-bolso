package br.com.receita_de_bolso.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.com.receita_de_bolso.Database.DBHelper;
import br.com.receita_de_bolso.Domain.Categoria;

public class CategoriaDAO {

    private SQLiteDatabase db;
    private DBHelper banco;

    public static String TABELA = "categorias";
    public static String ID = "id";
    public static String NOME = "nome";
    public static String DESCRICAO = "descricao";

    public CategoriaDAO(Context context){
        banco = new DBHelper(context);
    }

    //Retorna o ID da categoria inserida
    public long Add(Categoria categoria) {
        ContentValues valores = new ContentValues();
        db = banco.getWritableDatabase();

        valores.put(NOME, categoria.getNome());
        valores.put(DESCRICAO, categoria.getDescricao());

        long resultado = db.insert(TABELA, null, valores);

        db.close();

        if(resultado == -1)
            return -1;
        else
            return resultado;
    }

    public int Put(Categoria categoria){
        ContentValues valores = new ContentValues();
        db = banco.getWritableDatabase();

        String where = ID + "=" + categoria.getId();

        valores.put(NOME, categoria.getNome());
        valores.put(DESCRICAO, categoria.getDescricao());

        int response = db.update(TABELA, valores, where,null);
        db.close();

        return response;
    }



}
