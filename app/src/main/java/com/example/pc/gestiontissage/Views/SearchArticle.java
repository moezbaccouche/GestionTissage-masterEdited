package com.example.pc.gestiontissage.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.gestiontissage.Models.Article;
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

public class SearchArticle extends Activity {


    private EditText etMat;
    private Button btSearch;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_a);

        etMat = (EditText) findViewById(R.id.etCode);

        btSearch = (Button) findViewById(R.id.btSear);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = etMat.getText().toString();

                if(!code.equals(""))
                {
                    IRetro art = retrofit.create(IRetro.class);
                    Call<Article> call = art.getArticleByCode(code);
                    call.enqueue(new Callback<Article>() {
                        @Override
                        public void onResponse(Call<Article> call, Response<Article> response) {
                            if(response.body().getCode() != null)
                            {
                                Toast.makeText(SearchArticle.this, "Article avec le code : " + response.body().getCode() + " trouvé", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SearchArticle.this, ModifyArticle.class);


                                intent.putExtra("code", response.body().getCode());
                                intent.putExtra("vts", response.body().getVts());

                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(SearchArticle.this, "Article indisponible vérifier le code !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Article> call, Throwable t) {
                            Toast.makeText(SearchArticle.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(SearchArticle.this, "champ vide !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
