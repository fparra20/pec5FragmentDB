package hfad.com.pec4mascotas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

public class SecondFragment extends Fragment {

    ArrayList<Pet> petsList;
    PetRatingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Creamos un linear layout para mostrar la pestaña
        LinearLayout layout = new LinearLayout(getContext());

        // Orientacion vertical
        layout.setOrientation(LinearLayout.VERTICAL);

        // Width y height match parent
        layout.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        // Creamos un objeto recyclerView
        RecyclerView mainRecycler = (RecyclerView)inflater.inflate(R.layout.fragment_tab, container, false);

        // Creamos un objeto CircularImageView, de la libreria que hemos añadido.
        // Funciona exactamente como un ImageView
        CircularImageView circularImageView = new CircularImageView(this.getContext());

        // Ponemos una imagen
        circularImageView.setImageResource(R.drawable.mountain_horned_dragon);

        // Controlamos que no queden espacios en blanco
        circularImageView.setAdjustViewBounds(true);

        // Ponemos un borde de 15
        circularImageView.setBorderWidth(15f);

        // Ponemos un color en el borde
        circularImageView.setBorderColor(R.color.colorPrimary);

        // Creamos parámetros para el circular image view, va a tener wrap content tanto en wifth como height
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Añadimos un margen de 100 a cada lado para que la imagen quede centrada
        lp.setMargins(100, 10, 100, 10);

        // Asociamos los parámetros al objeto imagen
        circularImageView.setLayoutParams(lp);


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

        // Cambiamos el orden aleatoriamente
        Collections.shuffle(petsList);

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


        // Creamos las propiedades para el recycler view
        GridLayoutManager layoutManager= new GridLayoutManager(getActivity(),3);

        // Creamos el adaptador
        adapter = new PetRatingAdapter(petsList,getActivity());

        // Rellenamos el recyclerView
        mainRecycler.setAdapter(adapter);

        // Asociamos el recyclerview con sus parámetros
        mainRecycler.setLayoutManager(layoutManager);

        // Añadimos al Linear layout la imagen
        layout.addView(circularImageView);

        // Añadimos al Linear layout el recyclerview
        layout.addView(mainRecycler);

        // Devolvemos el linear layout para que se muestre
        return layout;
    }
}