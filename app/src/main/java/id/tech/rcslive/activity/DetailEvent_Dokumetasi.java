package id.tech.rcslive.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.tech.rcslive.adapters.RV_Adapter_Event_Dokumentasi;
import id.tech.rcslive.adapters.Rest_Adapter;
import id.tech.rcslive.models.Pojo_Dokumentasi;
import id.tech.rcslive.models.RowData_Dokumentasi;
import id.tech.rcslive.util.ParameterCollections;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by macbook on 4/1/16.
 */
public class DetailEvent_Dokumetasi extends AppCompatActivity{
    @Bind(R.id.rv)
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    String event_documentationid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rv_white_add_documentasi);
        ButterKnife.bind(this);

        ActionBar ac = getSupportActionBar();
        ac.setDisplayHomeAsUpEnabled(true);
        ac.setTitle("Event Photos");

        event_documentationid= getIntent().getStringExtra("event_documentationid");
        layoutManager = new GridLayoutManager(getApplicationContext(),3);

        new AsyncTask_LoadDokumentasi().execute();
    }

    private class AsyncTask_LoadDokumentasi extends AsyncTask<Void,Void,Void>{
        String cCode="1";
        List<RowData_Dokumentasi> data;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            data = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            RowData_Dokumentasi item= new RowData_Dokumentasi();
            item.setId("");
            item.setDocumentationPhoto(ParameterCollections.BASE_URL_IMG + "file-page1 (FILEminimizer).jpg");
            data.add(item);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(ParameterCollections.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            Rest_Adapter adapter = retrofit.create(Rest_Adapter.class);

            //sementara
//            event_documentationid = "doc20161112";
            Call<Pojo_Dokumentasi> call = adapter.get_all_dokumentasi("documentationbyid_documentation" ,event_documentationid);
            try{
                Response<Pojo_Dokumentasi> response = call.execute();
                if(response.isSuccess()){
                    if(response.body().getJsonCode().equals("1")){
                        if(response.body().getData() != null){
                            for(int i=0; i< response.body().getData().size(); i++){
//                                RowData_Dokumentasi item= new RowData_Dokumentasi();
//                                item.setId(response.body().getData().get(i).getId());
//                                item.setDocumentationPhoto(ParameterCollections.BASE_URL_IMG + response.body().getData().get(i).getDocumentationPhoto());
//                                data.add(item);

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
                adapter = new RV_Adapter_Event_Dokumentasi(getApplicationContext(), data);
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
