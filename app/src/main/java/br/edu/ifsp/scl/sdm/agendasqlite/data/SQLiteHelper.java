package br.edu.ifsp.scl.sdm.agendasqlite.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "agenda.db";
    static final String TABLE_NAME = "contatos";
    static final String KEY_ID = "id";
    static final String KEY_NOME = "nome";
    static final String KEY_FONE = "fone";
    static final String KEY_FONE2 = "fone2";
    static final String KEY_EMAIL = "email";
    static final String KEY_FAVORITO = "favorito";
    static final String KEY_DIA_MES_ANIVERSARIO = "dia_mes_aniversario";

    private static final int DATABASE_VERSION = 4;

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                                            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                            + KEY_NOME + " TEXT, "
                                            + KEY_FONE + " TEXT, "
                                            + KEY_FONE2 + " TEXT, "
                                            + KEY_EMAIL + " TEXT,"
                                            + KEY_DIA_MES_ANIVERSARIO + " TEXT,"
                                            + KEY_FAVORITO + " INTEGER );";

    private static final String ALTER_TABLE_FAVORITO = "ALTER TABLE " + TABLE_NAME
                                                + " ADD " + KEY_FAVORITO +" INTEGER";

    private static final String ALTER_TABLE_FONE2 = "ALTER TABLE " + TABLE_NAME
            + " ADD " + KEY_FONE2 +" TEXT";

    private static final String ALTER_TABLE_DIAMESANIVERSARIO = "ALTER TABLE " + TABLE_NAME
            + " ADD " + KEY_DIA_MES_ANIVERSARIO +" TEXT";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (newVersion){
            case(2):
                db.execSQL(ALTER_TABLE_FAVORITO);
            case(3):
                db.execSQL(ALTER_TABLE_FONE2);
            case(4):
                db.execSQL(ALTER_TABLE_DIAMESANIVERSARIO);
        }
    }
}
