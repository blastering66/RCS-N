package id.tech.rcslive.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

    public static boolean getLocationNow(Context context, SharedPreferences sh) {
        GPSTracker gps = new GPSTracker(context);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

//            now_latitude = String.valueOf(latitude);
//            now_longitude = String.valueOf(longitude);

            Log.e("Longitude", String.valueOf(longitude));
            Log.e("Latitude", String.valueOf(latitude));

            sh.edit()
                    .putString(ParameterCollections.TAG_LONGITUDE_NOW,
                            String.valueOf(longitude)).commit();
            sh.edit()
                    .putString(ParameterCollections.TAG_LATITUDE_NOW,
                            String.valueOf(latitude)).commit();

            return true;

        } else {
            return false;
        }
    }
}
