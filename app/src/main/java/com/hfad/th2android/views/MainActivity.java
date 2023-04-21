package com.hfad.th2android.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.hfad.th2android.R;
import com.hfad.th2android.adapters.MyViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private MyViewPagerAdapter myViewPagerAdapter;

    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        // Khởi tạo adapter và thêm 3 fragment vào adapter, sau đó setAdapter cho viewPager để viewPager có thể hiển thị các fragment
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        myViewPagerAdapter.addFragment(new FragmentList());
        myViewPagerAdapter.addFragment(new FragmentInfo());
        myViewPagerAdapter.addFragment(new FragmentSearch());
        viewPager2.setAdapter(myViewPagerAdapter);

        // Bắt sự kiện bấm vào tab item dưới thanh BottomNavigationView thì chuyển fragment hiển thị trên view pager
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_list:
                        viewPager2.setCurrentItem(0,false);
                        break;
                    case R.id.menu_item_info:
                        viewPager2.setCurrentItem(1, false);
                        break;
                    case R.id.menu_item_search:
                        viewPager2.setCurrentItem(2,false);
                        break;
                }
                return true;
            }
        });

        // Bắt sự kiện khi người dùng swipe ngang qua lại cái viewpager thì mình hiển thị tab item tương ứng dưới BottomNavigationView
        // được lựa chọn
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_item_list).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_item_info).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_item_search).setChecked(true);
                        break;
                }
            }
        });

        fab.setOnClickListener(view -> {
            Intent intentOpenAddActivity = new Intent(MainActivity.this,AddActivity.class);
            startActivity(intentOpenAddActivity);
        });

    }

    private void initView() {
        viewPager2 = findViewById(R.id.viewPager2);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
    }
}