package com.mayrelisledesma.animalkingdomapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner tipoSpinner;
    private Spinner animalSpinner;
    private ImageView animalImageView;
    private TextView descTextView;
    private MediaPlayer mediaPlayer;

    private List<Animal> animales = new ArrayList<>();
    private List<String> tipos = new ArrayList<>();
    private List<String> nombresAnimales = new ArrayList<>();
    private ArrayAdapter<String> animalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tipoSpinner = findViewById(R.id.tipoSpinner);
        animalSpinner = findViewById(R.id.animalSpinner);
        animalImageView = findViewById(R.id.animalImageView);
        descTextView = findViewById(R.id.descTextView);

        cargarDatosAnimales();
        configurarTipoSpinner();
    }

    private void cargarDatosAnimales() {
        // Agregar animales a la lista
        animales.add(new Animal("001", "HERBÍVOROS", "Comen diversos tipos de plantas", R.drawable.herbivoro_img, R.raw.herbivoro_sonido));
        animales.add(new Animal("002", "CARNÍVOROS", "Solo su dieta la componen proteínas de otros seres vivos", R.drawable.carnivoro_img, R.raw.carnivoro_sonido));

        for (Animal animal : animales) {
            if (!tipos.contains(animal.getTipoAnim())) {
                tipos.add(animal.getTipoAnim());
            }
        }
    }

    private void configurarTipoSpinner() {
        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipos);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoSpinner.setAdapter(tipoAdapter);

        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String tipoSeleccionado = tipos.get(position);
                actualizarAnimalesPorTipo(tipoSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                nombresAnimales.clear();
                if (animalAdapter != null) {
                    animalAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void actualizarAnimalesPorTipo(String tipo) {
        nombresAnimales.clear();
        for (Animal animal : animales) {
            if (animal.getTipoAnim().equals(tipo)) {
                nombresAnimales.add(animal.getCodAnim());
            }
        }

        animalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresAnimales);
        animalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animalSpinner.setAdapter(animalAdapter);

        animalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String codSeleccionado = nombresAnimales.get(position);
                mostrarDetallesAnimal(codSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No acción
            }
        });
    }

    private void mostrarDetallesAnimal(String codAnimal) {
        for (Animal animal : animales) {
            if (animal.getCodAnim().equals(codAnimal)) {
                descTextView.setText(animal.getDescAnim());
                animalImageView.setImageResource(animal.getFotoResid());

                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(this, animal.getSonidoResid());
                mediaPlayer.start();
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
