package br.com.receita_de_bolso.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public long insert(Categoria categoria) {
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

    public int update(Categoria categoria){
        ContentValues valores = new ContentValues();
        db = banco.getWritableDatabase();

        String where = ID + "=" + categoria.getId();

        valores.put(NOME, categoria.getNome());
        valores.put(DESCRICAO, categoria.getDescricao());

        int response = db.update(TABELA, valores, where,null);
        db.close();

        return response;
    }

    public Boolean delete(Long id) {
        db = banco.getWritableDatabase();
        String where = ID + "=" + id;

        Boolean response = db.delete(TABELA, where, null) > 0;
        db.close();

        return response;
    }


    public ArrayList<Categoria> getAll() {
        db = banco.getReadableDatabase();

        Cursor cursor = db.query(TABELA, new String[] { ID, NOME, DESCRICAO}, null,null,null,null,null);
        ArrayList<Categoria> result = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                Categoria categoria = new Categoria();
                categoria.setId(cursor.getLong(cursor.getColumnIndex(ID)));

                result.add(categoria);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return result;
    }

    public Categoria getById(Long id) {

        String where = ID + "=" + id;
        db = banco.getReadableDatabase();
        Cursor cursor = db.query(TABELA,new String[] {ID, NOME, DESCRICAO}, where,null,null,null,null);

        if(cursor.moveToFirst()){

            Categoria categoria = new Categoria();
            categoria.setId(cursor.getLong(cursor.getColumnIndex(ID)));
            categoria.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            categoria.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));

            db.close();

            return categoria;
        }

        db.close();
        return null;
    }

    public Categoria getByNome(String nome) {

        String where = NOME + " = ' " + nome + " ' ";
        db = banco.getReadableDatabase();
        Cursor cursor = db.query(TABELA,new String[] {ID, NOME, DESCRICAO}, where ,null,null,null,null);

        if(cursor.moveToFirst()){

            Categoria categoria = new Categoria();
            categoria.setId(cursor.getLong(cursor.getColumnIndex(ID)));
            categoria.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            categoria.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));

            db.close();

            return categoria;
        }

        db.close();
        return null;
    }
}
