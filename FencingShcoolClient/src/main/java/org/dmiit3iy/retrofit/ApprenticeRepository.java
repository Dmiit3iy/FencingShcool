package org.dmiit3iy.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.util.Constants;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ApprenticeRepository {
    private ObjectMapper objectMapper;
    private ApprenticeService service;

    public ApprenticeRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL + "apprentice/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper)).client(client).build();

        this.service = retrofit.create(ApprenticeService.class);
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

    public Apprentice post(Apprentice apprentice) throws IOException {
        Response<ResponseResult<Apprentice>> execute = this.service.post(apprentice).execute();
        return getData(execute);
    }


    public Apprentice getById(long id) throws IOException {
        Response<ResponseResult<Apprentice>> execute = service.getById(id).execute();
        return getData(execute);
    }

    public List<Apprentice> get() throws IOException {
        Response<ResponseResult<List<Apprentice>>> execute = service.getAll().execute();
        return getData(execute);
    }

    public Apprentice update(Apprentice apprentice) throws IOException {
        Response<ResponseResult<Apprentice>> execute = service.put(apprentice).execute();
        return getData(execute);
    }
    public Apprentice delete(long id) throws IOException {
        Response<ResponseResult<Apprentice>> execute = service.delete(id).execute();
        return getData(execute);
    }
}
