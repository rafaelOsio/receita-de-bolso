package br.com.receita_de_bolso.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        valores.put(INGREDIENTES, receita.getIngridientes());
        valores.put(MODO_PREPARO, receita.getModoPreparo());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
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
        valores.put(INGREDIENTES, receita.getIngridientes());
        valores.put(MODO_PREPARO, receita.getModoPreparo());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
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

        Cursor cursor = db.query(TABELA, new String[] { ID, CATEGORIA_ID, NOME, TEMPO_PREPARO, RENDIMENTO, INGREDIENTES, MODO_PREPARO}, null,null,null,null,null);
        ArrayList<Receita> result = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                Receita receita = new Receita();
                receita.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                receita.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                receita.setCategoriaId(cursor.getLong(cursor.getColumnIndex(CATEGORIA_ID)));
                receita.setTempoPreparo(cursor.getInt(cursor.getColumnIndex(TEMPO_PREPARO)));
                receita.setRendimento(cursor.getInt(cursor.getColumnIndex(RENDIMENTO)));
                receita.setIngridientes(cursor.getString(cursor.getColumnIndex(INGREDIENTES)));
                receita.setModoPreparo(cursor.getString(cursor.getColumnIndex(MODO_PREPARO)));

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

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
        Cursor cursor = db.query(TABELA, new String[] { ID, CATEGORIA_ID, NOME, TEMPO_PREPARO, RENDIMENTO, INGREDIENTES, MODO_PREPARO}, where, null,null,null,null,null);

        if(cursor.moveToFirst()){

            Receita receita = new Receita();
            receita.setId(cursor.getLong(cursor.getColumnIndex(ID)));
            receita.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            receita.setCategoriaId(cursor.getLong(cursor.getColumnIndex(CATEGORIA_ID)));
            receita.setTempoPreparo(cursor.getInt(cursor.getColumnIndex(TEMPO_PREPARO)));
            receita.setRendimento(cursor.getInt(cursor.getColumnIndex(RENDIMENTO)));
            receita.setIngridientes(cursor.getString(cursor.getColumnIndex(INGREDIENTES)));
            receita.setModoPreparo(cursor.getString(cursor.getColumnIndex(MODO_PREPARO)));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

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
}
