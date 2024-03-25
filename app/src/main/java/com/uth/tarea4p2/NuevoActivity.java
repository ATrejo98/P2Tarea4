package com.uth.tarea4p2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uth.tarea4p2.db.DbRegistros;


public class NuevoActivity extends AppCompatActivity {

    EditText txtDescripcion;
    Button btnGuarda, btnLimpiarfirma;
    private FirmaCampoDibujo dibujarfirma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Nuevo Registro");
        }

        btnLimpiarfirma = findViewById(R.id.btnLimpiarfirma);
        dibujarfirma = findViewById(R.id.lienzo);
        txtDescripcion = findViewById(R.id.txtDescripcion);

        btnGuarda = findViewById(R.id.btnGuarda);
        btnGuarda.setText(R.string.btn_guarda);

        btnLimpiarfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dibujarfirma.limpiarFirma();
            }
        });

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtDescripcion.getText().toString().equals("") ){
                    ShowAlerta("Debe Registrar una Descripcion.");
                }
                 else if (dibujarfirma.FirmaDigital().length == 0) {
                    ShowAlerta("Debe Dibujar una Firma.");
                } else{
                    DbRegistros dbRegistros = new DbRegistros(NuevoActivity.this);
                    long id = dbRegistros.insertarData(dibujarfirma.FirmaDigital(), txtDescripcion.getText().toString());

                    if (id > 0) {
                        Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        lista();
                    } else {
                        Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void ShowAlerta(String mensaje) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Atencion");
        dialog.setMessage(mensaje);
        dialog.setPositiveButton("Aceptar", null);
        dialog.setCancelable(false);
        dialog.show();
    }
    private void limpiar() {
        txtDescripcion.setText("");
    }
    private void lista(){
        limpiar();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}