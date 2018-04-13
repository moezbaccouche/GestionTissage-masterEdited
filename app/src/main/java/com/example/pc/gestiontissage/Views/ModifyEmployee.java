package com.example.pc.gestiontissage.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.pc.gestiontissage.Models.Employe;
import com.example.pc.gestiontissage.R;
import com.example.pc.gestiontissage.Retrofit.ConfigRetrofit;
import com.example.pc.gestiontissage.Retrofit.IRetro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by pc on 12/04/2018.
 */

public class ModifyEmployee extends Activity {
    private EditText etMat, etNom, etPrenom;
    private Button btAdd;


    ConfigRetrofit configRetrofit = new ConfigRetrofit();
    Retrofit retrofit = configRetrofit.getConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_e);
        etMat = (EditText) findViewById(R.id.matricule);
        etNom = (EditText) findViewById(R.id.Nom);
        etPrenom = (EditText) findViewById(R.id.prenom);
        btAdd = (Button) findViewById(R.id.add);

        String nom = getIntent().getStringExtra("nom");
        String prenom = getIntent().getStringExtra("prenom");
        String mat = getIntent().getStringExtra("mat");

        etMat.setText(mat);
        etNom.setText(nom);
        etPrenom.setText(prenom);

        etMat.setEnabled(false);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mat = etMat.getText().toString();
                String nom = etNom.getText().toString();
                String prenom = etPrenom.getText().toString();

                if(!mat.equals("") && !nom.equals("") && !prenom.equals(""))
                {

                    IRetro employe = retrofit.create(IRetro.class);
                    Employe employeToAdd = new Employe(nom, prenom);
                    Call<Employe> call = employe.updateEmploye(mat ,employeToAdd);
                    call.enqueue(new Callback<Employe>() {
                        @Override
                        public void onResponse(Call<Employe> call, Response<Employe> response) {
                            if (response.body().getNom() != null) {
                                Toast.makeText(ModifyEmployee.this, "Mise à jour de l'employé effectuée !", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ModifyEmployee.this, Gestion.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<Employe> call, Throwable t) {
                            Toast.makeText(ModifyEmployee.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(ModifyEmployee.this, "Champs vides !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}