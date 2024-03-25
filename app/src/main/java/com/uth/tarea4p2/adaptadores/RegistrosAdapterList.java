package com.uth.tarea4p2.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uth.tarea4p2.R;
import com.uth.tarea4p2.VerActivity;
import com.uth.tarea4p2.models.Registros;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrosAdapterList extends RecyclerView.Adapter<RegistrosAdapterList.RegistroViewHolder> {

    ArrayList<Registros> listaRegistros;
    ArrayList<Registros> listaOriginal;

    public RegistrosAdapterList(ArrayList<Registros> listaRegistros) {
        this.listaRegistros = listaRegistros;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaRegistros);
    }

    @NonNull
    @Override
    public RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_registro, null, false);
        return new RegistroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroViewHolder holder, int position) {

        holder.viewDescripcion.setText(listaRegistros.get(position).getDescripcion());

        byte[] photoBytes = listaRegistros.get(position).getImagen();
        Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
        holder.viewFoto.setImageBitmap(bitmap);
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaRegistros.clear();
            listaRegistros.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Registros> collecion = listaRegistros.stream()
                        .filter(i -> i.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaRegistros.clear();
                listaRegistros.addAll(collecion);
            } else {
                for (Registros c : listaOriginal) {
                    if (c.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaRegistros.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return listaRegistros.size();
    }

    public class RegistroViewHolder extends RecyclerView.ViewHolder {

        TextView viewDescripcion;
        ImageView viewFoto;

        public RegistroViewHolder(@NonNull View itemView) {
            super(itemView);

            viewDescripcion = itemView.findViewById(R.id.viewDescripcion);
            viewFoto = itemView.findViewById(R.id.viewFoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    intent.putExtra("ID", listaRegistros.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
