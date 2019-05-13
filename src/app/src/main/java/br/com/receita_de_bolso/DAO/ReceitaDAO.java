package br.com.receita_de_bolso.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.receita_de_bolso.Database.DBHelper;
import br.com.receita_de_bolso.Domain.Receita;

public class ReceitaDAO {

    private SQLiteDatabase db;
    private DBHelper banco;

    public static String TABELA = "receitas";
    public static String ID = "id";
    public static String CATEGORIA_ID = "categoria_id";
    public static String NOME = "nome";
    public static String TEMPO_PREPARO= "tempo_preparo";
    public static String RENDIMENTO = "rendimento";
    public static String INGREDIENTES = "ingredientes";
    public static String MODO_PREPARO = "modo_preparo";
    public static String ULTIMO_ACESSO = "ultimo_acesso";
    public static String IS_FAV = "is_fav";
    public static String NOME_IMAGEM = "nome_imagem";

    private static String PADRAO_DATA = "yyyy-MM-dd HH:mm:ss";

    public ReceitaDAO(Context context){
        banco = new DBHelper(context);
    }

    public long insert(Receita receita) {

        ContentValues valores = new ContentValues();
        db = banco.getWritableDatabase();

        valores.put(CATEGORIA_ID, receita.getCategoriaId());
        valores.put(NOME, receita.getNome());
        valores.put(TEMPO_PREPARO, receita.getTempoPreparo());
        valores.put(RENDIMENTO, receita.getRendimento());
        valores.put(INGREDIENTES, receita.getIngredientes());
        valores.put(MODO_PREPARO, receita.getModoPreparo());
        valores.put(IS_FAV, receita.getFav());
        valores.put(NOME_IMAGEM, receita.getImageName());

        DateFormat dateFormat = new SimpleDateFormat(PADRAO_DATA);
        String strDataUltimoAcesso = dateFormat.format(receita.getUltimoAcesso());

        valores.put(ULTIMO_ACESSO, strDataUltimoAcesso);

        long resultado = db.insert(TABELA, null, valores);

        db.close();

        return resultado;
    }

