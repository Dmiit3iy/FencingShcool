package org.dmiit3iy.retrofit;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.Training;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TrainingService {
    @POST(".")
    Call<ResponseResult<Training>> post(@Query("idTrainer") long idTrainer, @Query("idApprentice") long idApprentice,
                                        @Query("numberGym") int numberGym, @Query("date") LocalDate date,
                                        @Query("startTime") LocalTime startTime);

    @GET("{id}")
    Call<ResponseResult<Training>> get(@Path("id") long id);

    @GET("trainer/{id}")
    Call<ResponseResult<List<Training>>> getByTrainerId(@Path("id") long id);

    @GET("apprentice/{id}")
    Call<ResponseResult<List<Training>>> getByApprenticeId(@Path("id") long id);

    @DELETE("{id}")
    Call<ResponseResult<Training>> delete(@Path("id") long id);

    @GET(".")
    Call<ResponseResult<List<LocalTime>>> getTime(@Query("idTrainer") long idTrainer,@Query("date") LocalDate date,
                                              @Query("numberGym") int numberGym);

    @GET("freetime")
    Call<ResponseResult<Boolean>> getAnyFeeTime(@Query("idTrainer") long idTrainer,@Query("date") LocalDate date);
}
