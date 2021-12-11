package com.example.criminalintent;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks,
                                                                         CrimeFragment.Callbacks {

    private static final String TAG = "CrimeListActivity: ";

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getmId());
            startActivity(intent);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getmId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }
    @Override
    public void onDeleteFragment() {
        if (findViewById(R.id.detail_fragment_container) != null) {
            CrimeFragment fragment = (CrimeFragment) getSupportFragmentManager().
                    findFragmentById(R.id.detail_fragment_container);
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }
    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        listFragment.updateUI();
    }
    @Override
    public void onUpdated(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) != null) {
            Fragment newDetail = CrimeFragment.newInstance(crime.getmId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }
    @Override
    public void onCrimeDelete(Crime crime) {
        int position = 0;
        List<Crime> mCrimes = CrimeLab.get(this).getmCrimes();
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getmId().equals(crime.getmId())) {
                position = i;
                break;
            }
        }
        CrimeLab.get(this).deleteCrime(crime);
        mCrimes.remove(position);
        position = position % mCrimes.size();
        if (findViewById(R.id.detail_fragment_container) != null) {
            Fragment newDetail = CrimeFragment.newInstance(mCrimes.get(position).getmId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
            onCrimeUpdated(mCrimes.get(position));
        }
    }
    //----------------------------------------------------------------------------------------------

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
        Log.d(TAG, "onPause()");
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



    //  двойной клик для выхода из приложения
    private static long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}
