package id.tech.rcslive.activity;

/**
 * Created by RebelCreative-A1 on 21/03/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import id.tech.rcslive.fragment.*;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MenuUtama extends AppCompatActivity implements MaterialTabListener {
    private MaterialTabHost tabHost;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuutama);
        ActionBar ac = getSupportActionBar();
        ac.hide();

//        ImageView img_Back = (ImageView)findViewById(R.id.btn_back);
//        img_Back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

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
