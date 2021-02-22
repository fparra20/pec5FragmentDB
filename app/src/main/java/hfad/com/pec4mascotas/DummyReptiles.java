package hfad.com.pec4mascotas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DummyReptiles extends AppCompatActivity {

    private RecyclerView listaPets;
    ArrayList<Pet> dummyPets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_reptiles);

        //Añadimos la toolbar a la activity
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Accedemos a la app bar y permitimos el boton Up
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Recogemos el arraylist de mascotas de la actividad principal
        Intent i = getIntent();
        ArrayList<Pet> pets = (ArrayList<Pet>) i.getSerializableExtra("petlist");

        // Cambiamos el orden aleatoriamente
        Collections.shuffle(pets);

        // Creamos un nuevo array list que contenga las 5 primeras mascotas ya ordenadas
        dummyPets = new ArrayList<Pet>();

        // Rellenamos el arraylist
        for(int j=0; j<5; j++)
            dummyPets.add(pets.get(j));


        // Obtenemos el recyclerview y le damos propiedades
        listaPets = (RecyclerView)findViewById(R.id.rvPets_dummy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listaPets.setLayoutManager(linearLayoutManager);

        // Creamos un nuevo adaptador
        PetAdapter adapter = new PetAdapter(dummyPets,this);

        // Usamos el nuevo adaptador para rellenar la lista
        listaPets.setAdapter(adapter);

    }
}