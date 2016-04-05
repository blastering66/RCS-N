package id.tech.rcslive.fragment;

/**
 * Created by RebelCreative-A1 on 21/03/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.tech.rcslive.activity.R;
import id.tech.rcslive.models.*;
import id.tech.rcslive.adapters.*;
import id.tech.rcslive.activity.R;
import id.tech.rcslive.util.ParameterCollections;
import id.tech.rcslive.util.PublicFunctions;
import retrofit.Call;
import retrofit.Response;

public class Events_Joined extends Fragment{
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_rv, null);
        rv = (RecyclerView)view.findViewById(R.id.rv);


        new ASyncTask_GetAllEvent_Joined().execute();
        return view;
    }

    private class ASyncTask_GetAllEvent_Joined extends AsyncTask<Void,Void,Void> {
        String cCode="0";
        List<Rowdata_EventJoined> data;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            data = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Rest_Adapter adapter = PublicFunctions.initRetrofit();

            SharedPreferences spf = getActivity().getSharedPreferences(ParameterCollections.SPF_NAME, Context.MODE_PRIVATE);
            Call<Pojo_EventHighlight> call = adapter.get_all_events_joined(ParameterCollections.KIND_EVENT,
                    spf.getString(ParameterCollections.SPF_USER_ID, ""));

            try{
                Response<Pojo_EventHighlight> response_event = call.execute();
                if(response_event.isSuccess()){
                    if(response_event.body().getJsonCode().equals("1")){
                        if(response_event.body().getData() != null){
                            for(int i=0; i< response_event.body().getData().size(); i++){
                                Rowdata_EventJoined item = new Rowdata_EventJoined();
                                item.setIdEvent(response_event.body().getData().get(i).getId());
                                item.setTvTgl(response_event.body().getData().get(i).getDeadline());
                                item.setTvJudul(response_event.body().getData().get(i).getEventTitle());
                                item.setTvAlamat(response_event.body().getData().get(i).getEventLocation());
                                item.setTvKategori(response_event.body().getData().get(i).getCategoriesName());
                                item.setEventPhoto(response_event.body().getData().get(i).getEventPhoto());
                                item.setJoined("0 Joined");
                                data.add(item);
                            }
                        }
                    }
                    cCode="1";
                }else{
                    cCode="0";
                }
            }catch (IOException e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(cCode.equals("1")){
                layoutManager = new GridLayoutManager(getContext(),1);
                adapter = new RV_Adapter_Joined(getActivity(), data);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);
            }else{
//                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG).show();
            }
        }
    }
}
