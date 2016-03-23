package id.tech.rcslive.fragment;

/**
 * Created by RebelCreative-A1 on 21/03/2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.tech.rcslive.activity.R;
import id.tech.rcslive.models.*;
import id.tech.rcslive.adapters.*;

public class Events_Highlight extends Fragment {
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_rv, null);
        rv = (RecyclerView)view.findViewById(R.id.rv);
        layoutManager = new GridLayoutManager(getContext(),1);

        List<Rowdata_EventHighlight> data = new ArrayList<>();
        Rowdata_EventHighlight item = new Rowdata_EventHighlight();
        item.setIdEvent("1");
        item.setTvTgl("Mon, 1 April 2016");
        item.setTvJudul("Kumpul Kangen Angkatan 2010");
        item.setTvAlamat("Jl. Soekarno Hatta no. 45, Gedung Mahaka Square. Jakarta Timur");
        item.setTvKategori("#Reuni");
        item.setJoined("58 People Joined");
        data.add(item);

        Rowdata_EventHighlight item2 = new Rowdata_EventHighlight();
        item2.setIdEvent("2");
        item2.setTvTgl("Tue, 12 April 2016");
        item2.setTvJudul("Latihan Futsal Bulanan");
        item2.setTvAlamat("Futsal Celebrity, Kelapa Gading. Jakarta Timur");
        item2.setTvKategori("#Sport");
        item2.setJoined("20 People Joined");
        data.add(item2);

        adapter = new RV_Adapter_Highlight(getContext(), data);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        return view;
    }


}
