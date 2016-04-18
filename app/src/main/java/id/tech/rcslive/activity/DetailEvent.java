package id.tech.rcslive.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.pkmmte.view.CircularImageView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.blastering99.htmlloader.CustomProgressDialog;
import id.tech.rcslive.adapters.Rest_Adapter;
import id.tech.rcslive.models.PojoResponseGmap;
import id.tech.rcslive.models.PojoResponseInsert;
import id.tech.rcslive.models.Pojo_Comment;
import id.tech.rcslive.models.Pojo_Dokumentasi;
import id.tech.rcslive.models.Pojo_EventUserJoined;
import id.tech.rcslive.util.ParameterCollections;
import id.tech.rcslive.util.PublicFunctions;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by macbook on 4/1/16.
 */
public class DetailEvent extends AppCompatActivity {
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    SharedPreferences spf;
    @Bind(R.id.tv_alamat)
    TextView tv_alamat;
    @Bind(R.id.tv_tgl)
    TextView tv_tgl;
    @Bind(R.id.tv_jarak)
    TextView tv_jarak;
    @Bind(R.id.tv_waktu_tempuh)
    TextView tv_waktu_tempuh;
    @Bind(R.id.tv_desc)
    TextView tv_desc;
    @Bind(R.id.img_map)
    ImageView img_map;
    @Bind(R.id.img_event)
    ImageView img_event;
    @Bind(R.id.img_joined_1)
    ImageView img_joined_1;
    @Bind(R.id.img_joined_2)
    ImageView img_joined_2;
    @Bind(R.id.img_joined_3)
    ImageView img_joined_3;
    @Bind(R.id.btn_more_desc)
    Button btn_more_desc;
    @Bind(R.id.btn_more_photo)
    Button btn_more_photo;
    @Bind(R.id.btn_more_people_joined)
    Button btn_more_people_joined;
    String id_event, event_documentationid;
    @Bind(R.id.frame_comment_top)
    FrameLayout frame_comment_top;
    @Bind(R.id.frame_comment_top_2)
    FrameLayout frame_comment_top_2;
    @Bind(R.id.img_gallery_01)
    ImageView img_gallery_01;
    @Bind(R.id.img_gallery_02)
    ImageView img_gallery_02;
    @Bind(R.id.img_gallery_03)
    ImageView img_gallery_03;
    @Bind(R.id.tv_no_comment)
    TextView tv_no_comment;
    @Bind(R.id.btn_more_people_joined_img) CircularImageView btn_more_people_joined_img;
    @Bind(R.id.btn_gallery_img) ImageView btn_gallery_img;
    @Bind(R.id.img_pic) CircularImageView img_pic;
    @Bind(R.id.tv_pic) TextView tv_pic;
    @Bind(R.id.btn_call_pic) ImageView btn_call_pic;
    String member_phone;
    String id_user;
    String now_latitude, now_longitude, lat_event, lon_event;

    private Activity activity;

