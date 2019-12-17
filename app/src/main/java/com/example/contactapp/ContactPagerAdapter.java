package com.example.contactapp;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ContactPagerAdapter extends FragmentPagerAdapter {

    ArrayList<ContactJDO> mArrayList = new ArrayList<>();

    private final String TAG = "ContactPagerAdapter";

    public ContactPagerAdapter(FragmentManager pFragmentManager, ArrayList<ContactJDO> pArrayList) {
        super(pFragmentManager);
        this.mArrayList = pArrayList;
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount: ");
        return mArrayList.size();
    }

    @Override
    public Fragment getItem(int pPosition) {
        Log.i(TAG, "getItem: ");
        return new PagerFragment(mArrayList.get(pPosition),pPosition);
    }
}