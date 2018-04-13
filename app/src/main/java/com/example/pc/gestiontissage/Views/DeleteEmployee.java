package com.example.pc.gestiontissage.Views;

import android.app.Activity;
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

public class DeleteEmployee extends Activity {


    private EditText etMat;
    private Button btDel;

    ConfigRetrofit configRetrofit = new ConfigRetrofit();
    Retrofit retrofit = configRetrofit.getConfig();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.del);

        etMat = (EditText) findViewById(R.id.etMat);

        btDel = (Button) findViewById(R.id.btDel);

        btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mat = etMat.getText().toString();
                if(!mat.equals(""))
                {
                    IRetro emp = retrofit.create(IRetro.class);
                    Call<String> call = emp.deleteEmployee(mat);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body() != null)
                            {
                                Toast.makeText(DeleteEmployee.this, "Suppression effectuée !", Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent(supprimerE.this, gestion.class);
                                //startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(DeleteEmployee.this, "Employé introuvable !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(DeleteEmployee.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(DeleteEmployee.this, "Champ vide !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
