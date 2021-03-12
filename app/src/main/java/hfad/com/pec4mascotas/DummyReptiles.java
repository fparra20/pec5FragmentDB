package hfad.com.pec4mascotas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import hfad.com.pec4mascotas.database.PetsDataBaseHelper;

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


        // Creamos el cursor para traer los datos de la BD
        SQLiteOpenHelper PetsDataBaseHelper = new PetsDataBaseHelper(this);

        // Extrae la base de datos para trabajar con ella
        SQLiteDatabase db = PetsDataBaseHelper.getReadableDatabase();

        // Crea una consulta en la tabla PET con las 4 columnas deseadas
        // Además ponemos que se ordenen de forma descendente según el numero de likes
        // Y también que el límite de resultados sea 5
        Cursor cursor = db.query("PET",
                new String[]{"_id", "IMAGE_RESOURCE_ID", "NAME", "RATING"}, null,
                null, null, null, "RATING DESC", "5");

        //Creamos un arraylist de tipo Pet vacío
        dummyPets = new ArrayList<Pet>();

        // Comprobamos que el cursor no está vacío
        if (cursor.moveToFirst()) {
            // Este código se repite mientras cursor siga teniendo datos
            do {
                // Para cada dato del cursor introducimos un nuevo valor en el ArrayList
                dummyPets.add(new Pet(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getInt(3)));
            } while (cursor.moveToNext());

            // Si no tiene datos o no le quedan, cierra el cursor y las llamadas a la base de datos.
        } else {
            cursor.close();
            db.close();
        }

        // Obtenemos el recyclerview y le damos propiedades
        listaPets = (RecyclerView)findViewById(R.id.rvPets_dummy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listaPets.setLayoutManager(linearLayoutManager);

        // Creamos un nuevo adaptador
        PetDummyAdapter adapter = new PetDummyAdapter(dummyPets,this);

        // Usamos el nuevo adaptador para rellenar la lista
        listaPets.setAdapter(adapter);

    }
}