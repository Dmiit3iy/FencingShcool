package org.dmiit3iy.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.Training;
import org.dmiit3iy.util.Constants;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Query;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TrainingRepository {

    private ObjectMapper objectMapper;
    private TrainingService service;

    public TrainingRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL + "training/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper)).client(client).build();

        this.service = retrofit.create(TrainingService.class);
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

    public Training post(long idTrainer, long idApprentice,int numberGym, LocalDate date,LocalTime startTime) throws IOException {
        Response<ResponseResult<Training>> execute = this.service.post(idTrainer,idApprentice,numberGym,date,startTime).execute();
        return getData(execute);
    }

    public Training get(long id) throws IOException {
        Response<ResponseResult<Training>> execute = service.get(id).execute();
        return getData(execute);
    }

    public List<Training> getByIdTrainer(long id) throws IOException {
        Response<ResponseResult<List<Training>>> execute = service.getByTrainerId(id).execute();
        return getData(execute);
    }

    public List<LocalTime> getTime(long idTrainer,LocalDate date, int numberGym) throws IOException {
        Response<ResponseResult<List<LocalTime>>> execute = service.getTime(idTrainer,date,numberGym).execute();
        return getData(execute);
    }
    public Boolean getAnyFreeTime(long idTrainer,LocalDate date) throws IOException {
        Response<ResponseResult<Boolean>> execute = service.getAnyFeeTime(idTrainer,date).execute();
        return getData(execute);
    }

    public List<Training> getByIdApprentice(long id) throws IOException {
        Response<ResponseResult<List<Training>>> execute = service.getByApprenticeId(id).execute();
        return getData(execute);
    }

    public Training delete(long id) throws IOException {
        Response<ResponseResult<Training>> execute = service.delete(id).execute();
        return getData(execute);
    }
}
