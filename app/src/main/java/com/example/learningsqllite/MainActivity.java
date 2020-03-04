package com.example.learningsqllite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learningsqllite.db.CRUDHelper;
import com.example.learningsqllite.db.DatabaseContract;
import com.example.learningsqllite.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    CRUDHelper _crudHelper;
    DatabaseHelper _dbHelper;
    TextView _tvResult;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _tvResult = findViewById(R.id.tvResult);

        _dbHelper = new DatabaseHelper(this, "dbBimbingan", null, 1);
        _crudHelper = new CRUDHelper(_dbHelper);
        _crudHelper.clearTable(DatabaseContract.TbDosen.TABLE_NAME);
        _crudHelper.clearTable(DatabaseContract.TbMahasiswa.TABLE_NAME);
        _crudHelper.clearTable(DatabaseContract.TbJurusan.TABLE_NAME);


        ContentValues Dosen1 = new ContentValues();
        ContentValues Dosen2 = new ContentValues();
        ContentValues mhs1 = new ContentValues();
        ContentValues mhs2 = new ContentValues();
        ContentValues mhs3 = new ContentValues();
        ContentValues mhs4 = new ContentValues();
        ContentValues jrs1 = new ContentValues();
        ContentValues jrs2 = new ContentValues();
//        ContentValues mhs5 = new ContentValues();
        ContentValues[] mhsArr = {mhs3, mhs4};

        Dosen1.put("nama","akbar");
        Dosen1.put("email","muhammad.aminul@ub.ac.id");
        Dosen2.put("nama","afi");
        Dosen2.put("email","tri.afi@ub.ac.id");
        mhs1.put(DatabaseContract.TbMahasiswa.COLUMN_NAME_NAMA,"mhs1");
        mhs1.put("email","mhs1@ub.ac.id");
        mhs1.put("id_dosenpa","1");
        mhs1.put("id_jurusan","1");
        mhs2.put(DatabaseContract.TbMahasiswa.COLUMN_NAME_NAMA,"mhs2");
        mhs2.put("email","mhs2@ub.ac.id");
        mhs2.put("id_dosenpa","2");
        mhs2.put("id_jurusan","2");
        mhs3.put(DatabaseContract.TbMahasiswa.COLUMN_NAME_NAMA,"mhs3");
        mhs3.put("email","mhs3@ub.ac.id");
        mhs3.put("id_dosenpa","1");
        mhs3.put("id_jurusan","1");
        mhs4.put(DatabaseContract.TbMahasiswa.COLUMN_NAME_NAMA,"mhs3");
        mhs4.put("email","mhs3@ub.ac.id");
        mhs4.put("id_dosenpa","2");
        mhs4.put("id_jurusan","2");

        jrs1.put("nama", "Teknik Komputer");
        jrs2.put("nama", "Teknologi Informasi");

        _crudHelper.insertDosen(Dosen1);
        _crudHelper.insertDosen(Dosen2);
        _crudHelper.insertMahasiswa(mhs1);
        _crudHelper.insertMahasiswa(mhs2);
        _crudHelper.insertJurusan(jrs1);
        _crudHelper.insertJurusan(jrs2);

        _crudHelper.insertMahasiswaTransaction(mhsArr);
        _tvResult.setText("");
        _tvResult.append("Nama: "+_crudHelper.getMahasiswa(null).get(0).get("nama")+""+System.getProperty("line.separator")+
                "Email: "+_crudHelper.getMahasiswa(null).get(0).get("email")+System.getProperty("line.separator")+
                "Dosen PA: "+_crudHelper.getMahasiswa(null).get(0).get("dosenPa")+System.getProperty("line.separator")+
                "Jurusan: "+_crudHelper.getMahasiswa(null).get(0).get("jurusan")+System.getProperty("line.separator"));
        _tvResult.append(System.getProperty("line.separator"));
        _tvResult.append("Nama: "+_crudHelper.getMahasiswa(null).get(1).get("nama")+""+System.getProperty("line.separator")+
                "Email: "+_crudHelper.getMahasiswa(null).get(1).get("email")+System.getProperty("line.separator")+
                "Dosen PA: "+_crudHelper.getMahasiswa(null).get(1).get("dosenPa")+System.getProperty("line.separator")+
                "Jurusan: "+_crudHelper.getMahasiswa(null).get(1).get("jurusan")+System.getProperty("line.separator"));
        _tvResult.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _dbHelper.close();
    }
}
