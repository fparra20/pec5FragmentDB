package hfad.com.pec4mascotas;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    ArrayList<Pet> pets;
    Activity activity;
    TextView textView;

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
        petViewHolder.tvRating.setText(pet.getRating());

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

                // Creamos un cuadro de diálogo
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                // Creamos un linea layout para el dialogo
                LinearLayout linearLayout = new LinearLayout(v.getContext());

                // Creamos un objeto rating bar
                RatingBar rating = new RatingBar(v.getContext());

                // Damos a RatingBar parámetros de LinearLayour, estos son los requeridos:
                // layout_height y layout_widht, los ponemos en wrap content
                rating.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                // Ponemos máximo 5 estrellas
                rating.setNumStars(5);

                // Y que se vaya llenando con un incremento de 0.5
                rating.setStepSize((float)0.5);

                //add ratingBar to linearLayout
                linearLayout.addView(rating);

                // En la API descubrimos que para cada tipo de gravity hay un int asociado,
                // el 1 corresponde a center_horizontal
                linearLayout.setGravity(1);

                // Ponemos el icono al lado del título
                alertDialog.setIcon(R.drawable.puntuation_filled);

                // Y un título
                alertDialog.setTitle("Add Rating: ");

                // Tenemos que añadir el linealayout al diálogo
                alertDialog.setView(linearLayout);

                // Creamos un botón de aceptar
                alertDialog.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // Guardamos el valor actual de puntuación
                                float currentValue=rating.getRating();

                                // Cambiamos el rating de la mascota en cuestion
                                pet.setRating(String.valueOf(currentValue));

                                // Actualizamos también el TextView que lo muestra
                                petViewHolder.tvRating.setText(String.valueOf(currentValue));

                                // Cerramos el cuadro de diáloigo
                                dialog.dismiss();
                            }
                        });

                // Mostramos el diálogo
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }
}
