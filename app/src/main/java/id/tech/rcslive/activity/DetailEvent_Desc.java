package id.tech.rcslive.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by macbook on 4/1/16.
 */
public class DetailEvent_Desc extends AppCompatActivity{
    @Bind(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_desc);
        ButterKnife.bind(this);

        tv.setText(getIntent().getStringExtra("desc"));
    }
}
