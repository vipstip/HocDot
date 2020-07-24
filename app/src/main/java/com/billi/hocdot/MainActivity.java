package com.billi.hocdot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;

import com.bil.bilmobileads.ADBanner;
import com.bil.bilmobileads.PBMobileAds;
import com.bil.bilmobileads.entity.BannerSize;
import com.bil.bilmobileads.interfaces.AdDelegate;
import com.billi.hocdot.Adapter.CustomExpandableListAdapter;
import com.billi.hocdot.Helpers.FragmentNavigationManage;
import com.billi.hocdot.Interface.NavigationManage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String,List<String>> lstChild;
    private NavigationManage navigationManage;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    ADBanner adBanner;
    FrameLayout bannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) ) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }
        }
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        expandableListView = (ExpandableListView) findViewById(R.id.nav_view);
        navigationManage = FragmentNavigationManage.getInstance(this);
        initItems();
        View listHeaderView = getLayoutInflater().inflate(R.layout.header_menu,null, false);
        expandableListView.addHeaderView(listHeaderView);
        genData();

        addDrawersItem();
        setupDrawer();

        if (savedInstanceState == null){
            selectFirstItemAsDefault();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);

        PBMobileAds.getInstance().initialize(getApplicationContext());

        this.bannerView = findViewById(R.id.bannerView);
        if (this.adBanner == null){
            this.adBanner = new ADBanner(bannerView,"464b8063-1569-49dc-96d9-0db7656e2576");
            adBanner.setAdSize(BannerSize.Banner320x50);
            adBanner.setAutoRefreshMillis(40000);
            this.adBanner.load();
            this.adBanner.setListener(new AdDelegate() {
                @Override
                public void onAdLoaded(String data) {

                }
                @Override
                public void onAdImpression(String data) {
                    PBMobileAds.getInstance().log(data);
                }

                @Override
                public void onAdClosed(String data) {
                    PBMobileAds.getInstance().log(data);
                }

                @Override
                public void onAdFailedToLoad(String err) {
                    PBMobileAds.getInstance().log(err);
                }

                @Override
                public void onAdLeftApplication(String data) {
                    PBMobileAds.getInstance().log(data);
                }
            });
        }
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirstItemAsDefault() {
        if (navigationManage != null){
            navigationManage.showFragment("0");
            getSupportActionBar().setTitle("Chọn Lớp");
        }
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.nav_open, R.string.nav_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void addDrawersItem() {
        adapter = new CustomExpandableListAdapter(this,lstTitle,lstChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                getSupportActionBar().setTitle(lstTitle.get(i).toString());
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                getSupportActionBar().setTitle("Menu");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                String selectedItem = ((List) (lstChild.get(lstTitle.get(i)))).get(i1).toString();
                if (items[1].equals(lstTitle.get(i))){
                    navigationManage.showFragment(selectedItem);
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (items[2].equals(lstTitle.get(i))){
                    navigationManage.showFragmentBangTinh();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                return false;
            }

        });
    }

    private void genData() {
        List<String> title = Arrays.asList("Tìm kiếm","Đổi lớp","Bảng tính trực tuyến");
        List<String> childitem = Arrays.asList("Lớp 1","Lớp 2","Lớp 3", "Lớp 4", "Lớp 5", "Lớp 6", "Lớp 7", "Lớp 8", "Lớp 9", "Lớp 10", "Lớp 11", "Lớp 12");

        lstChild = new TreeMap<>();
        lstChild.put(title.get(0),null);
        lstChild.put(title.get(1),childitem);
        lstChild.put(title.get(2),null);
        lstTitle = new ArrayList<>(title);

    }

    private void initItems() {
        items = new String[]{"Tìm kiếm","Đổi lớp","Bảng tính trực tuyến"};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        navigationManage.backFragment();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.adBanner.destroy();
    }

}

