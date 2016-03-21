package id.tech.rcslive.activity;

import butterknife.OnClick;
import id.tech.rcslive.util.*;
import id.tech.rcslive.fragment.WalkthroughFragmentActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginForm  extends AppCompatActivity {
    private CallbackManager cm;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private LoginResult json_result;
    @Bind(R.id.btn_fb_connect) View btn_Fb;
    private String user_id, user_email, user_fullname, user_foto, user_gender;
    @Bind(R.id.ed_username)
    EditText ed_Username;
    @Bind(R.id.ed_password) EditText ed_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_form);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Gabung Yuk");

        btn_Fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginForm.this, Arrays.asList("public_profile", "email"));
            }
        });

        cm = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = AccessToken.getCurrentAccessToken();


            }
        };


        LoginManager.getInstance().registerCallback(cm, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("sukses", loginResult.toString());
                        json_result = loginResult;

                        GraphRequest request = GraphRequest.newMeRequest(json_result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject user, GraphResponse response) {

                                user_id = user.optString("id");
                                user_fullname = user.optString("name");
                                user_email = user.optString("email");
                                user_gender = user.optString("gender");

                                try {
//                            String url_img_profile = "http://graph.facebook.com/"+id+"/picture.type(large)";
//                            ServiceHandlerJSON sh = new ServiceHandlerJSON();

                                    JSONObject obj_pic = user.optJSONObject("picture");
                                    JSONObject obj_data = obj_pic.optJSONObject("data");
                                    user_foto = obj_data.optString("url");


                                } catch (Exception e) {

                                }

                                Log.e("fb response = ", user_fullname + " , "+ user_email +", "+ user_foto);
                            }
                        });

                        final Bundle b = new Bundle();
                        b.putString("fields", "name,email,gender,picture.type(large)");
                        request.setParameters(b);
                        request.executeAsync();

//                GraphRequest.newMeRequest(json_result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//
//                    @Override
//                    public void onCompleted(JSONObject user, GraphResponse response) {
//                        String id = user.optString("id");
//                        String name = user.optString("name");
//                        String email = user.optString("email");
//
//
//                    }
//                }).executeAsync();

//                new AsyncTask_FB_LOGIN().execute();
                    }

                    @Override
                    public void onCancel() {
                        Log.e("canceled", "");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("error", error.getMessage().toString());
                    }
                }

        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cm.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

            case R.id.action_login:
                startActivity(new Intent(getApplicationContext(), MenuUtama.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
