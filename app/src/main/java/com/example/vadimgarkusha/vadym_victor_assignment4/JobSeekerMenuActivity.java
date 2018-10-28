package com.example.vadimgarkusha.vadym_victor_assignment4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class JobSeekerMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_menu);
    }

    public void goJobOffer(View view){
        Intent intent = new Intent(this, JobOffersListActivity.class);
        startActivity(intent);
    }

    public void goJobSeekerInfo(View view){
        Intent intent = new Intent(this, ViewJobSeekerInfo.class);
        startActivity(intent);
    }
}
