package id.tech.rcslive.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.tech.rcslive.adapters.RV_Adapter_All_Event_Comments;
import id.tech.rcslive.adapters.RV_Adapter_Event_Dokumentasi;
import id.tech.rcslive.adapters.Rest_Adapter;
import id.tech.rcslive.models.Pojo_Comment;
import id.tech.rcslive.models.Pojo_Dokumentasi;
import id.tech.rcslive.models.RowData_Dokumentasi;
import id.tech.rcslive.models.Rowdata_Comment;
import id.tech.rcslive.util.ParameterCollections;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by macbook on 4/4/16.
 */
public class DetailEvent_Comment  extends AppCompatActivity {
    @Bind(R.id.rv)
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    String id_event;
    @Bind(R.id.btn_refresh)
    ImageView btn_refresh;
    @OnClick(R.id.btn_refresh) void onClickRefresh(){
        new AsyncTask_LoadComments().execute();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rv_white_add_comment);
        ButterKnife.bind(this);

        ActionBar ac = getSupportActionBar();
        ac.setDisplayHomeAsUpEnabled(true);
        ac.setTitle("Event Comments");

        id_event= getIntent().getStringExtra("id_event");
        layoutManager = new GridLayoutManager(getApplicationContext(),3);

        new AsyncTask_LoadComments().execute();
    }

    private class AsyncTask_Dummy extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class AsyncTask_LoadComments extends AsyncTask<Void,Void,Void> {
        String cCode="0";
        List<Rowdata_Comment> data;
        Animation animation;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            data = new ArrayList<>();

            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
            animation.setRepeatMode(Animation.INFINITE);

            rv.setVisibility(View.GONE);
            btn_refresh.setVisibility(View.VISIBLE);
            btn_refresh.setAnimation(animation);

        }

        @Override
        protected Void doInBackground(Void... params) {
//            RowData_Dokumentasi item= new RowData_Dokumentasi();
//            item.setId("");
//            item.setDocumentationPhoto(ParameterCollections.BASE_URL_IMG + "file-page1 (FILEminimizer).jpg");
//            data.add(item);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(ParameterCollections.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            Rest_Adapter adapter = retrofit.create(Rest_Adapter.class);

            //sementara
//            event_documentationid = "doc20161112";
            Call<Pojo_Comment> call = adapter.get_all_event_comments("commentsbyid_event", id_event);
            try{
                Thread.sleep(1000);
                Response<Pojo_Comment> response = call.execute();
                if(response.isSuccess()){
                    if(response.body().getJsonCode().equals("1")){
                        if(response.body().getData() != null){
                            for(int i=0; i< response.body().getData().size(); i++){
                                cCode = "1";
                                Rowdata_Comment item = new Rowdata_Comment();

                                String cCommentor00 = response.body().getData().get(i).getMemberName();
                                String cComment00 = response.body().getData().get(i).getCommentsText();
                                String cPhotoCommentor00 = response.body().getData().get(i).getMemberPhoto();
//                                RowData_Dokumentasi item= new RowData_Dokumentasi();
//                                item.setId(response.body().getData().get(i).getId());
//                                item.setDocumentationPhoto(ParameterCollections.BASE_URL_IMG + response.body().getData().get(i).getDocumentationPhoto());
                                item.setNamaCommentor(cCommentor00);
                                item.setCommentDescription(cComment00);
                                item.setCommentorPhoto(cPhotoCommentor00);

                                data.add(item);

                            }
                            cCode=response.body().getJsonCode();
                        }
                    }



                }
            }catch (IOException e){

            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(cCode.equals("1")){
                rv.setVisibility(View.VISIBLE);
                btn_refresh.setVisibility(View.GONE);
                btn_refresh.setImageResource(R.drawable.img_transparent);

                adapter = new RV_Adapter_All_Event_Comments(data,getApplicationContext());
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);
            }else{
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
                rv.setVisibility(View.GONE);
                btn_refresh.setVisibility(View.VISIBLE);
                animation.cancel();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return false;
    }
}