    @OnClick(R.id.btn_call_pic) void onClick_CallPIC(){
        try {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + member_phone)));
        }catch (SecurityException e){

        }

    }

    @OnClick(R.id.btn_more_people_joined_img) void onClickMorePeopleJoined(){
        Intent intent = new Intent(getApplicationContext(), DetailEvent_UserJoined.class);
        intent.putExtra("id_event", id_event);
        startActivity(intent);
    }

    @OnClick(R.id.btn_gallery_img) void onClickMoreDoukemtasi(){
        Intent intent = new Intent(getApplicationContext(), DetailEvent_Dokumetasi.class);
        intent.putExtra("event_documentationid", event_documentationid);
        startActivity(intent);
    }

    @OnClick(R.id.btn_more_desc)
    void onCLick() {
        if (btn_more_desc.getText().toString().equals("MORE")) {
            tv_desc.setMaxLines(50);
            btn_more_desc.setText("LESS");
        } else {
            tv_desc.setMaxLines(3);
            btn_more_desc.setText("MORE");
        }

    }

    @OnClick(R.id.btn_more_people_joined)
    void onCLickMoreUserJoined() {
        Intent intent = new Intent(getApplicationContext(), DetailEvent_UserJoined.class);
        intent.putExtra("id_event", id_event);
        startActivity(intent);

    }

    @OnClick(R.id.btn_more_photo)
    void onCLickMoreDokumentasi() {
        Intent intent = new Intent(getApplicationContext(), DetailEvent_Dokumetasi.class);
        intent.putExtra("event_documentationid", event_documentationid);
        startActivity(intent);

    }

    @OnClick(R.id.btn_more_komentar)
    void onCLickMoreKomentar() {
        Intent intent = new Intent(getApplicationContext(), DetailEvent_Comment.class);
        intent.putExtra("id_event", id_event);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        activity = this;
        ButterKnife.bind(this);
        spf = getSharedPreferences(ParameterCollections.SPF_NAME, MODE_PRIVATE);
        id_user = spf.getString(ParameterCollections.SPF_USER_ID, "");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.img_arrow_back);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed()
                finish();
            }
        });

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);

        String url_photo_event = getIntent().getStringExtra("url_photo_event");
        String judul_event = getIntent().getStringExtra("judul_event");
        String alamat_event = getIntent().getStringExtra("alamat_event");
        String tgl_event = getIntent().getStringExtra("tgl_event");
        lat_event = getIntent().getStringExtra("lat_event");
        lon_event = getIntent().getStringExtra("lon_event");
        String desc_event = getIntent().getStringExtra("desc_event");

        String member_name = getIntent().getStringExtra("member_name");
        member_phone = getIntent().getStringExtra("member_phone");
        String member_photo = getIntent().getStringExtra("member_photo");

        Target target = new SimpleTarget<Bitmap>(){
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                img_pic.setImageBitmap(resource);
            }
        };

        Glide.with(this).load(member_photo).asBitmap().into(target);
        tv_pic.setText(member_name);

        id_event = getIntent().getStringExtra("id_event");
        event_documentationid = getIntent().getStringExtra("event_documentationid");

        mCollapsingToolbarLayout.setTitle(judul_event);
        tv_alamat.setText(alamat_event);
        tv_tgl.setText(tgl_event);
