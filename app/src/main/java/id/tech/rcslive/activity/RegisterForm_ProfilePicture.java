package id.tech.rcslive.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by macbook on 5/13/16.
 */
public class RegisterForm_ProfilePicture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profilepic);
        ButterKnife.bind(this);

//        spf = getSharedPreferences(ParameterCollections.SPF_NAME, MODE_PRIVATE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Photo Profile");
    }
}
