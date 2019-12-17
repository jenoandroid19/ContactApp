package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewContact extends AppCompatActivity {

    private ViewPager mViewPager;
    private ContactPagerAdapter mPagerAdapter;
    private int mListPosition;
    public ArrayList<ContactJDO> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        mViewPager = findViewById(R.id.viewpager);

        mArrayList = getIntent().getParcelableArrayListExtra("contactObj");
        mListPosition = getIntent().getExtras().getInt("listPosition");

        mPagerAdapter = new ContactPagerAdapter(getSupportFragmentManager(), mArrayList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mListPosition);
    }
}
