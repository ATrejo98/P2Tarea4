package com.uth.tarea4p2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.uth.tarea4p2.db.DbRegistros;
import com.uth.tarea4p2.models.Registros;

import androidx.appcompat.widget.Toolbar;

public class EditarActivity extends AppCompatActivity {

    EditText txtDescripcion;
    Button btnGuarda, btnLimpiarfirma;
    boolean correcto = false;
    Registros registro;
    int id = 0;
    private FirmaCampoDibujo dibujarfirma;


    @SuppressLint("RestrictedApi")
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
            getSupportActionBar().setTitle("Actualizar Registro");
        }

        btnLimpiarfirma = findViewById(R.id.btnLimpiarfirma);
        dibujarfirma = findViewById(R.id.lienzo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnGuarda.setText(R.string.btn_editar);

        btnLimpiarfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dibujarfirma.limpiarFirma();
            }
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbRegistros dbRegistros = new DbRegistros(EditarActivity.this);
        registro = dbRegistros.verData(id);

        if (registro != null) {
            txtDescripcion.setText(registro.getDescripcion());
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtDescripcion.getText().toString().equals("") ){
                    ShowAlerta("Debe Registrar una Descripcion.");
                } else if (dibujarfirma.FirmaDigital().length == 0) {
                    ShowAlerta("Debe Dibujar una nueva Firma.");
                }  else{

                    correcto = dbRegistros.editarData(id, dibujarfirma.FirmaDigital(), txtDescripcion.getText().toString());

                    if(correcto){
                        Toast.makeText(EditarActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        lista();
                    } else {
                        Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
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

    private void lista(){
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