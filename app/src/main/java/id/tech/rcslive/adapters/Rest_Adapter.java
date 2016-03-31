package id.tech.rcslive.adapters;

/**
 * Created by macbook on 3/31/16.
 */
import id.tech.rcslive.models.Pojo_EventHighlight;
import id.tech.rcslive.models.Pojo_ResponseLogin;
import id.tech.rcslive.util.ParameterCollections;
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

public interface Rest_Adapter {

    @GET("getApi.php?")
    Call<Pojo_ResponseLogin> login(
            @Query("kind") String kind,
            @Query("email") String email,
            @Query("password") String password
    );

    @GET("getApi.php?")
    Call<Pojo_EventHighlight> get_all_events(
            @Query("kind") String kind
    );

    @GET("getApi.php?")
    Call<Pojo_EventHighlight> get_all_events_joined(
            @Query("kind") String kind,
            @Query("id") String id
    );
}
