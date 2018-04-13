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

public class AddEmployee extends Activity {

    private EditText etMat, etNom, etPrenom;
    private Button btAdd;

    IRetro retro;
    Employe employeToAdd;

    ConfigRetrofit configRetro = new ConfigRetrofit();
    Retrofit retrofit = configRetro.getConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        etMat = (EditText) findViewById(R.id.matricule);
        etNom = (EditText) findViewById(R.id.Nom);
        etPrenom = (EditText) findViewById(R.id.prenom);
        btAdd = (Button) findViewById(R.id.add);


        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mat = etMat.getText().toString();
                String nom = etNom.getText().toString();
                String prenom = etPrenom.getText().toString();

                if(!mat.equals("") && !nom.equals("") && !prenom.equals(""))
                {

                    retro = retrofit.create(IRetro.class);
                    employeToAdd = new Employe(nom, mat, prenom);

                    Call<Employe> callVerif = retro.getEmployeeByMatricule(mat);
                    callVerif.enqueue(new Callback<Employe>() {
                        @Override
                        public void onResponse(Call<Employe> call, Response<Employe> response) {
                            if(response.body().getMat() != null)
                            {
                                Toast.makeText(AddEmployee.this, "L'employé existe déjà verifier le matricule !", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Call<Employe> callEmp = retro.addEmployee(employeToAdd);
                                callEmp.enqueue(new Callback<Employe>() {
                                    @Override
                                    public void onResponse(Call<Employe> call, Response<Employe> response) {
                                        if (response.body().getNom() != null) {
                                            Toast.makeText(AddEmployee.this, "Insertion de l'employé effectuée !", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(AddEmployee.this, Gestion.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Employe> call, Throwable t) {
                                        Toast.makeText(AddEmployee.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Employe> call, Throwable t) {

                        }
                    });



                }
                else
                {
                    Toast.makeText(AddEmployee.this, "Champs vides !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
