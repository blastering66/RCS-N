package id.tech.rcslive.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.pkmmte.view.CircularImageView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.blastering99.htmlloader.CustomProgressDialog;
import id.tech.rcslive.adapters.Rest_Adapter;
import id.tech.rcslive.dialogs.DialogConfirmation;
import id.tech.rcslive.models.Pojo_ResponseAccountUser;
import id.tech.rcslive.util.ParameterCollections;
import id.tech.rcslive.util.PublicFunctions;
import retrofit.Call;
import retrofit.Response;

/**
 * Created by macbook on 5/19/16.
 */
public class AccountDetail extends AppCompatActivity {
    @Bind(R.id.img_profile)
    CircularImageView img_Profile;
    SharedPreferences spf;
    private ExitActivityTransition exitTransition;
    @OnClick(R.id.btn_logout) public void onLogout(){
        DialogConfirmation dialog = new DialogConfirmation();
        dialog.setSh(spf);
        dialog.setFrom(0);
        dialog.setText("Sure to Logout ?");
        dialog.show(getSupportFragmentManager(), "");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountdetail);
        ButterKnife.bind(this);
        ActionBar ac = getSupportActionBar();
        ac.setTitle("Account Detail");
        ac.setDisplayHomeAsUpEnabled(true);

        spf = getSharedPreferences(ParameterCollections.SPF_NAME, MODE_PRIVATE);

        new AsyncTask_LoadPorifle().execute();
//        ActivityTransition.with(getIntent()).to(img_Profile).start(savedInstanceState);
//        exitTransition = ActivityTransition.with(getIntent()).to(img_Profile).start(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_account, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
//                exitTransition.exit(AccountDetail.this);
                finish();
                break;
            case R.id.action_save:
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
//        exitTransition.exit(AccountDetail.this);
        finish();
    }

    private class AsyncTask_LoadPorifle extends AsyncTask<Void,Void,Void>{
        boolean isSukses = false;
        String message = "";
        private CustomProgressDialog pDialog;
        private String idUser, nameUser, urlPhotoUsr= "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new CustomProgressDialog(AccountDetail.this, R.style.SpotsDialogDefault);
            pDialog.setLoaderType(CustomProgressDialog.SPINNING_SQUARE);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Rest_Adapter adapter = PublicFunctions.initRetrofit();
            try {
                String id = spf.getString(ParameterCollections.SPF_USER_ID,"0");
                Call<Pojo_ResponseAccountUser> call = adapter.get_profile_user(id);

                Response<Pojo_ResponseAccountUser> response = call.execute();

                if(response.isSuccess()){
                    if(response.body() != null){
                       if(response.body().getJsonCode().equals("1")){
                           String name = response.body().getData().get(0).getMemberName();

                       }else{
                           message = response.body().getResponse();
                       }
                    }
                }else{
                    message = response.errorBody().toString();
                }

            }catch (IOException e){
                message = "Something Wrong, IOException";

            }catch (Exception e){
                message = "Something Wrong, General Exception  ";
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            if(isSukses){

            }else{
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
