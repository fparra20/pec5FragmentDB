package hfad.com.pec4mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Pet> pets;
    private RecyclerView listaPets;
    PetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListaPets();

        //Asignamos la toolbar creada como app bar de la activity
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Iniciamos la variable RecyclerView que la conecta con el layout
        listaPets = (RecyclerView)findViewById(R.id.rvPets);

        // Creamos las propiedades para el recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listaPets.setLayoutManager(linearLayoutManager);

        // Creamos el adaptador para crear la recyclerview con los 10 animales
        adapter = new PetAdapter(pets,this);

        // Rellenamos el recyclerView
        listaPets.setAdapter(adapter);

    }

    // Método que se llama para crear el menú en la toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Método que indica lo que pasa cuando se clica una opción del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Dependiendo del elemento clicado
        switch (item.getItemId()){

            // Si clicamos action_dummy_reptiles (el icono favorito)
            case R.id.action_dummy_reptiles:

                // Crea un nuevo intent  hacia la clase DummyReptiles
                Intent intent = new Intent(this, DummyReptiles.class);

                // Manda a la nueva activity el arraylist de mascotas
                intent.putExtra("petlist", pets);

                // Inicia la actividad con los parametros indicados
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Rellenamos el arraylist con 10 reptiles por defecto
    public void initListaPets() {
        pets = new ArrayList<Pet>();
        pets.add(new Pet(R.drawable.camaleon,"Chameleon","3.5"));
        pets.add(new Pet(R.drawable.mountain_horned_dragon,"Mountain Horned Dragon","4.0"));
        pets.add(new Pet(R.drawable.russian_turtle,"Russian turtle","5.0"));
        pets.add(new Pet(R.drawable.fire_skink,"Fire Skink","3.0"));
        pets.add(new Pet(R.drawable.red_eared_slider_turtle,"Red Eared Turtle","3.0"));
        pets.add(new Pet(R.drawable.baby_bearded_dragon,"Baby Bearded Dragon","5.0"));
        pets.add(new Pet(R.drawable.gecko,"Common Gecko","5.0"));
        pets.add(new Pet(R.drawable.ball_phyton,"Ball Phyton","4.0"));
        pets.add(new Pet(R.drawable.anolis_carolinensis,"Anolis Carolinensis","5.0"));
        pets.add(new Pet(R.drawable.leopard_gecko,"Leopard Gecko","2.0"));
    }

}