package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.Callbacks{

    private static final String EXTRA_CRIME_ID = "com.example.criminalIntent.crime_id";
    private static final String TAG = "CrimePagerActivity: ";

    private ViewPager2 mViewPager;
    private List<Crime> mCrimes;
    private ViewPagerAdapter mAdapter;

//--------------------------------------------------------------------------------------------------
    @Override
    public void onCrimeUpdated(Crime crime) {

    }
    @Override
    public void onCrimeDelete(Crime crime) {
        int position = 0;
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getmId().equals(crime.getmId())) {
                position = i;
                break;
            }
        }
        Toast.makeText(this, position + " ", Toast.LENGTH_LONG).show();
        CrimeLab.get(this).deleteCrime(crime);
        mCrimes.remove(position);
//        mAdapter.notifyItemRemoved(position);
//        updateUI();
//        position = position % mCrimes.size() == 0 ?
//                (position-1) % mCrimes.size() : position % mCrimes.size();
        this.finish();

    }

//--------------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_crime_pager);

        mCrimes = CrimeLab.get(this).getmCrimes();
        mViewPager = (ViewPager2) findViewById(R.id.crime_view_pager);
//        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
//        mViewPager.setOffscreenPageLimit(5);
//        mViewPager.setPageTransformer(new MarginPageTransformer(dpToPx(16)));
//        mViewPager.setAdapter(mAdapter);
        updateUI();

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        for (int i = 0; i < mCrimes.size(); i++){
            if (mCrimes.get(i).getmId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }

    public void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(this);
        List<Crime> crimes = crimeLab.getmCrimes();
        if (mAdapter == null) {
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
            mViewPager.setOffscreenPageLimit(5);
            mViewPager.setPageTransformer(new MarginPageTransformer(dpToPx(16)));
            mViewPager.setAdapter(mAdapter);
        } else {
            mAdapter.setCrimes(crimes);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {

        private List<Crime> mCrimes_2;

        public ViewPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
            mCrimes_2 = CrimeLab.get(CrimePagerActivity.this).getmCrimes();
        }

        @Override
        public int getItemCount() {
            return mCrimes_2.size();
        }

        @Override
        public Fragment createFragment(int position) {
            Crime crime = mCrimes_2.get(position);
            return CrimeFragment.newInstance(crime.getmId());
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes_2 = crimes;
        }
    }

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    private int dpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart()");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume()");
    }
    @Override
    public void onPause(){
        super.onPause();
        int i = mViewPager.getCurrentItem();
        Log.d(TAG, "onPause() " + i);
        Intent intent = getIntent();
        Crime crime = mCrimes.get(i);
        intent.putExtra(EXTRA_CRIME_ID, crime.getmId());
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop()");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}
