package id.tech.rcslive.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.tech.rcslive.util.ParameterCollections;
import id.tech.rcslive.util.PublicFunctions;

/**
 * Created by macbook on 4/1/16.
 */
public class DetailEvent extends AppCompatActivity{
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    SharedPreferences spf;
    @Bind(R.id.tv_alamat) TextView tv_alamat;
    @Bind(R.id.tv_tgl) TextView tv_tgl;
    @Bind(R.id.tv_jarak ) TextView tv_jarak;
    @Bind(R.id.tv_waktu_tempuh) TextView tv_waktu_tempuh;
    @Bind(R.id.tv_desc) TextView tv_desc;
    @Bind(R.id.img_map) ImageView img_map;
    @Bind(R.id.img_event) ImageView img_event;
    @Bind(R.id.img_joined_1) ImageView img_joined_1;
    @Bind(R.id.img_joined_2) ImageView img_joined_2;
    @Bind(R.id.img_joined_3) ImageView img_joined_3;
    @Bind(R.id.btn_more_desc)Button btn_more_desc;

    @OnClick(R.id.btn_more_desc) void onCLick(){
        if(btn_more_desc.getText().toString().equals("MORE")){
            tv_desc.setMaxLines(50);
            btn_more_desc.setText("LESS");
        }else{
            tv_desc.setMaxLines(3);
            btn_more_desc.setText("MORE");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        spf = getSharedPreferences(ParameterCollections.SPF_NAME, MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.img_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed()
                finish();
            }
        });
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);

        String url_photo_event = getIntent().getStringExtra("url_photo_event");
        String judul_event = getIntent().getStringExtra("judul_event");
        String alamat_event = getIntent().getStringExtra("alamat_event");
        String tgl_event = getIntent().getStringExtra("tgl_event");
        String lat_event = getIntent().getStringExtra("lat_event");
        String lon_event = getIntent().getStringExtra("lon_event");
        String desc_event = getIntent().getStringExtra("desc_event");

        mCollapsingToolbarLayout.setTitle(judul_event);
        tv_alamat.setText(alamat_event);
        tv_tgl.setText(tgl_event);
//        tv_desc.setText(desc_event);

        PublicFunctions.getLocationNow(getApplicationContext(), spf);

        String now_latitude = spf.getString(ParameterCollections.TAG_LATITUDE_NOW, "0");
        String now_longitude = spf.getString(ParameterCollections.TAG_LONGITUDE_NOW, "0");

        String url_map = "http://maps.google.com/maps/api/staticmap?center="
                + lat_event
                + ","
                + lon_event
                + "&zoom=10&size=600x400&markers=color:blue|size:mid|"
                + lat_event
                + ","
                + lon_event
                + "&sensor=false";

        Glide.with(this).load(url_map).into(img_map);
        Glide.with(this).load(url_photo_event).into(img_event);

        Location selected_location=new Location("location_user");
        selected_location.setLatitude(Double.parseDouble(now_latitude));
        selected_location.setLongitude(Double.parseDouble(now_longitude));
        Location near_locations=new Location("location_event");
        near_locations.setLatitude(Double.parseDouble(lat_event));
        near_locations.setLongitude(Double.parseDouble(lon_event));

        double distance=selected_location.distanceTo(near_locations);
        tv_jarak.setText(String.valueOf(Math.round(distance)+ " Meter"));
        tv_waktu_tempuh.setText("1 Hour Estimated");
//        img_joined_1;
//        img_joined_2;
//        img_joined_3;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Event Joined", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
