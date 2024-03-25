package com.uth.tarea4p2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.uth.tarea4p2.models.Registros;

import java.util.ArrayList;

public class DbRegistros extends DbHelper {

    Context context;

    public DbRegistros(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarData(byte[] imagen, String descripcion) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("imagen", imagen);
            values.put("descripcion", descripcion);

            id = db.insert(TABLE_REGISTROS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Registros> mostrarData() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Registros> listaRegistros = new ArrayList<>();
        Registros registro;
        Cursor cursorRegistros;

        cursorRegistros = db.rawQuery("SELECT * FROM " + TABLE_REGISTROS + " ORDER BY descripcion ASC", null);

        if (cursorRegistros.moveToFirst()) {
            do {
                registro = new Registros();
                registro.setId(cursorRegistros.getInt(0));
                registro.setImagen(cursorRegistros.getBlob(1));
                registro.setDescripcion(cursorRegistros.getString(2));
                listaRegistros.add(registro);
            } while (cursorRegistros.moveToNext());
        }

        cursorRegistros.close();

        return listaRegistros;
    }

    public Registros verData(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Registros registro = null;
        Cursor cursorRegistros;

        cursorRegistros = db.rawQuery("SELECT * FROM " + TABLE_REGISTROS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorRegistros.moveToFirst()) {
            registro = new Registros();
            registro.setId(cursorRegistros.getInt(0));
            registro.setImagen(cursorRegistros.getBlob(1));
            registro.setDescripcion(cursorRegistros.getString(2));
        }

        cursorRegistros.close();

        return registro;
    }

    public boolean editarData(int id, byte[] FirmaDigital, String descripcion) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("imagen", FirmaDigital);
        values.put("descripcion", descripcion);

        try {
            db.update(TABLE_REGISTROS, values, "id = ?",new String[]{String.valueOf(id)});
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarData(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_REGISTROS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
