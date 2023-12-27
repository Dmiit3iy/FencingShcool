package org.dmiit3iy.retrofit;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.TrainerSchedule;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalTime;

public interface ScheduleService {

    @POST("{id}")
    Call<ResponseResult<TrainerSchedule>> post(@Path("id") long id, @Body TrainerSchedule trainerSchedule);

    @POST("{id}")
    Call<ResponseResult<TrainerSchedule>> post(@Path("id") long id, @Query("dayWeek") String dayWeek,
                                              @Query("start") LocalTime start, @Query("end") LocalTime end);

    @GET("{id}")
    Call<ResponseResult<TrainerSchedule>> get(@Path("id") long id);

    @PUT("/{id}/update")
    Call<ResponseResult<TrainerSchedule>> put(@Path("id") long id, @Query("dayWeek") String dayWeek,
                                              @Query("start") LocalTime start, @Query("end") LocalTime end);
    @DELETE("{id}")
    Call<ResponseResult<TrainerSchedule>> delete(@Path("id") long id, @Query("day") String day);

}



