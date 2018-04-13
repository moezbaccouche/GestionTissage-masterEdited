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

public class SearchEmployee extends Activity {

    EditText etMat;
    private Button btSearch;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        etMat=(EditText)findViewById(R.id.mat);

        btSearch = (Button) findViewById(R.id.btSear);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mat = etMat.getText().toString();
                if(!mat.equals(""))
                {
                    IRetro emp = retrofit.create(IRetro.class);
                    Call<Employe> call = emp.getEmployeeByMatricule(mat);
                    call.enqueue(new Callback<Employe>() {
                        @Override
                        public void onResponse(Call<Employe> call, Response<Employe> response) {
                            if(response.body().getMat() != null)
                            {
                                Toast.makeText(SearchEmployee.this, "Employé avec matricule " + response.body().getMat() + " trouvé !", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(SearchEmployee.this, ModifyEmployee.class);

                                intent.putExtra("nom", response.body().getNom());
                                intent.putExtra("prenom", response.body().getPrenom());
                                intent.putExtra("mat", response.body().getMat());


                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(SearchEmployee.this, "Employé indisponible vérifier le matricule !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Employe> call, Throwable t) {
                            Toast.makeText(SearchEmployee.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(SearchEmployee.this, "Champ vide !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
