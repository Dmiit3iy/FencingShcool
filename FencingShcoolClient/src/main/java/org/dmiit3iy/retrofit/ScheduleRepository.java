package org.dmiit3iy.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.util.Constants;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import java.io.IOException;
import java.time.LocalTime;


public class ScheduleRepository {
    private ObjectMapper objectMapper;
    private ScheduleService service;

    public ScheduleRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL + "schedule/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper)).client(client).build();

        this.service = retrofit.create(ScheduleService.class);
    }

    private <T> T getData(Response<ResponseResult<T>> execute) throws IOException {
        if (execute.code() != 200) {
            String string = execute.errorBody().string();
            System.out.println(string);
            String message = objectMapper.readValue(string,
                    new TypeReference<ResponseResult<T>>() {
                    }).getMessage();
            System.out.println(message);
            throw new IllegalArgumentException(message);
        }
        return execute.body().getData();
    }

    public TrainerSchedule post(long id, TrainerSchedule trainerSchedule) throws IOException {
        Response<ResponseResult<TrainerSchedule>> execute = this.service.post(id, trainerSchedule).execute();
        return getData(execute);
    }
    public TrainerSchedule post(long id, String dayWeek, LocalTime start, LocalTime end) throws IOException {
        Response<ResponseResult<TrainerSchedule>> execute = this.service.post(id, dayWeek, start, end).execute();
        return getData(execute);
    }


    public TrainerSchedule get(long id) throws IOException {
        Response<ResponseResult<TrainerSchedule>> execute = service.get(id).execute();
        return getData(execute);
    }

    public TrainerSchedule put(long id, String dayWeek, LocalTime start, LocalTime end) throws IOException {
        Response<ResponseResult<TrainerSchedule>> execute = service.put(id, dayWeek, start, end).execute();
        return getData(execute);
    }

    public TrainerSchedule delete(long id, String day) throws IOException {
        Response<ResponseResult<TrainerSchedule>> execute = service.delete(id, day).execute();
        return getData(execute);
    }
}
