package id.tech.rcslive.activity;

/**
 * Created by RebelCreative-A1 on 21/03/2016.
 */

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.pkmmte.view.CircularImageView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.tech.rcslive.adapters.Rest_Adapter;
import id.tech.rcslive.fragment.*;
import id.tech.rcslive.models.Pojo_EventHighlight;
import id.tech.rcslive.util.ParameterCollections;
import id.tech.rcslive.util.PublicFunctions;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import retrofit.Call;
import retrofit.Response;

public class MenuUtama extends AppCompatActivity implements MaterialTabListener {
    private MaterialTabHost tabHost;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    @Bind(R.id.img_profile)
    CircularImageView img_Profile;
    @Bind(R.id.tv_nama)
    TextView tv_Nama;
    SharedPreferences spf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuutama);
        ButterKnife.bind(this);
        ActionBar ac = getSupportActionBar();
        ac.hide();

        spf = getSharedPreferences(ParameterCollections.SPF_NAME, MODE_PRIVATE);

        new AsyncTask_LoadProfile().execute();

        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        viewPager = (ViewPager) this.findViewById(R.id.pager );

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabHost.setSelectedNavigationItem(position);
            }
        });

        //Menambah jenis Tab
        tabHost.addTab(tabHost.newTab().setText("Highlight").setTabListener(this));
        tabHost.addTab(tabHost.newTab().setText("Calendar").setTabListener(this));
        tabHost.addTab(tabHost.newTab().setText("Joined").setTabListener(this));

    }

    private class AsyncTask_LoadProfile extends AsyncTask<Void,Void,Void>{
        Bitmap bitmap_Profile;
        String url,username;

        @Override
        protected Void doInBackground(Void... params) {
             url= spf.getString(ParameterCollections.SPF_USER_PHOTO_URL, "");
             username= spf.getString(ParameterCollections.SPF_USER_NAME, "unknown");
            if(url.contains("jpg")){
                try{
                    bitmap_Profile = Glide.with(getApplicationContext()).
                            load(spf.getString(ParameterCollections.SPF_USER_PHOTO_URL, "unknown")).asBitmap().into(100,100).get();

                }catch (ExecutionException e){

                }catch (InterruptedException e){

                }


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            img_Profile.setImageBitmap(bitmap_Profile);
            tv_Nama.setText(username);
        }
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter{
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public Fragment getItem(int num) {
            switch (num){
                case 0:
                    return new Events_Highlight();
                case 1:
                    return new Events_Calendar();

                default:
                    return new Events_Joined();
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Highlight";
                case 1:
                    return "Calendar";
                case 2:
                    return "Joined";
                default:
                    return"";

            }

        }
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
}
