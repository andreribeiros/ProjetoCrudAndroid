package br.com.adsddm.cadastromedico;

import java.util.List;

import br.com.adsddm.cadastromedico.DAO.Medico;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitMedico {

    @POST("/api/medico")
    Call<Void> salvarMedico(@Body Medico medico);

    @GET("/api/medicos")
    Call<List<Medico>> listaMedicos();

    @PUT("/api/medicos/")
    Call<Void> atualizarMedico(@Path("id") String id, @Body Medico Medico);

    @DELETE("/api/medico/")
    Call<Void> deletarMedico(@Body Medico medico);

    @GET("/api/medico/{id}")
    Call<Medico> listaMedicoUnico(@Path("id") Long id);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://apirest-medicos.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
