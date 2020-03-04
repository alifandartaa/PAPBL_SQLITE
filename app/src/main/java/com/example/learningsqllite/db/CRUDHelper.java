package com.example.learningsqllite.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class CRUDHelper {
    private DatabaseHelper dbHelper;

    public CRUDHelper(DatabaseHelper _dbHelper) {
        this.dbHelper = _dbHelper;
    }

    public long insertDosen(ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.insert(DatabaseContract.TbDosen.TABLE_NAME, null, values);
    }

    public long insertMahasiswa(ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.insert(DatabaseContract.TbMahasiswa.TABLE_NAME, null, values);
    }

    public long insertJurusan(ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.insert(DatabaseContract.TbJurusan.TABLE_NAME, null, values);
    }

    public long insertMahasiswaTransaction(ContentValues[] values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        long _id = 0;
        try {
            for (ContentValues value : values) {
                db.insert(DatabaseContract.TbMahasiswa.TABLE_NAME, null, value);
            }
            db.setTransactionSuccessful();
            _id = 1;
        } finally {
            _id = 0;
            db.endTransaction();
        }
        return _id;
    }

    public ArrayList<HashMap<String, String>> getMahasiswa(@Nullable String _id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseContract.TbMahasiswa.TABLE_NAME + "." + BaseColumns._ID,
                DatabaseContract.TbMahasiswa.TABLE_NAME + "." + DatabaseContract.TbMahasiswa.COLUMN_NAME_NAMA + " as NamaMhs",
                //biar tidak ambigu nama kolom " nama" di
                //dosen dan mahasiswa
                DatabaseContract.TbMahasiswa.TABLE_NAME + "." + DatabaseContract.TbMahasiswa.COLUMN_NAME_EMAIL,
                DatabaseContract.TbDosen.TABLE_NAME + "." + DatabaseContract.TbDosen.COLUMN_NAME_NAMA + " as DosenPA",
                DatabaseContract.TbJurusan.TABLE_NAME + "." + DatabaseContract.TbJurusan.COLUMN_NAME_NAMA + " as Jurusan"
        };

        String selection =
                DatabaseContract.TbMahasiswa.TABLE_NAME + "." + DatabaseContract.TbMahasiswa.COLUMN_NAME_ID_DOSENPA +
                        " = " +
                        DatabaseContract.TbDosen.TABLE_NAME + "." + DatabaseContract.TbDosen._ID + " AND "+

                        DatabaseContract.TbMahasiswa.TABLE_NAME + "." +DatabaseContract.TbMahasiswa.COLUMN_NAME_ID_JURUSAN+
                        " = "+
                        DatabaseContract.TbJurusan.TABLE_NAME + "."+ DatabaseContract.TbJurusan._ID;

        String[] selectionArgs = null;
        if (_id != null) {
            selectionArgs = new String[]{_id};
            selection =  DatabaseContract.TbMahasiswa.TABLE_NAME + "." + DatabaseContract.TbMahasiswa.COLUMN_NAME_ID_DOSENPA +
                    " = " +
                    DatabaseContract.TbDosen.TABLE_NAME + "." + DatabaseContract.TbDosen._ID + " AND " +

                    DatabaseContract.TbMahasiswa.TABLE_NAME + "." + BaseColumns._ID +

                    DatabaseContract.TbMahasiswa.TABLE_NAME +"." +DatabaseContract.TbMahasiswa.COLUMN_NAME_ID_JURUSAN+
                    " = "+
                    DatabaseContract.TbJurusan.TABLE_NAME +"."+DatabaseContract.TbJurusan._ID;
        }

        Cursor cursor = db.query(
                DatabaseContract.TbMahasiswa.TABLE_NAME + " , " +
                        DatabaseContract.TbDosen.TABLE_NAME + " , " +
                        DatabaseContract.TbJurusan.TABLE_NAME,// The table to query
                projection, // The array of columns to return (pass null to get all)
                selection, // The columns for the WHERE clause
                selectionArgs, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        ArrayList<HashMap<String, String>> Mahasiswas = new ArrayList<HashMap<String, String>>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TbMahasiswa.TABLE_NAME + "." + DatabaseContract.TbMahasiswa._ID));
            String nama = cursor.getString(cursor.getColumnIndexOrThrow("NamaMhs"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TbMahasiswa.TABLE_NAME + "." + DatabaseContract.TbMahasiswa.COLUMN_NAME_EMAIL));
