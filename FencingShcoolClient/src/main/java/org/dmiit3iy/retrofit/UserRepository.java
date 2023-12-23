package org.dmiit3iy.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.User;
import org.dmiit3iy.util.Constants;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class UserRepository {
    private ObjectMapper objectMapper;
    private UserService service;

    public UserRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL + "user/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper)).client(client).build();

        this.service = retrofit.create(UserService.class);
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

    public User post(User user) throws IOException {
        Response<ResponseResult<User>> execute = this.service.post(user).execute();
        return getData(execute);
    }



    public User get(String login, String password) throws IOException {
        Response<ResponseResult<User>> execute = service.getByLoginAndPassword(login, password).execute();
        return getData(execute);
    }

    public User get(long id) throws IOException {
        Response<ResponseResult<User>> execute = service.get(id).execute();
        return getData(execute);
    }

    public User delete(long id) throws IOException {
        Response<ResponseResult<User>> execute = service.delete(id).execute();
        return getData(execute);
    }
}
