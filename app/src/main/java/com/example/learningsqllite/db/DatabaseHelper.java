package com.example.learningsqllite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_TbMAHASISWA =
            String.format("CREATE TABLE %s (" +
                            "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "%s TEXT," +
                            "%s TEXT," +
                            "%s INTEGER," +
                            "%s INTEGER, " +
                            "FOREIGN KEY ( %s) " +
                            "REFERENCES %s (%s) " +
                            "ON UPDATE CASCADE ON DELETE CASCADE," +
                            "FOREIGN KEY ( %s) " +
                            "REFERENCES %s (%s) " +
                            "ON UPDATE CASCADE ON DELETE CASCADE )",
                    DatabaseContract.TbMahasiswa.TABLE_NAME,
                    DatabaseContract.TbMahasiswa._ID,
                    DatabaseContract.TbMahasiswa.COLUMN_NAME_NAMA,
                    DatabaseContract.TbMahasiswa.COLUMN_NAME_EMAIL,
                    DatabaseContract.TbMahasiswa.COLUMN_NAME_ID_DOSENPA,
                    DatabaseContract.TbMahasiswa.COLUMN_NAME_ID_JURUSAN,
                    DatabaseContract.TbMahasiswa.COLUMN_NAME_ID_DOSENPA,
                    DatabaseContract.TbDosen.TABLE_NAME,
                    DatabaseContract.TbDosen._ID,
                    DatabaseContract.TbMahasiswa.COLUMN_NAME_ID_JURUSAN,
                    DatabaseContract.TbJurusan.TABLE_NAME,
                    DatabaseContract.TbJurusan._ID);

    private static final String SQL_CREATE_TbDOSEN = "CREATE TABLE " +
            DatabaseContract.TbDosen.TABLE_NAME + " (" +
            DatabaseContract.TbDosen._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.TbDosen.COLUMN_NAME_NAMA + " TEXT,"
            + DatabaseContract.TbDosen.COLUMN_NAME_EMAIL + " TEXT)";

    private static final String SQL_DELETE_TbMahasiswa = "DROP TABLE IF EXISTS " +
            DatabaseContract.TbMahasiswa.TABLE_NAME;

    private static final String SQL_DELETE_TbDosen = "DROP TABLE IF EXISTS " +
            DatabaseContract.TbDosen.TABLE_NAME;

    private static final String SQL_CREATE_JURUSAN =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT)",
                    DatabaseContract.TbJurusan.TABLE_NAME,
                    DatabaseContract.TbJurusan._ID,
                    DatabaseContract.TbJurusan.COLUMN_NAME_NAMA
            );

    private static final String SQL_DELETE_JURUSAN = "DROP TABLE IF EXISTS " +
            DatabaseContract.TbDosen.TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TbDOSEN);
        db.execSQL(SQL_CREATE_TbMAHASISWA);
        db.execSQL(SQL_CREATE_JURUSAN);
    }

    public DatabaseHelper(Context context, String dbName, @Nullable SQLiteDatabase.CursorFactory factory, int dbVersion) {
        super(context, dbName, factory, dbVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TbDosen);
        db.execSQL(SQL_DELETE_TbMahasiswa);
        db.execSQL(SQL_DELETE_JURUSAN);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