//            String dosenPa = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TbDosen.TABLE_NAME + "." + DatabaseContract.TbDosen.COLUMN_NAME_NAMA));
//            String jurusan = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TbJurusan.TABLE_NAME + "." + DatabaseContract.TbJurusan.COLUMN_NAME_NAMA));
            String dosenPa = cursor.getString(
                    /*cursor.getColumnIndexOrThrow(DatabaseContract.TbDosen.TABLE_NAME + "."
                            + DatabaseContract.TbDosen.COLUMN_NAME_NAMA));*/
                    cursor.getColumnIndexOrThrow("DosenPA"));
            String jurusan = cursor.getString(
                    /*cursor.getColumnIndexOrThrow(DatabaseContract.TbJurusan.TABLE_NAME+"."
                            +DatabaseContract.TbJurusan.COLUMN_NAME_NAMA));*/
                    cursor.getColumnIndexOrThrow("Jurusan"));

            HashMap<String, String> mahasiswa = new HashMap<String, String>();
            mahasiswa.put("id", id);
            mahasiswa.put("nama", nama);
            mahasiswa.put("email", email);
            mahasiswa.put("dosenPa", dosenPa);
            mahasiswa.put("jurusan", jurusan);
            Mahasiswas.add(mahasiswa);
        }
        cursor.close();
        return Mahasiswas;
    }

    public ArrayList<HashMap<String, String>> getDosen(@Nullable String _id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                DatabaseContract.TbDosen.COLUMN_NAME_NAMA,
                DatabaseContract.TbDosen.COLUMN_NAME_EMAIL
        };
        String selection = null;
        String[] selectionArgs = null;
        if (_id != null) {
            selectionArgs = new String[]{_id};
            selection = DatabaseContract.TbDosen._ID + " = ?";
        }
        String sortOrder = DatabaseContract.TbDosen.COLUMN_NAME_NAMA + " DESC";

        Cursor cursor = db.query(
                DatabaseContract.TbDosen.TABLE_NAME + "",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        ArrayList<HashMap<String, String>> Dosens = new ArrayList<HashMap<String, String>>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TbDosen._ID));
            String nama = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TbDosen.COLUMN_NAME_NAMA));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TbDosen.COLUMN_NAME_EMAIL));

            HashMap<String, String> dosen = new HashMap<String, String>();
            dosen.put("id", id);
            dosen.put("nama", nama);
            dosen.put("email", email);
            Dosens.add(dosen);
        }
        cursor.close();
        return Dosens;
    }

    public ArrayList<HashMap<String, String>> getJurusan(@Nullable String _id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                DatabaseContract.TbDosen.COLUMN_NAME_NAMA
        };
        String selection = null;
        String[] selectionArgs = null;
        if (_id != null) {
            selectionArgs = new String[]{_id};
            selection = DatabaseContract.TbDosen._ID + " = ?";
        }
        String sortOrder = DatabaseContract.TbDosen.COLUMN_NAME_NAMA + " DESC";

        Cursor cursor = db.query(
                DatabaseContract.TbDosen.TABLE_NAME + "",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        ArrayList<HashMap<String, String>> Jurusans = new ArrayList<HashMap<String, String>>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TbDosen._ID));
            String nama = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TbDosen.COLUMN_NAME_NAMA));

            HashMap<String, String> jurusan = new HashMap<String, String>();
            jurusan.put("id", id);
            jurusan.put("nama", nama);
            Jurusans.add(jurusan);
        }
        cursor.close();
        return Jurusans;
    }

    public int updateMahasiswa(String id, ContentValues cv) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DatabaseContract.TbMahasiswa._ID + " = ?";
        String[] selectionArgs = {id};
        return db.update(DatabaseContract.TbMahasiswa.TABLE_NAME, cv, selection, selectionArgs);
    }

    public int updateDosen(String id, ContentValues cv) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DatabaseContract.TbDosen._ID + " = ?";
        String[] selectionArgs = {id};
        return db.update(DatabaseContract.TbDosen.TABLE_NAME, cv, selection, selectionArgs);
    }

    public int deleteMahasiswa(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DatabaseContract.TbMahasiswa._ID + " = ?";
        String[] selectionArgs = {id};
        return db.delete(DatabaseContract.TbMahasiswa.TABLE_NAME, selection, selectionArgs);
    }

    public int deleteDosen(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DatabaseContract.TbDosen._ID + " = ?";
        String[] selectionArgs = {id};
        return db.delete(DatabaseContract.TbDosen.TABLE_NAME, selection, selectionArgs);
    }

    public void clearTable(String TABLE_NAME) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        db.execSQL(clearDBQuery);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME= '" + TABLE_NAME + "'");
    }
}
