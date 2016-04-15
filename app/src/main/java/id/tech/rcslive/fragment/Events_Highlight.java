package id.tech.rcslive.fragment;

/**
 * Created by RebelCreative-A1 on 21/03/2016.
 */

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
import id.tech.rcslive.util.ParameterCollections;
import id.tech.rcslive.util.PublicFunctions;
import retrofit.Call;
import retrofit.Response;

public class Events_Highlight extends Fragment {
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ASyncTask_GetAllEvent().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_rv, null);
        rv = (RecyclerView)view.findViewById(R.id.rv);
        layoutManager = new GridLayoutManager(getContext(),1);

//        List<Rowdata_EventHighlight> data = new ArrayList<>();
//        Rowdata_EventHighlight item = new Rowdata_EventHighlight();
//        item.setIdEvent("1");
//        item.setTvTgl("Mon, 1 April 2016");
//        item.setTvJudul("Kumpul Kangen Angkatan 2010");
//        item.setTvAlamat("Jl. Soekarno Hatta no. 45, Gedung Mahaka Square. Jakarta Timur");
//        item.setTvKategori("#Reuni");
//        item.setJoined("58 People Joined");
//        data.add(item);
//
//        Rowdata_EventHighlight item2 = new Rowdata_EventHighlight();
//        item2.setIdEvent("2");
//        item2.setTvTgl("Tue, 12 April 2016");
//        item2.setTvJudul("Latihan Futsal Bulanan");
//        item2.setTvAlamat("Futsal Celebrity, Kelapa Gading. Jakarta Timur");
//        item2.setTvKategori("#Sport");
//        item2.setJoined("20 People Joined");
//        data.add(item2);

        return view;
    }

    private class ASyncTask_GetAllEvent extends AsyncTask<Void,Void,Void> {
        String cCode="0";
        List<Rowdata_EventHighlight> data;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            data = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Rest_Adapter adapter = PublicFunctions.initRetrofit();

            Call<Pojo_EventHighlight> call = adapter.get_all_events_highlight(ParameterCollections.KIND_EVENT,
                    true, "3");

            try{
                Response<Pojo_EventHighlight> response_event = call.execute();
                if(response_event.isSuccess()){
                    if(response_event.body().getJsonCode().equals("1")){
                        if(response_event.body().getData() != null){
                            for(int i=0; i< response_event.body().getData().size(); i++){
                                Rowdata_EventHighlight item = new Rowdata_EventHighlight();
                                item.setIdEvent(response_event.body().getData().get(i).getId());
                                item.setTvTgl(response_event.body().getData().get(i).getDeadline());
                                item.setTvJudul(response_event.body().getData().get(i).getEventTitle());
                                item.setTvAlamat(response_event.body().getData().get(i).getEventLocation());
                                item.setTvKategori(response_event.body().getData().get(i).getCategoriesName());
                                item.setEventPhoto(response_event.body().getData().get(i).getEventPhoto());
                                item.setEventLat(response_event.body().getData().get(i).getEventLat());
                                item.setEventLon(response_event.body().getData().get(i).getEventLon());
                                item.setEventMinjoin(response_event.body().getData().get(i).getEventMinjoin());
                                item.setEventDescription(response_event.body().getData().get(i).getEventDescription());
                                item.setEventDocumentationid(response_event.body().getData().get(i).getEventDocumentationid());
                                item.setMemberName(response_event.body().getData().get(i).getMemberName());
                                item.setMemberPhone(response_event.body().getData().get(i).getMemberPhone());
                                item.setMemberPhoto(response_event.body().getData().get(i).getMemberPhoto());
                                item.setTotalJoin(response_event.body().getData().get(i).getTotalJoin());
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
                adapter = new RV_Adapter_Highlight(getContext(), data);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);
            }else{
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG).show();
            }
        }
    }


}
