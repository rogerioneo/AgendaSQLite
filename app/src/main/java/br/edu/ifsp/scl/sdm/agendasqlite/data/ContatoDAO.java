package br.edu.ifsp.scl.sdm.agendasqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.edu.ifsp.scl.sdm.agendasqlite.model.Contato;

public class ContatoDAO {

    SQLiteDatabase database;
    SQLiteHelper dbHelper;

    ContatoDAO(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }

    public void incluirContato(Contato contato) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NOME, contato.getNome());
        values.put(SQLiteHelper.KEY_FONE, contato.getFone());
        values.put(SQLiteHelper.KEY_EMAIL, contato.getEmail());

        database.insert(SQLiteHelper.TABLE_NAME, null, values);

        database.close();
    }
}