//        tv_desc.setText(desc_event);

        PublicFunctions.getLocationNow(getApplicationContext(), spf);

        now_latitude        = spf.getString(ParameterCollections.TAG_LATITUDE_NOW, "0");
        now_longitude = spf.getString(ParameterCollections.TAG_LONGITUDE_NOW, "0");

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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask_JoinEvent().execute();
            }
        });

        new AsyncTask_LoadTopComment().execute();
        new AsyncTask_LoadTopDokumentasi().execute();
        new AsyncTask_LoadTopUserJoined().execute();
        new ASyncTask_CalculateDistance().execute();
    }

    private class ASyncTask_CalculateDistance extends AsyncTask<Void,Void,Void>{
        String distance, duration;
        boolean isSukses= false;
        @Override
        protected Void doInBackground(Void... params) {
            try{
                Rest_Adapter adapter = PublicFunctions.initRetrofit_Gmaps();
                Call<PojoResponseGmap> call = adapter.calculate_distance(now_latitude + "," + now_longitude,
                        lat_event + "," + lon_event, "false", "metric");
                Response<PojoResponseGmap> response = call.execute();
                if(response.isSuccess()){
                    if(response.body() != null){
                        isSukses = true;
                        distance = response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText();
                        duration = response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText();
                    }

                }
            }catch (IOException e){

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(isSukses){
                tv_jarak.setText("Distance " + distance);
                tv_waktu_tempuh.setText("Estimated Time " + duration );
            }else{
                tv_jarak.setText("");
                tv_waktu_tempuh.setText("");
            }

        }
    }

    private class AsyncTask_JoinEvent extends AsyncTask<Void, Void, Void> {
        //        private CustomProgressDialog loader;
        private CustomProgressDialog loader;
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loader = new CustomProgressDialog(activity, R.style.SpotsDialogDefault);
            loader.setLoaderType(CustomProgressDialog.SPINNING_SQUARE);
            loader.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Rest_Adapter adapter = PublicFunctions.initRetrofit();
                Call<PojoResponseInsert> call = adapter.insert_join_event(
                        ParameterCollections.KIND_JOIN, id_event,id_user);
//                Call<PojoResponseInsert> call = adapter.insert_join_event_get();

                Response<PojoResponseInsert> response = call.execute();

                if(response.isSuccess()){
                    if(response.body() != null){
                        if(response.body().getJsonCode().equals("1")){
                            if(response.body().getData().equals("1")){
                                isSuccess = true;
                            }
                        }
                    }
                }

            } catch (IOException e) {
                Log.e("Error = " ,e.getMessage().toString());

            }catch (Exception e) {
                Log.e("Error = " , e.getMessage().toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loader.dismiss();

            if(isSuccess){
                View view = findViewById(R.id.fab);
                Snackbar.make(view, "Event Joined", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else{
                View view = findViewById(R.id.fab);
                Snackbar.make(view, "Fail to Join", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }


        }
    }

    private class AsyncTask_LoadTopUserJoined extends AsyncTask<Void, Void, Void> {
        private String url1, url2, url3;
        private boolean isRestSucceded = false;

        @Override
        protected Void doInBackground(Void... params) {
            Rest_Adapter adapter = PublicFunctions.initRetrofit();
            Call<Pojo_EventUserJoined> call = adapter.get_top_user_joined(ParameterCollections.KIND_USER_JOINED,
                    id_event, "3");
            try {
                Response<Pojo_EventUserJoined> response = call.execute();

                if (response.isSuccess()) {
                    if(response.body() != null){
                        if (response.body().getJsonCode().equals("1")) {
                            isRestSucceded = true;
                            if (response.body().getData().size() == 3) {
                                for (int i = 0; i < response.body().getData().size(); i++) {
                                    switch (i) {
                                        case 0:
                                            url1 = response.body().getData().get(i).getUserjoinedPhoto();
                                            break;
                                        case 1:
                                            url2 = response.body().getData().get(i).getUserjoinedPhoto();
                                            break;
                                        case 2:
                                            url3 = response.body().getData().get(i).getUserjoinedPhoto();
                                            break;
                                    }
                                }
                            }
                        }
                    }

                }
            } catch (IOException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(isRestSucceded){
                img_joined_1.setVisibility(View.VISIBLE);
                img_joined_2.setVisibility(View.VISIBLE);
                img_joined_3.setVisibility(View.VISIBLE);

                SimpleTarget target  = new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        img_joined_1.setImageBitmap(resource);
                    }
                };

                SimpleTarget target2  = new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        img_joined_2.setImageBitmap(resource);
                    }
                };

                SimpleTarget target3  = new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        img_joined_3.setImageBitmap(resource);
                    }
                };

                Glide.with(activity).load(url2).asBitmap().into(target);
                Glide.with(activity).load(url2).asBitmap().into(target2);
                Glide.with(activity).load(url3).asBitmap().into(target3);
            }
        }
    }

    private class AsyncTask_LoadTopDokumentasi extends AsyncTask<Void, Void, Void> {
        private String url1, url2, url3;
        private boolean isRestSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ParameterCollections.BASE_URL).build();

            Rest_Adapter adapter = retrofit.create(Rest_Adapter.class);
            Call<Pojo_Dokumentasi> call = adapter.get_top_dokumentasi(ParameterCollections.KIND_DOKUMENTASI,
                    event_documentationid, "3");

            Log.e("Event Id = ", event_documentationid);
            try {
                Response<Pojo_Dokumentasi> response = call.execute();

                if (response.isSuccess()) {
                    if(response.body() != null){
                        if (response.body().getJsonCode().equals("1")) {
                            isRestSuccess = true;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                switch (i) {
                                    case 0:
                                        url1 = ParameterCollections.BASE_URL_IMG + response.body().getData().get(i).getDocumentationPhoto();
                                        break;
                                    case 1:
                                        url2 = ParameterCollections.BASE_URL_IMG + response.body().getData().get(i).getDocumentationPhoto();
                                        break;
                                    case 2:
                                        url3 = ParameterCollections.BASE_URL_IMG + response.body().getData().get(i).getDocumentationPhoto();
                                        break;
                                }
                            }
                        }
                    }

                }
            } catch (IOException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (isRestSuccess) {
                img_gallery_01.setVisibility(View.VISIBLE);
                img_gallery_02.setVisibility(View.VISIBLE);
                img_gallery_03.setVisibility(View.VISIBLE);

                SimpleTarget target  = new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        img_gallery_01.setImageBitmap(resource);
                    }
                };

                SimpleTarget target2  = new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        img_gallery_02.setImageBitmap(resource);
                    }
                };

                SimpleTarget target3  = new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        img_gallery_03.setImageBitmap(resource);
                    }
                };

                Glide.with(activity).load(url2).asBitmap().into(target);
                Glide.with(activity).load(url2).asBitmap().into(target2);
                Glide.with(activity).load(url3).asBitmap().into(target3);
            }
        }
    }

    private class AsyncTask_LoadTopComment extends AsyncTask<Void, Void, Void> {
        private String cCommentor00, cComment00, cPhotoCommentor00,
                cCommentor01, cComment01, cPhotoCommentor01;
        boolean isCallSucceed = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ParameterCollections.BASE_URL).build();
            Rest_Adapter adapter = retrofit.create(Rest_Adapter.class);

            Call<Pojo_Comment> call = adapter.get_top_comments(ParameterCollections.KIND_COMMENTS_EVENT,
                    id_event, "2");

            try {
                Response<Pojo_Comment> response = call.execute();
                if (response.isSuccess()) {
                    if(response.body() != null){
                        if (response.body().getJsonCode().equals("1") ) {
                            if (response.body().getData().size() == 2) {
                                isCallSucceed = true;
                                cCommentor00 = response.body().getData().get(0).getMemberName();
                                cComment00 = response.body().getData().get(0).getCommentsText();
                                cPhotoCommentor00 = response.body().getData().get(0).getMemberPhoto();

                                cCommentor01 = response.body().getData().get(1).getMemberName();
                                cComment01 = response.body().getData().get(1).getCommentsText();
                                cPhotoCommentor01 = response.body().getData().get(1).getMemberPhoto();
                            }
                        }
                    }


                } else {

                }
            } catch (IOException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (isCallSucceed) {
                tv_no_comment.setVisibility(View.GONE);

                LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
                View view_Comment_00 = mInflater.inflate(R.layout.item_comment, null);
                CircularImageView img_00 = (CircularImageView) view_Comment_00.findViewById(R.id.img_commentor);
                TextView tv_commentor_00 = (TextView) view_Comment_00.findViewById(R.id.tv_commentor);
                TextView tv_comment_00 = (TextView) view_Comment_00.findViewById(R.id.tv_comment);
                Glide.with(DetailEvent.this).load(cPhotoCommentor00).into(img_00);
                tv_commentor_00.setText(cCommentor00);
                tv_comment_00.setText(cComment00);
                frame_comment_top.addView(view_Comment_00);

                LayoutInflater mInflater1 = LayoutInflater.from(getApplicationContext());
                View view_Comment_01 = mInflater1.inflate(R.layout.item_comment, null);
                CircularImageView img_01 = (CircularImageView) view_Comment_01.findViewById(R.id.img_commentor);
                TextView tv_commentor_01 = (TextView) view_Comment_01.findViewById(R.id.tv_commentor);
                TextView tv_comment_01 = (TextView) view_Comment_01.findViewById(R.id.tv_comment);
                Glide.with(DetailEvent.this).load(cPhotoCommentor01).into(img_01);
                tv_commentor_01.setText(cCommentor01);
                tv_comment_01.setText(cComment01);
                frame_comment_top_2.addView(view_Comment_01);
            }

        }
    }

}
