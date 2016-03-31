package id.tech.rcslive.util;

import id.tech.rcslive.adapters.Rest_Adapter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by macbook on 3/31/16.
 */
public class PublicFunctions {

    public static Rest_Adapter initRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ParameterCollections.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Rest_Adapter adapter = retrofit.create(Rest_Adapter.class);
        return adapter;
    }
}
