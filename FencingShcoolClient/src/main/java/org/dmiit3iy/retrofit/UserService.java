package org.dmiit3iy.retrofit;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserService {
    @GET(".")
    Call<ResponseResult<User>> getByLoginAndPassword(@Query("login") String login, @Query("password") String password);

    @GET("{id}")
    Call<ResponseResult<User>> get(@Path("id") long id);

    @DELETE("{id}")
    Call<ResponseResult<User>> delete(@Path("id") long id);

    @POST(".")
    Call<ResponseResult<User>> post (@Body User user);
}


