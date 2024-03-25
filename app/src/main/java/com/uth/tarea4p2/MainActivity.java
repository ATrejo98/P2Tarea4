package com.uth.tarea4p2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uth.tarea4p2.adaptadores.RegistrosAdapterList;
import com.uth.tarea4p2.db.DbRegistros;
import com.uth.tarea4p2.models.Registros;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaRegistros;
    ArrayList<Registros> listaArrayRegistros;
    FloatingActionButton fabNuevo, fabAbout;
    RegistrosAdapterList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtBuscar = findViewById(R.id.txtBuscar);
        listaRegistros = findViewById(R.id.listaRegistros);
        fabNuevo = findViewById(R.id.favNuevo);
        fabAbout = findViewById(R.id.favAbout);
        listaRegistros.setLayoutManager(new LinearLayoutManager(this));

        DbRegistros dbRegistros = new DbRegistros(MainActivity.this);

        listaArrayRegistros = new ArrayList<>();

        adapter = new RegistrosAdapterList(dbRegistros.mostrarData());
        listaRegistros.setAdapter(adapter);

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registro();
            }
        });

        fabAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAboutDialog(MainActivity.this);
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }



    private void Registro(){
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }

    public  static  void showAboutDialog(Activity activity) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.dialog_about, null);
        TextView txtAppVersion = view.findViewById(R.id.txt_app_version);
        txtAppVersion.setText(activity.getString(R.string.msg_about_version) + " " + 1 + " (" + 1.0 + ")");
        final MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(activity);
        alert.setView(view);
        alert.setPositiveButton(R.string.dialog_option_ok, (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
}