package com.example.db;

import java.util.ArrayList;

import java.util.HashMap;
 
 
 
import android.content.ContentValues;
 
import android.content.Context;
 
import android.database.Cursor;
 
import android.database.sqlite.SQLiteDatabase;
 
import android.database.sqlite.SQLiteOpenHelper;
 
 
 
public class SQLiteHelper extends SQLiteOpenHelper {
 
 
 
    private static final String nama_database = "adit_jr.db";
 
    private static final int versi_database = 1;
 
    private static final String query_buat_tabel_mahasiswa_pemain = "CREATE TABLE IF NOT EXISTS tabel_mahasiswa (id INTEGER PRIMARY KEY AUTOINCREMENT, nim INTEGER, nama TEXT, jurusan TEXT)";
 
    private static final String query_hapus_tabel_mahasiswa_pemain = "DROP TABLE IF EXISTS query_buat_tabel_mahasiswa_pemain";
 
 
 
    public SQLiteHelper(Context context) {
 
        super(context, nama_database, null, versi_database);
 
    }
 
 
 
    @Override
 
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
 
        sqLiteDatabase.execSQL(query_buat_tabel_mahasiswa_pemain);
 
        System.out.println("tabel_mahasiswa sudah dibuat");
 
    }
 
 
 
    @Override
 
    public void onUpgrade(SQLiteDatabase database, int versi_lama,
 
            int versi_baru) {
 
        database.execSQL(query_hapus_tabel_mahasiswa_pemain);
 
        onCreate(database);
 
 
 
    }
 
 
 
    public void tambah_mahasiswa(String nim, String nama, String jurusan) {
 
        SQLiteDatabase database = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
 
        values.put("nim", nim);
 
        values.put("nama", nama);
 
        values.put("jurusan",jurusan);
 
        database.insert("tabel_mahasiswa", null, values);
 
        database.close();
 
    }
 
   
 
    public ArrayList<HashMap<String, String>> tampil_semua_mahasiswa() {
 
 
 
        // deklarasikan sebuah arraylist yang bisa menampung hashmap
 
        ArrayList<HashMap<String, String>> arrayListMahasiswa= new ArrayList<HashMap<String, String>>();
 
 
 
        SQLiteDatabase database = this.getWritableDatabase();
 
 
 
        Cursor cursor = database.rawQuery("SELECT * FROM tabel_mahasiswa", null);
 
 
 
        // kursor langsung diarkan ke posisi paling awal data pada tabel_mahasiswa
 
        if (cursor.moveToFirst()) {
 
            do {
 
                // deklarasikan sebuah hashmap, yang bisa menamp
 
                HashMap<String, String> hashMapmahasiswa = new HashMap<String, String>();
 
 
 
                // masukkan masing-masing field dari tabel_mahasiswa ke dalam hashMapMahasiswa
 
                //pastikan id_mahasiswa, nim, nama dan jurusan sama persis dengan field yang ada pada id_mahasiswa
 
                hashMapmahasiswa.put("id", cursor.getString(0));
 
                hashMapmahasiswa.put("nim", cursor.getString(1));
 
                hashMapmahasiswa.put("nama", cursor.getString(2));
 
                hashMapmahasiswa.put("jurusan", cursor.getString(3));
 
 
 
                // masukkan hashMapmahasiswa ke dalam arrayListMahasiswa
 
                arrayListMahasiswa.add(hashMapmahasiswa);
 
 
 
            } while (cursor.moveToNext());
 
        }
 
 
 
        return arrayListMahasiswa;
 
    }
 
   
 
    public int update_mahasiswa(int id, String nim, String nama, String jurusan) {
 
        SQLiteDatabase database = this.getWritableDatabase();
 
        ContentValues recordMahasiswa = new ContentValues();
 
        recordMahasiswa.put("nim", nim);
 
        recordMahasiswa.put("nama", nama);
 
        recordMahasiswa.put("jurusan", jurusan);
 
        return database.update("tabel_mahasiswa", recordMahasiswa, "id=" + id, null);
 
    }
 
   
 
    public void hapus_mahasiswa (int id) {
 
        SQLiteDatabase database = this.getWritableDatabase();
 
        database.execSQL("DELETE FROM  tabel_mahasiswa WHERE id='" + id+ "'");
 
        database.close();
 
    }
 
   
 
    public HashMap<String, String> tampil_mahasiswa_berdasarkan_id(int id) {
 
       
 
        SQLiteDatabase database = this.getReadableDatabase();
 
       
 
        HashMap<String, String> hashMapmahasiswa = new HashMap<String, String>();
 
        Cursor cursor = database.rawQuery("SELECT * FROM tabel_mahasiswa WHERE id=" + id + "", null);
 
       
 
        if (cursor.moveToFirst()) {
 
            do {
 
                hashMapmahasiswa.put("id", cursor.getString(0));
 
                hashMapmahasiswa.put("nim", cursor.getString(1));
 
                hashMapmahasiswa.put("nama", cursor.getString(2));
 
                hashMapmahasiswa.put("jurusan", cursor.getString(3));
 
            } while (cursor.moveToNext());
 
        }
 
        return hashMapmahasiswa;
 
    }
   
 
}
