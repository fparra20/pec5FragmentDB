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

public class PetRatingAdapter extends RecyclerView.Adapter<PetRatingAdapter.PetViewHolder> {

    ArrayList<Pet> pets;
    Activity activity;

    public PetRatingAdapter(ArrayList<Pet> pets, Activity activity) {
        this.pets = pets;
        this.activity = activity;
    }
    public static class PetViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPhoto;
        private TextView tvRating;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            // Iniciamos todos los item con los que vamos a trabajar
            imgPhoto = (ImageView)itemView.findViewById(R.id.imgPhoto);
            tvRating = (TextView)itemView.findViewById(R.id.tvRating);
        }
    }
    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pet_rating,parent,false);
        return new PetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder petViewHolder, int position) {
        Pet pet = pets.get(position);

        // En este caso no queremos que el botón del rating responda, de hecho que ni se muestre.
        petViewHolder.imgPhoto.setImageResource(pet.getPhoto());
        petViewHolder.tvRating.setText(String.valueOf(pet.getRating()));
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }
}
