package com.example.vadimgarkusha.vadym_victor_assignment4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class JobSeekerRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_register);
    }

    public void goJobSeekerMenu(View view){
        Intent intent = new Intent(this, JobSeekerMenuActivity.class);
        startActivity(intent);
    }
}
