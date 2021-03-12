package hfad.com.pec4mascotas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Collections;

import hfad.com.pec4mascotas.database.PetsDataBaseHelper;

public class TopFragment extends Fragment {

    ArrayList<Pet> petsList;
    PetAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Creamos un objeto recyclerView
        RecyclerView mainRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_tab, container, false);

        // Creamos el cursor para traer los datos de la BD
        SQLiteOpenHelper PetsDataBaseHelper = new PetsDataBaseHelper(this.getContext());

        // Extrae la base de datos para trabajar con ella
        SQLiteDatabase db = PetsDataBaseHelper.getReadableDatabase();

        // Crea una consulta en la tabla PET con las 4 columnas deseadas
        Cursor cursor = db.query("PET",
                new String[]{"_id", "IMAGE_RESOURCE_ID", "NAME", "RATING"}, null,
                null, null, null, null);

        //Creamos un arraylist de tipo Pet vacío
        petsList = new ArrayList<Pet>();

        // Comprobamos que el cursor no está vacío
        if (cursor.moveToFirst()) {

            // Este código se repite mientras cursor siga teniendo datos
            do {
                // Para cada dato del cursor introducimos un nuevo valor en el ArrayList
                petsList.add(new Pet(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getInt(3)));
            } while (cursor.moveToNext());

            // Si no tiene datos o no le quedan, cierra el cursor y las llamadas a la base de datos.
        } else {
            cursor.close();
            db.close();
        }

        // Creamos el adaptador y le pasamos la lista de pets
        adapter = new PetAdapter(petsList, getActivity());

            // Creamos las propiedades para el recycler view, para que solo muestre 1 columna
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);

            // Rellenamos el recyclerView
            mainRecycler.setAdapter(adapter);

            // Asociamos el recyclerview con sus parámetros
            mainRecycler.setLayoutManager(layoutManager);

            // Devolvemos el linear layout para que se muestre
            return mainRecycler;
    }
}