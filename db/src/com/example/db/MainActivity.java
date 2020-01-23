package com.example.db;

import java.util.ArrayList;
import java.util.HashMap;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
 
public class MainActivity extends Activity implements OnClickListener {
 
    SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
 
    TableLayout tabelMahasiswa;
    Button buttonTambahMahasiswa;
    ArrayList<Button> buttonEdit = new ArrayList<Button>();
    ArrayList<Button> buttonDelete = new ArrayList<Button>();
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Appmahasiswa");
        setContentView(R.layout.activity_main);
 
        tabelMahasiswa = (TableLayout) findViewById(R.id.tabelMahasiswa);
        buttonTambahMahasiswa = (Button) findViewById(R.id.buttonTambahMahasiswa);
        buttonTambahMahasiswa.setOnClickListener(this);
 
        TableRow barisTabel = new TableRow(this);
        barisTabel.setBackgroundColor(Color.CYAN);
 
        TextView viewHeaderId = new TextView(this);
        TextView viewHeaderNim = new TextView(this);
        TextView viewHeaderNama = new TextView(this);
        TextView viewHeaderJurusan = new TextView(this);
        TextView viewHeaderAction = new TextView(this);
 
        viewHeaderId.setText("ID");
        viewHeaderNim.setText("Nim");
        viewHeaderNama.setText("Nama");
        viewHeaderJurusan.setText("Jurusan");
        viewHeaderAction.setText("Action");
 
        viewHeaderId.setPadding(5, 1, 5, 1);
        viewHeaderNim.setPadding(5, 1, 5, 1);
        viewHeaderNama.setPadding(5, 1, 5, 1);
        viewHeaderJurusan.setPadding(5, 1, 5, 1);
        viewHeaderAction.setPadding(5, 1, 5, 1);
 
        barisTabel.addView(viewHeaderId);
        barisTabel.addView(viewHeaderNim);
        barisTabel.addView(viewHeaderNama);
        barisTabel.addView(viewHeaderJurusan);
        barisTabel.addView(viewHeaderAction);
 
        tabelMahasiswa.addView(barisTabel, new TableLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
 
        ArrayList<HashMap<String, String>> arrayListMahasiswa = sqLiteHelper
                .tampil_semua_mahasiswa();
 
        if (arrayListMahasiswa.size() > 0) {
 
            for (int i = 0; i < arrayListMahasiswa.size(); i++) {
 
                // ambil masing-masing hasmap dari arrayListMahasiswa
                HashMap<String, String> hashMapRecordMahasiswa = arrayListMahasiswa
                        .get(i);
 
                // JSONObject jsonChildNode = arrayMahasiswa.getJSONObject(i);
                String nim = hashMapRecordMahasiswa.get("nim");
                String nama = hashMapRecordMahasiswa.get("nama");
                String jurusan = hashMapRecordMahasiswa.get("jurusan");
                String id = hashMapRecordMahasiswa.get("id");
 
                System.out.println("Nim :" + nim);
                System.out.println("Nama :" + nama);
                System.out.println("Jurusan :" + jurusan);
                System.out.println("ID :" + id);
 
                barisTabel = new TableRow(this);
 
                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }
 
                TextView viewId = new TextView(this);
                viewId.setText(id);
                viewId.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewId);
 
                TextView viewNim = new TextView(this);
                viewNim.setText(nim);
                viewNim.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewNim);
               
