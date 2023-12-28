package org.dmiit3iy.retrofit;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.Trainer;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApprenticeService {
    @POST(".")
    Call<ResponseResult<Apprentice>> post(@Body Apprentice apprentice);

    @GET(".")
    Call<ResponseResult<List<Apprentice>>> getAll();

    @GET("/{id}")
    Call<ResponseResult<Apprentice>> getById(@Path("id") long id);

    @PUT(".")
    Call<ResponseResult<Apprentice>> put(@Body Apprentice apprentice);

    @DELETE("{id}")
    Call<ResponseResult<Apprentice>> delete(@Path("id") long id);
}
