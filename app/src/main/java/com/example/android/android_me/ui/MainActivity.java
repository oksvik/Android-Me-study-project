package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

/**
 * Created by dudar on 02.05.2017.
 */

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener{

    // values for the list index of the selected images
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    static final int HEAD_PART = 0;
    static final int BODY_PART = 1;
    static final int LEG_PART = 2;
    static final int NUMBER_OF_IMAGES_OF_EACH_TYPE = 12;

    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Determine if creating a two-pane or single-pane display
        if (findViewById(R.id.android_me_linear_layout) != null){
            //This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            //Getting rid of the "Next" button that appears on phones for launching a second activity
            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);

            //Change the GridView to space out the images more on tablet
            GridView gridView = (GridView) findViewById(R.id.master_list_gridview);
            gridView.setNumColumns(2);

            //Create new body part fragment
            if (savedInstanceState == null) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                HeadPartFragment headFragment = new HeadPartFragment();
                headFragment.setImageIds(AndroidImageAssets.getHeads());
                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());
                 fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                LegsPartFragment legsFragment = new LegsPartFragment();
                legsFragment.setImageIds(AndroidImageAssets.getLegs());
                fragmentManager.beginTransaction()
                        .add(R.id.legs_container, legsFragment)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onImageSelected(int position) {
        Toast.makeText(this,"Position clicked = " + position, Toast.LENGTH_SHORT).show();

        //wiil be = 0 for the head fragment, 1 for the body fragment, and 2 for the leg fragment
        int bodyPartType = position/NUMBER_OF_IMAGES_OF_EACH_TYPE;

        //Store the correct list index no matter where in the image list had been clicked
        //the index will always be a value between 0-11
        int listIndex =  position - NUMBER_OF_IMAGES_OF_EACH_TYPE*bodyPartType;

        if (mTwoPane){
            //handle twoPane case

            BodyPartFragment newFragment = new BodyPartFragment();

            switch (bodyPartType){
                case HEAD_PART:
                    newFragment.setImageIds(AndroidImageAssets.getHeads());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container,newFragment)
                            .commit();
                    break;
                case BODY_PART:
                    newFragment.setImageIds(AndroidImageAssets.getBodies());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container,newFragment)
                            .commit();
                    break;
                case LEG_PART:
                    newFragment.setImageIds(AndroidImageAssets.getLegs());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.legs_container,newFragment)
                            .commit();
                default:
                    break;
            }

        }else {
            switch (bodyPartType) {
                case HEAD_PART:
                    headIndex = listIndex;
                    break;
                case BODY_PART:
                    bodyIndex = listIndex;
                    break;
                case LEG_PART:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }
        }

        //Put this information in a Bundle and attach it to an Intent
        Bundle bundle= new Bundle();
        bundle.putInt("headIndex",headIndex);
        bundle.putInt("bodyIndex",bodyIndex);
        bundle.putInt("legIndex",legIndex);

        final Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(bundle);

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}
