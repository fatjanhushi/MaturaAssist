package com.fatjoni.droid.maturaassist;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class ResultActivity extends AppCompatActivity {
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.INTENT_MESSAGE);

        TextView result = (TextView) findViewById(R.id.tv_result);
        result.setText(message);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1837256325017145/6718415019");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                NavUtils.navigateUpFromSameTask(ResultActivity.this);
            }
        });

        requestNewInterstitial();

       final AdView mAdView2 = (AdView) findViewById(R.id.adView2);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AdRequest adRequest = new AdRequest.Builder().addTestDevice(MainActivity.DEVICE_ID).build();
                mAdView2.loadAd(adRequest);
                mAdView2.bringToFront();
            }
        }, 500);


    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            super.onBackPressed();
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .addTestDevice(MainActivity.DEVICE_ID)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}
