package id.tech.rcslive.util;

/**
 * Created by RebelCreative-A1 on 21/03/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import id.tech.rcslive.models.*;
public interface RestAdapter {

    @POST("")
    Call<Pojo_ResponseLogin> loginCall(
            @Query("username") String username,
            @Query("password") String password);
}