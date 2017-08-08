
package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class AndroidMeActivity extends AppCompatActivity {

    private static final int DEFAULT_INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        if (savedInstanceState == null) {

        HeadPartFragment headFragment = new HeadPartFragment();
        headFragment.setImageIds(AndroidImageAssets.getHeads());
        int headIndex = getIntent().getIntExtra("headIndex", DEFAULT_INDEX);
        headFragment.setListIndex(headIndex);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.head_container, headFragment)
                .commit();

        BodyPartFragment bodyFragment = new BodyPartFragment();
        bodyFragment.setImageIds(AndroidImageAssets.getBodies());
        int bodyIndex = getIntent().getIntExtra("bodyIndex",DEFAULT_INDEX);
        bodyFragment.setListIndex(bodyIndex);

        fragmentManager.beginTransaction()
                .add(R.id.body_container, bodyFragment)
                .commit();

        LegsPartFragment legsFragment = new LegsPartFragment();
        legsFragment.setImageIds(AndroidImageAssets.getLegs());
        int legIndex = getIntent().getIntExtra("legIndex",DEFAULT_INDEX);
        legsFragment.setListIndex(legIndex);

        fragmentManager.beginTransaction()
                .add(R.id.legs_container, legsFragment)
                .commit();
    }

}
}
