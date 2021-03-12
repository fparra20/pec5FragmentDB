package hfad.com.pec4mascotas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asignamos la toolbar creada como app bar de la activity
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creamos el adaptador para el viewpager
        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());

        // Creamos el viewpager
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        // Vinculamos el viewpager con el adaptador
        pager.setAdapter(pagerAdapter);

        // Creamos un tablayout
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
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

                // Inicia la actividad con los parametros indicados
                startActivity(intent);
                return true;

            // Si clicamos en Contact nos lleva a un formulario para enviar emails
            case R.id.contact:
                Intent intent_contact = new Intent(this, ContactActivity.class);
                startActivity(intent_contact);
                return true;

            // Si clicamos en About nos lleva a una página donde se ven datos del creador
            case R.id.about:
                Intent intent_about = new Intent(this, AboutActivity.class);
                startActivity(intent_about);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Crearemos las dos pestañas y y que cada una muestre un contenido fragment
    private class SectionsPagerAdapter extends FragmentPagerAdapter{

        public SectionsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new TopFragment();
                case 1:
                    return new SecondFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Home";
                case 1:
                    return "Favorite";
            }
            return null;
        }
    }

}