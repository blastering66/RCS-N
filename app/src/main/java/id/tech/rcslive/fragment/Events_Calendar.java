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
import id.tech.rcslive.models.Rowdata_EventCalendar;
import id.tech.rcslive.models.Rowdata_EventJoined;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_rv_calendar, null);
        rv = (RecyclerView)view.findViewById(R.id.rv);

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

            Rowdata_EventCalendar item = new Rowdata_EventCalendar();
            item.setIdEvent("");
            item.setTvTgl("2016-04-09 18:00");
            item.setTvJudul("Health Talk AIA");
            item.setTvAlamat("");
            item.setTvKategori("Umum");
            item.setEventPhoto("");
            item.setJoined("10 Joined");
            item.setTypeView(0);
            data.add(item);

            Rowdata_EventCalendar item2 = new Rowdata_EventCalendar();
            item2.setIdEvent("");
            item2.setTvTgl("2016-04-10 09:00");
            item2.setTvJudul("Badminton - PLN Duren Tiga");
            item2.setTvAlamat("");
            item2.setTvKategori("Sport");
            item2.setEventPhoto("");
            item2.setJoined("15 Joined");
            item2.setTypeView(1);
            data.add(item2);
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
