package com.uth.tarea4p2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.uth.tarea4p2.db.DbRegistros;
import com.uth.tarea4p2.models.Registros;

public class VerActivity extends AppCompatActivity {

    EditText txtDescripcion;
    ImageView imgFoto;
    Button btnGuarda;
    Registros registro;
    int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Detalle del Registro");
        }

        View btnEliminar = findViewById(R.id.btnEliminar);
        View btnActualizar = findViewById(R.id.btnActualizar);


        txtDescripcion = findViewById(R.id.txtDescripcion);
        imgFoto = findViewById(R.id.imgFoto);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnGuarda.setVisibility(View.INVISIBLE);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbRegistros dbRegistros = new DbRegistros(VerActivity.this);
        registro = dbRegistros.verData(id);



        if(registro != null){
            txtDescripcion.setText(registro.getDescripcion());

            byte[] photoBytes = registro.getImagen();
            Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
            imgFoto.setImageBitmap(bitmap);
        }

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(VerActivity.this);
                builder.setTitle("Atencion");
                builder.setMessage("¿Desea eliminar el registro " + registro.getDescripcion() + " ?");
                builder.setPositiveButton("Aceptar", (di, i) -> {
                    if(dbRegistros.eliminarData(id)){
                        lista();
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();

            }
        });
    }



    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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