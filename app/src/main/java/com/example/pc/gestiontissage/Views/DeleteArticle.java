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

public class DeleteArticle extends Activity {

    private Button btDel;
    private EditText etCode;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.del_a);

        etCode = (EditText) findViewById(R.id.etCode);

        btDel = (Button) findViewById(R.id.btDel);

        btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = etCode.getText().toString();
                if(!code.equals(""))
                {
                    IRetro art = retrofit.create(IRetro.class);
                    Call<Article> call = art.deleteArticle(code);
                    call.enqueue(new Callback<Article>() {
                        @Override
                        public void onResponse(Call<Article> call, Response<Article> response) {
                            if(response.body().getCode() != null)
                            {
                                Toast.makeText(DeleteArticle.this, "Suppression effectuée !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DeleteArticle.this, Gestion.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(DeleteArticle.this, "Article introuvable !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Article> call, Throwable t) {
                            Toast.makeText(DeleteArticle.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(DeleteArticle.this, "Champ vide !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

