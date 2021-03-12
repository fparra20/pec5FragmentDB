package hfad.com.pec4mascotas;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hfad.com.pec4mascotas.database.PetsDataBaseHelper;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    ArrayList<Pet> pets;
    Activity activity;

    public PetAdapter(ArrayList<Pet> pets, Activity activity) {
        this.pets = pets;
        this.activity = activity;
    }
    public static class PetViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPhoto;
        private TextView tvName;
        private TextView tvRating;
        private ImageButton ibPuntuation;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            // Iniciamos todos los item con los que vamos a trabajar
            imgPhoto = (ImageView)itemView.findViewById(R.id.imgPhoto);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvRating = (TextView)itemView.findViewById(R.id.tvRating);
            ibPuntuation = (ImageButton)itemView.findViewById(R.id.ibPuntuation);
        }
    }
    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pet,parent,false);
        return new PetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder petViewHolder, int position) {
        Pet pet = pets.get(position);
        petViewHolder.imgPhoto.setImageResource(pet.getPhoto());
        petViewHolder.tvName.setText(pet.getName());
        petViewHolder.tvRating.setText(String.valueOf(pet.getRating()));

        // Que si clickamos el nombre, nos salga un toast con nombre completo
        petViewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,pet.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        // Evento cuando se clicka el camaleon para puntuar
        petViewHolder.ibPuntuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Guardamos el valor actual de puntuación
                int currentValue=pet.getRating();

                // Sumamos 1
                int newValue = currentValue+1;

                // Cambiamos el rating de la mascota en cuestion
                pet.setRating(newValue);

                // Actualizamos también el TextView que lo muestra
                petViewHolder.tvRating.setText(String.valueOf(newValue));

                // Creamos el cursor para traer los datos de la BD
                SQLiteOpenHelper PetsDataBaseHelper = new PetsDataBaseHelper(v.getContext());

                // Extrae la base de datos para trabajar con ella
                SQLiteDatabase db = PetsDataBaseHelper.getReadableDatabase();

                ContentValues petValues = new ContentValues();
                /*
                 * creamos cada uno de los campos de la fila a insertar
                 */
                petValues.put("RATING",String.valueOf(newValue));

                // Ejecutamos el update del rating en esta mascota en concreto
                db.update("PET", petValues,"_id= ?",new String[]{String.valueOf(pet.getPet_id())});

            }
        });
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }
}
