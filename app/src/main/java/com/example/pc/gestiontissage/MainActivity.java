package com.example.pc.gestiontissage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.gestiontissage.Models.Article;
import com.example.pc.gestiontissage.Models.Employe;
import com.example.pc.gestiontissage.Retrofit.ConfigRetrofit;
import com.example.pc.gestiontissage.Retrofit.IRetro;
import com.example.pc.gestiontissage.Views.Gestion;
import com.example.pc.gestiontissage.Views.ModifyArticle;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button ges;
    private EditText etMat;
    private EditText etArticle;
    private EditText etMetrage;
    private EditText etHtrv;
    private EditText etArret;


    public double productionEmp;


    public int vts;
    public int mg;


    private TextView tvMg;
    private TextView tvProd;
    private TextView hiddenTvProd;
    private TextView hiddenTvVts;
    
    IRetro retro;


    private Button btCalc;



    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ges= (Button) findViewById(R.id.btGes);
        etMat= (EditText) findViewById(R.id.EtMat);
        etArret= (EditText) findViewById(R.id.EtArr);
        etArticle= (EditText) findViewById(R.id.EtArt);
        etMetrage= (EditText) findViewById(R.id.EtMet);
        etHtrv= (EditText) findViewById(R.id.etHtv);

        tvMg= (TextView) findViewById(R.id.TvMg);
        tvProd= (TextView) findViewById(R.id.TvProd);
        hiddenTvProd= (TextView) findViewById(R.id.hiddenTvProd);
        hiddenTvVts= (TextView) findViewById(R.id.hiddenTvVts);

        hiddenTvProd.setVisibility(View.INVISIBLE);
        hiddenTvVts.setVisibility(View.INVISIBLE);

        ges.setOnClickListener(this);

        btCalc = (Button) findViewById(R.id.btCalc);

        btCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String article = etArticle.getText().toString();
                final String mat = etMat.getText().toString();
                String arretField = etArret.getText().toString();
                String htrvField = etHtrv.getText().toString();
                String metrageField = etMetrage.getText().toString();
                
                if(!article.equals("") && !mat.toString().equals("") && !arretField.equals("") && !htrvField.equals("") && !metrageField.equals("")) {

                    final int arret = Integer.parseInt(arretField);
                    final int htrv = Integer.parseInt(htrvField);
                    final int metrage = Integer.parseInt(metrageField);


                    retro = retrofit.create(IRetro.class);
                    Call<Employe> callEmp = retro.getEmployeeByMatricule(mat);
                    Call<Article> callArticle = retro.getArticleByCode(article);
                    callEmp.enqueue(new Callback<Employe>() {
                        @Override
                        public void onResponse(Call<Employe> call, Response<Employe> response) {
                            if (response.body().getMat() != null) {
                                productionEmp = response.body().getProduction();

                                hiddenTvProd.setText(String.valueOf(productionEmp));

                                Toast.makeText(MainActivity.this, hiddenTvProd.getText(), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Employé inexistant, vérifier le matricule !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Employe> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

                    callArticle.enqueue(new Callback<Article>() {
                        @Override
                        public void onResponse(Call<Article> call, Response<Article> response) {
                            if (response.body().getCode() != null) {
                                vts = response.body().getVts();

                                hiddenTvVts.setText(String.valueOf(vts));

                                int minutesG = (metrage * Integer.parseInt(hiddenTvVts.getText().toString())) / 100;
                                double prod = 0;
                                try {
                                    prod = Double.parseDouble(hiddenTvProd.getText().toString());
                                }
                                catch(Exception e)
                                {
                                    Toast.makeText(MainActivity.this, "La production n'a pas pu être récupérée, veuillez réessayer", Toast.LENGTH_SHORT).show();
                                }

                                double prime = prod + ((((minutesG / (htrv - arret)) * 100) - 100));

                                tvMg.setText(String.valueOf(minutesG));
                                tvProd.setText(String.valueOf(prime));

                                Call<Employe> callUpdateProd = retro.updateProd(mat, prime);
                                callUpdateProd.enqueue(new Callback<Employe>() {
                                    @Override
                                    public void onResponse(Call<Employe> call, Response<Employe> response) {
                                        if(response.body() != null)
                                        {
                                            Toast.makeText(MainActivity.this, "Mise à jour de la production de l'employé effectué avec succès !", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Employe> call, Throwable t) {
                                        Toast.makeText(MainActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Article inexsitant, vérifier le code !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Article> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

                   
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Champs vides !", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent( MainActivity.this,Gestion.class);
        startActivity(i);
    }
}
