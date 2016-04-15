package id.tech.rcslive.fragment;

/**
 * Created by RebelCreative-A1 on 21/03/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

//import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;
//import dev.dworks.libs.astickyheader.ui.PinnedSectionListView;
import id.tech.rcslive.activity.R;
import id.tech.rcslive.adapters.CustomAdapter_Calendar;
import id.tech.rcslive.adapters.RV_Adapter_Calendar;
import id.tech.rcslive.adapters.RV_Adapter_Joined;
import id.tech.rcslive.adapters.Rest_Adapter;
import id.tech.rcslive.models.Pojo_EventHighlight;
import id.tech.rcslive.models.Rowdata_EventCalendar;
import id.tech.rcslive.models.Rowdata_EventJoined;
import id.tech.rcslive.util.ParameterCollections;
import id.tech.rcslive.util.PublicFunctions;
import retrofit.Call;
import retrofit.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Events_Calendar extends Fragment{
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    SharedPreferences spf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_rv_calendar, null);
        rv = (RecyclerView)view.findViewById(R.id.rv);

        spf = getActivity().getSharedPreferences(ParameterCollections.SPF_NAME, Context.MODE_PRIVATE);
        new AsyncTask_GetAll_EventByCalendar().execute();
        return view;
    }

    private class AsyncTask_GetAll_EventByCalendar extends AsyncTask<Void,Void,Void>{
        String cCode="0";
        List<Rowdata_EventCalendar> data;
        CustomAdapter_Calendar adapter_Calendar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            data = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Rest_Adapter adapter= PublicFunctions.initRetrofit();

            Call<Pojo_EventHighlight> call = adapter.get_all_events_calendar(ParameterCollections.KIND_EVENT,
                    "ON");

            try{
                Response<Pojo_EventHighlight> response = call.execute();

                if(response.isSuccess()){
                    if(response.body().getJsonCode().equals("1")){
                        for(int i=0; i < response.body().getData().size(); i++){
                            Rowdata_EventCalendar item = new Rowdata_EventCalendar();
                            item.setIdEvent(response.body().getData().get(i).getId());
                            item.setTvTgl(response.body().getData().get(i).getDeadline());
                            item.setTvJudul(response.body().getData().get(i).getEventTitle());
                            item.setTvAlamat(response.body().getData().get(i).getEventLocation());
                            item.setTvKategori(response.body().getData().get(i).getCategoriesName());
                            item.setEventPhoto(response.body().getData().get(i).getEventPhoto());

                            //blum ada Joined
                            item.setJoined(response.body().getData().get(i).getEventMinjoin());
                            item.setEventMinjoin(response.body().getData().get(i).getEventMinjoin());

                            item.setMemberPhone(response.body().getData().get(i).getMemberPhone());
                            item.setMemberPhoto(response.body().getData().get(i).getMemberPhoto());

                            String tgl_spf = spf.getString(ParameterCollections.SPF_SAME_DATE, "");

                            if(tgl_spf.equals("") || !tgl_spf.equals(response.body().getData().get(i).getDeadline())){
                                item.setTypeView(0);
                            }else{
                                item.setTypeView(1);
                            }
                            spf.edit().putString(ParameterCollections.SPF_SAME_DATE, response.body().getData().get(i).getDeadline()).commit();

                            data.add(item);
                        }
                    }
                }
            }catch (IOException e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            layoutManager = new GridLayoutManager(getContext(),1);
            adapter = new RV_Adapter_Calendar(getActivity(), data);

//            adapter_Calendar = new CustomAdapter_Calendar(getActivity(),0,data);
//            SimpleSectionedListAdapter adapter_pinnedListview = new SimpleSectionedListAdapter(getActivity(),
//                    adapter_Calendar, R.layout.item_event_calendar_header, R.id.tv_header);
//            adapter_pinnedListview.setSections(data.toArray(new SimpleSectionedListAdapter.Section[0]));
//            rv.setAdapter(adapter_pinnedListview);


            rv.setLayoutManager(layoutManager);
            rv.setAdapter(adapter);
        }
    }
}
