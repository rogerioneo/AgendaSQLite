package br.edu.ifsp.scl.sdm.agendasqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.scl.sdm.agendasqlite.model.Contato;

public class ContatoDAO {

    SQLiteDatabase database;
    SQLiteHelper dbHelper;

    public ContatoDAO(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }

    public List<Contato> listaContatos() {
        database = dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        cursor = database.query(SQLiteHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                SQLiteHelper.KEY_NOME);
        while (cursor.moveToNext()) {
            Contato c = new Contato();
            c.setId(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.KEY_ID)));             //cursor.getInt(0)
            c.setNome(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_NOME)));      //cursor.getString(1)
            c.setFone(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_FONE)));      //cursor.getString(2)
            c.setEmail(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_EMAIL)));    //cursor.getString(3)
            contatos.add(c);
        }

        cursor.close();
        database.close();

        return contatos;
    }


    public long incluirContato(Contato contato) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NOME, contato.getNome());
        values.put(SQLiteHelper.KEY_FONE, contato.getFone());
        values.put(SQLiteHelper.KEY_EMAIL, contato.getEmail());

        long id = database.insert(SQLiteHelper.TABLE_NAME, null, values);

        database.close();
        return id;
    }

    public void alterarContato(Contato contato) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NOME, contato.getNome());
        values.put(SQLiteHelper.KEY_FONE, contato.getFone());
        values.put(SQLiteHelper.KEY_EMAIL, contato.getEmail());

        database.update(SQLiteHelper.TABLE_NAME, values,
                SQLiteHelper.KEY_ID + "=" + contato.getId(), null);
        database.close();
    }

    public void excluirContato(Contato contato) {
        database = dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.TABLE_NAME,
                SQLiteHelper.KEY_ID + "=" + contato.getId(), null);
        database.close();
    }
}