                TextView viewNama = new TextView(this);
                viewNama.setText(nama);
                viewNama.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewNama);
 
                TextView viewJurusan = new TextView(this);
                viewJurusan.setText(jurusan);
                viewJurusan.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewJurusan);
 
                buttonEdit.add(i, new Button(this));
                buttonEdit.get(i).setId(Integer.parseInt(id));
                buttonEdit.get(i).setTag("Edit");
                buttonEdit.get(i).setText("Edit");
                buttonEdit.get(i).setOnClickListener(this);
                barisTabel.addView(buttonEdit.get(i));
 
                buttonDelete.add(i, new Button(this));
                buttonDelete.get(i).setId(Integer.parseInt(id));
                buttonDelete.get(i).setTag("Delete");
                buttonDelete.get(i).setText("Delete");
                buttonDelete.get(i).setOnClickListener(this);
                barisTabel.addView(buttonDelete.get(i));
 
                tabelMahasiswa.addView(barisTabel, new TableLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
 
            }
        }
 
    }
 
    @Override
    public void onClick(View view) {
 
        if (view.getId() == R.id.buttonTambahMahasiswa) {
            // Toast.makeText(MainActivity.this, "Button Tambah Data",
            // Toast.LENGTH_SHORT).show();
 
            tambahMahasiswa();
 
        } else {
           
           // Melakukan pengecekan pada data array, agar sesuai dengan index masing-masing button
             
            for (int i = 0; i < buttonEdit.size(); i++) {
 
                // jika yang diklik adalah button edit
                if (view.getId() == buttonEdit.get(i).getId()
                        && view.getTag().toString().trim().equals("Edit")) {
                    // Toast.makeText(MainActivity.this, "Edit : " +
                    // buttonEdit.get(i).getId(), Toast.LENGTH_SHORT).show();
                    int id = buttonEdit.get(i).getId();
                    getDataByID(id);
 
                } // jika yang diklik adalah button delete
                else if (view.getId() == buttonDelete.get(i).getId()
                        && view.getTag().toString().trim().equals("Delete")) {
                    // Toast.makeText(MainActivity.this, "Delete : " +
                    // buttonDelete.get(i).getId(), Toast.LENGTH_SHORT).show();
                    int id = buttonDelete.get(i).getId();
                    deleteMahasiswa(id);
 
                }
            }
        }
    }
 
    public void deleteMahasiswa(int id) {
 
        sqLiteHelper.hapus_mahasiswa(id);
 
        // restart acrtivity
        finish();
        startActivity(getIntent());
 
    }
 
    public void getDataByID(int id) {
 
        String nimEdit = null, namaEdit = null, jurusanEdit = null;
 
        HashMap<String, String> hashMapMahasiswa = sqLiteHelper
                .tampil_mahasiswa_berdasarkan_id(id);
 
        for (int i = 0; i < hashMapMahasiswa.size(); i++) {
            nimEdit = hashMapMahasiswa.get("nim");
            namaEdit = hashMapMahasiswa.get("nama");
            jurusanEdit = hashMapMahasiswa.get("jurusan");
        }
 
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);
 
        // buat id tersembunyi di alertbuilder
        final TextView viewId = new TextView(this);
        viewId.setText(String.valueOf(id));
        viewId.setTextColor(Color.TRANSPARENT);
        layoutInput.addView(viewId);
 
        final EditText editNim = new EditText(this);
        editNim.setText(nimEdit);
        layoutInput.addView(editNim);
 
       
        final EditText editNama = new EditText(this);
        editNama.setText(namaEdit);
        layoutInput.addView(editNama);
 
        final EditText editJurusan = new EditText(this);
        editJurusan.setText(jurusanEdit);
        layoutInput.addView(editJurusan);
 
        AlertDialog.Builder builderEditMahasiswa = new AlertDialog.Builder(this);
        builderEditMahasiswa.setTitle("Update Mahasiswa");
        builderEditMahasiswa.setView(layoutInput);
        builderEditMahasiswa.setPositiveButton("Update",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nim = editNim.getText().toString();
                        String nama = editNama.getText().toString();
                        String jurusan = editJurusan.getText().toString();
 
                        System.out.println("Nim : " + nim + " Nama " + nama + " Jurusan : "
                                + jurusan);
 
                        sqLiteHelper.update_mahasiswa(Integer.parseInt(viewId
                                .getText().toString()), editNim.getText()
                                .toString(),editNama.getText().toString(),
                                editJurusan.getText().toString());
 
                        // restart acrtivity
                        finish();
                        startActivity(getIntent());
                    }
 
                });
 
        builderEditMahasiswa.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builderEditMahasiswa.show();
 
    }
 
    public void tambahMahasiswa() {
        // layout akan ditampilkan pada AlertDialog
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);
 
        final EditText editNim = new EditText(this);
        editNim.setHint("Nim");
        layoutInput.addView(editNim);
       
        final EditText editNama = new EditText(this);
        editNama.setHint("Nama");
        layoutInput.addView(editNama);
 
        final EditText editJurusan = new EditText(this);
        editJurusan.setHint("Jurusan");
        layoutInput.addView(editJurusan);
 
        AlertDialog.Builder builderInsertMahasiswa = new AlertDialog.Builder(this);
        builderInsertMahasiswa.setTitle("Insert Mahasiswa");
        builderInsertMahasiswa.setView(layoutInput);
        builderInsertMahasiswa.setPositiveButton("Insert",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nim = editNim.getText().toString();
                        String nama = editNama.getText().toString();
                        String jurusan = editJurusan.getText().toString();
 
                        System.out.println(" Nim " + nim + "Nama : " + nama + " Jurusan : "
                                + jurusan);
 
                        sqLiteHelper.tambah_mahasiswa(nim,nama,jurusan);
                        // restart acrtivity
                        finish();
                        startActivity(getIntent());
                    }
 
                });
 
        builderInsertMahasiswa.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builderInsertMahasiswa.show();
    }
}