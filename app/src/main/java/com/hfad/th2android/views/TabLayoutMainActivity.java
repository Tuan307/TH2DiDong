package com.hfad.th2android.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.tabs.TabLayoutMediator;
import com.hfad.th2android.R;
import com.hfad.th2android.adapters.MyViewPagerAdapter;
import com.hfad.th2android.databinding.ActivityTabLayoutMainBinding;

import java.util.ArrayList;

public class TabLayoutMainActivity extends AppCompatActivity {

    private ActivityTabLayoutMainBinding binding;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab_layout_main);

        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        myViewPagerAdapter.addFragment(new FragmentList());
        myViewPagerAdapter.addFragment(new FragmentInfo());
        myViewPagerAdapter.addFragment(new FragmentSearch());
        binding.tabViewPager.setAdapter(myViewPagerAdapter);
        ArrayList<String> list = new ArrayList<>();
        list.add("Fragment List");
        list.add("Fragment Info");
        list.add("Fragment Search");
        new TabLayoutMediator(binding.tabLayout1, binding.tabViewPager, false, (tab, position) -> {
            tab.setText(list.get(position));
        }).attach();

        binding.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOpenAddActivity = new Intent(TabLayoutMainActivity.this, AddActivity.class);
                startActivity(intentOpenAddActivity);
            }
        });
    }

}