    public int update(Receita receita){

        ContentValues valores = new ContentValues();
        db = banco.getWritableDatabase();

        String where = ID + "=" + receita.getId();

        valores.put(CATEGORIA_ID, receita.getCategoriaId());
        valores.put(NOME, receita.getNome());
        valores.put(TEMPO_PREPARO, receita.getTempoPreparo());
        valores.put(RENDIMENTO, receita.getRendimento());
        valores.put(INGREDIENTES, receita.getIngredientes());
        valores.put(MODO_PREPARO, receita.getModoPreparo());
        valores.put(IS_FAV, receita.getFav());
        valores.put(NOME_IMAGEM, receita.getImageName());

        DateFormat dateFormat = new SimpleDateFormat(PADRAO_DATA);
        String strDataUltimoAcesso = dateFormat.format(receita.getUltimoAcesso());

        valores.put(ULTIMO_ACESSO, strDataUltimoAcesso);

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

    public ArrayList<Receita> getAll() {
        db = banco.getReadableDatabase();

        Cursor cursor = db.query(TABELA, new String[] { ID, NOME_IMAGEM, CATEGORIA_ID, NOME, TEMPO_PREPARO, RENDIMENTO, INGREDIENTES, MODO_PREPARO, ULTIMO_ACESSO, IS_FAV}, null,null,null,null,null);
        ArrayList<Receita> result = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                Receita receita = new Receita();
                receita.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                receita.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                receita.setCategoriaId(cursor.getLong(cursor.getColumnIndex(CATEGORIA_ID)));
                receita.setTempoPreparo(cursor.getInt(cursor.getColumnIndex(TEMPO_PREPARO)));
                receita.setRendimento(cursor.getInt(cursor.getColumnIndex(RENDIMENTO)));
                receita.setIngredientes(cursor.getString(cursor.getColumnIndex(INGREDIENTES)));
                receita.setModoPreparo(cursor.getString(cursor.getColumnIndex(MODO_PREPARO)));
                receita.setFav(cursor.getInt(cursor.getColumnIndex(IS_FAV)) == 1);
                receita.setImageName(cursor.getString(cursor.getColumnIndex(NOME_IMAGEM)));

                SimpleDateFormat formatter = new SimpleDateFormat(PADRAO_DATA);

                try {
                    Date date = new Date(formatter.parse(cursor.getString(cursor.getColumnIndex(ULTIMO_ACESSO))).getTime());
                    receita.setUltimoAcesso(date);
                } catch (ParseException e) {
                    //hihi
                }

                result.add(receita);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return result;
    }

    public ArrayList<Receita> getAllRecentes() {
        db = banco.getReadableDatabase();

        Cursor cursor = db.query(TABELA, new String[] { ID, NOME_IMAGEM, CATEGORIA_ID, NOME, TEMPO_PREPARO, RENDIMENTO, INGREDIENTES, MODO_PREPARO, ULTIMO_ACESSO, IS_FAV}, null,null,null,null,  ULTIMO_ACESSO + " DESC ", "10");
        ArrayList<Receita> result = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                Receita receita = new Receita();
                receita.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                receita.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                receita.setCategoriaId(cursor.getLong(cursor.getColumnIndex(CATEGORIA_ID)));
                receita.setTempoPreparo(cursor.getInt(cursor.getColumnIndex(TEMPO_PREPARO)));
                receita.setRendimento(cursor.getInt(cursor.getColumnIndex(RENDIMENTO)));
                receita.setIngredientes(cursor.getString(cursor.getColumnIndex(INGREDIENTES)));
                receita.setModoPreparo(cursor.getString(cursor.getColumnIndex(MODO_PREPARO)));
                receita.setFav(cursor.getInt(cursor.getColumnIndex(IS_FAV)) == 1);
                receita.setImageName(cursor.getString(cursor.getColumnIndex(NOME_IMAGEM)));

                SimpleDateFormat formatter = new SimpleDateFormat(PADRAO_DATA);

                try {
                    Date date = new Date(formatter.parse(cursor.getString(cursor.getColumnIndex(ULTIMO_ACESSO))).getTime());
                    receita.setUltimoAcesso(date);
                } catch (ParseException e) {
                    //hihi
                }

                result.add(receita);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return result;
    }

    public Receita getById(Long id) {

        String where = ID + "=" + id;
        db = banco.getReadableDatabase();
        Cursor cursor = db.query(TABELA, new String[] { ID, NOME_IMAGEM, CATEGORIA_ID, NOME, TEMPO_PREPARO, RENDIMENTO, INGREDIENTES, MODO_PREPARO, ULTIMO_ACESSO, IS_FAV}, where, null,null,null,null,null);

        if(cursor.moveToFirst()){

            Receita receita = new Receita();
            receita.setId(cursor.getLong(cursor.getColumnIndex(ID)));
            receita.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            receita.setCategoriaId(cursor.getLong(cursor.getColumnIndex(CATEGORIA_ID)));
            receita.setTempoPreparo(cursor.getInt(cursor.getColumnIndex(TEMPO_PREPARO)));
            receita.setRendimento(cursor.getInt(cursor.getColumnIndex(RENDIMENTO)));
            receita.setIngredientes(cursor.getString(cursor.getColumnIndex(INGREDIENTES)));
            receita.setModoPreparo(cursor.getString(cursor.getColumnIndex(MODO_PREPARO)));
            receita.setFav(cursor.getInt(cursor.getColumnIndex(IS_FAV)) == 1);
            receita.setImageName(cursor.getString(cursor.getColumnIndex(NOME_IMAGEM)));

            SimpleDateFormat formatter = new SimpleDateFormat(PADRAO_DATA);

            try {
                Date date = new Date(formatter.parse(cursor.getString(cursor.getColumnIndex(ULTIMO_ACESSO))).getTime());
                receita.setUltimoAcesso(date);
            } catch (ParseException e) {
                //hihi
            }

            db.close();

            return receita;
        }

        db.close();
        return null;
    }

    public ArrayList<Receita> getByCategoryId(Long id) {
        db = banco.getReadableDatabase();

        String where = CATEGORIA_ID + "=" + id;
        Cursor cursor = db.query(TABELA, new String[] { ID, NOME_IMAGEM, CATEGORIA_ID, NOME, TEMPO_PREPARO, RENDIMENTO, INGREDIENTES, MODO_PREPARO, ULTIMO_ACESSO, IS_FAV }, where, null,null,null,null,null);
        ArrayList<Receita> result = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                Receita receita = new Receita();
                receita.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                receita.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                receita.setCategoriaId(cursor.getLong(cursor.getColumnIndex(CATEGORIA_ID)));
                receita.setTempoPreparo(cursor.getInt(cursor.getColumnIndex(TEMPO_PREPARO)));
                receita.setRendimento(cursor.getInt(cursor.getColumnIndex(RENDIMENTO)));
                receita.setIngredientes(cursor.getString(cursor.getColumnIndex(INGREDIENTES)));
                receita.setModoPreparo(cursor.getString(cursor.getColumnIndex(MODO_PREPARO)));
                receita.setFav(cursor.getInt(cursor.getColumnIndex(IS_FAV)) == 1);
                receita.setImageName(cursor.getString(cursor.getColumnIndex(NOME_IMAGEM)));

                SimpleDateFormat formatter = new SimpleDateFormat(PADRAO_DATA);

                try {
                    Date date = new Date(formatter.parse(cursor.getString(cursor.getColumnIndex(ULTIMO_ACESSO))).getTime());
                    receita.setUltimoAcesso(date);
                } catch (ParseException e) {
                    //hihi
                }

                result.add(receita);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return result;
    }

    public ArrayList<Receita> getAllFav() {
        db = banco.getReadableDatabase();

        String where = IS_FAV + "=" + 1;
        Cursor cursor = db.query(TABELA, new String[] { ID, NOME_IMAGEM, CATEGORIA_ID, NOME, TEMPO_PREPARO, RENDIMENTO, INGREDIENTES, MODO_PREPARO, ULTIMO_ACESSO, IS_FAV }, where, null,null,null,null,null);
        ArrayList<Receita> result = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                Receita receita = new Receita();
                receita.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                receita.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                receita.setCategoriaId(cursor.getLong(cursor.getColumnIndex(CATEGORIA_ID)));
                receita.setTempoPreparo(cursor.getInt(cursor.getColumnIndex(TEMPO_PREPARO)));
                receita.setRendimento(cursor.getInt(cursor.getColumnIndex(RENDIMENTO)));
                receita.setIngredientes(cursor.getString(cursor.getColumnIndex(INGREDIENTES)));
                receita.setModoPreparo(cursor.getString(cursor.getColumnIndex(MODO_PREPARO)));
                receita.setFav(cursor.getInt(cursor.getColumnIndex(IS_FAV)) == 1);
                receita.setImageName(cursor.getString(cursor.getColumnIndex(NOME_IMAGEM)));

                SimpleDateFormat formatter = new SimpleDateFormat(PADRAO_DATA);

                try {
                    Date date = new Date(formatter.parse(cursor.getString(cursor.getColumnIndex(ULTIMO_ACESSO))).getTime());
                    receita.setUltimoAcesso(date);
                } catch (ParseException e) {
                    //hihi
                }

                result.add(receita);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return result;
    }
}
