package id.tech.rcslive.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.tech.rcslive.adapters.RV_Adapter_Event_Joined;
import id.tech.rcslive.adapters.RV_Adapter_Highlight;
import id.tech.rcslive.adapters.RV_Adapter_Joined;
import id.tech.rcslive.adapters.Rest_Adapter;
import id.tech.rcslive.models.Pojo_EventUserJoined;
import id.tech.rcslive.models.Rowdata_EventUserJoined;
import id.tech.rcslive.util.ParameterCollections;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by macbook on 4/1/16.
 */
public class DetailEvent_UserJoined extends AppCompatActivity{
    @Bind(R.id.rv)
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    String id_event,event_documentationid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rv_white);
        ButterKnife.bind(this);
        id_event = getIntent().getStringExtra("id_event");


        ActionBar ac = getSupportActionBar();
        ac.setDisplayHomeAsUpEnabled(true);

        layoutManager = new GridLayoutManager(getApplicationContext(),3);

        new AsyncTask_LoadUserJoined().execute();
    }

    private class AsyncTask_LoadUserJoined extends AsyncTask<Void,Void,Void>{
        String cCode="0";
        List<Rowdata_EventUserJoined> data;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            data = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(ParameterCollections.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            Rest_Adapter adapter = retrofit.create(Rest_Adapter.class);

            Call<Pojo_EventUserJoined> call = adapter.get_all_user_joined(ParameterCollections.KIND_USER_JOINED, id_event);
            try{
                Response<Pojo_EventUserJoined> response = call.execute();
                if(response.isSuccess()){
                    if(response.body().getJsonCode().equals("1")){
                        if(response.body().getData() != null){
                            for(int i=0; i< response.body().getData().size(); i++){
                                Rowdata_EventUserJoined item= new Rowdata_EventUserJoined();
                                item.setUserjoinedId("");
                                item.setUserjoinedUniqueid("");
                                item.setUserjoinedName(response.body().getData().get(i).getUserjoinedName());
                                item.setUserjoinedPhoto(response.body().getData().get(i).getUserjoinedPhoto());
                                data.add(item);

                            }
                            cCode=response.body().getJsonCode();
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
            if(cCode.equals("1")){
                adapter = new RV_Adapter_Event_Joined(getApplicationContext(), data);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);
            }else{
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
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
