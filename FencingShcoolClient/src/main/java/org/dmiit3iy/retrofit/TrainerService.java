package org.dmiit3iy.retrofit;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.Trainer;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface TrainerService {

    @POST(".")
    Call<ResponseResult<Trainer>> post(@Body Trainer trainer);

    @GET(".")
    Call<ResponseResult<List<Trainer>>> getAll();

    @GET("/{id}")
    Call<ResponseResult<Trainer>> getById(@Path("id") long id);

    @PUT(".")
    Call<ResponseResult<Trainer>> put(@Body Trainer trainer);

    @DELETE("{id}")
    Call<ResponseResult<Trainer>> delete(@Path("id") long id);
}
