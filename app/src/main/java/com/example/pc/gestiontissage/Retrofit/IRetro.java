package com.example.pc.gestiontissage.Retrofit;

import com.example.pc.gestiontissage.Models.Arret;
import com.example.pc.gestiontissage.Models.Article;
import com.example.pc.gestiontissage.Models.Employe;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by pc on 12/04/2018.
 */

public interface IRetro {

    @GET("employe/getEmployeeByMatricule/{matricule}")
    Call<Employe> getEmployeeByMatricule(@Path("matricule") String matricule);


    @POST("employe/updateEmploye/{matricule}")
    Call<Employe> updateEmploye(@Path("matricule") String matricule, @Body Employe employe);


    @POST("employe/addEmployee")
    Call<Employe> addEmployee(@Body Employe employe);

    @GET("employe/deleteEmployee/{matricule}")
    Call<String> deleteEmployee(@Path("matricule") String matricule);

    @GET("article/getArticleByCode/{code}")
    Call<Article> getArticleByCode(@Path("code") String code);

    @POST("article/updateArticle/{code}")
    Call<Article> updateArticle(@Path("code") String code, @Body Article article);

    @GET("employe/updateProd/{matricule}/{production}")
    Call<Employe> updateProd(@Path("matricule") String matricule, @Path("production") double production);


    @POST("article/addArticle")
    Call<Article> addArticle(@Body Article article);

    @GET("article/deleteArticle/{code}")
    Call<Article> deleteArticle(@Path("code") String code);

    @GET("arret/getArretByCode/{code}")
    Call<Arret> getArretByCode(@Path("code") String code);
}
