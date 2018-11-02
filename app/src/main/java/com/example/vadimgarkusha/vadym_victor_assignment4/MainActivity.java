package com.example.vadimgarkusha.vadym_victor_assignment4;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public DataBaseHelper db;
    private Intent intent;
    private DialogBuilder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBaseHelper(this);
        dialog = new DialogBuilder(this);
    }

    public void goRegisterJobSeeker(View view){
                Intent intent = new Intent(this, JobSeekerRegisterActivity.class);
                startActivity(intent);
    }

    public void goJobSeekerMenu(View view){
        EditText login = findViewById(R.id.login_field);
        EditText password = findViewById(R.id.password_field);
        String loginText = login.getText().toString();
        String passwordText = password.getText().toString();
        Cursor res = db.loginAsJobSeeker(loginText, passwordText);
        Log.i("CURSOR", Integer.toString(res.getCount()));
        Integer id = null;
//        if (res.moveToFirst()) {
//            id = res.getInt(res.getColumnIndex("candidateId"));
//            Log.i("FDFSDFSDFSDF", Integer.toString(id));
//        }
        intent = new Intent(this, JobSeekerMenuActivity.class);
        intent.putExtra("isAdmin", false);
//        intent.putExtra("id", id);
        if (res.getCount() == 0) {
            dialog.showMessage("Error!", "The login or password is invalid!");
        } else {
            dialog.showMessage("Success!", "You logged as a Job Seeker!");
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            startActivity(intent);
                        }
                    }, 3000);
        }
    }

    public void goAdminMenu(View view){
        EditText login = findViewById(R.id.login_field);
        EditText password = findViewById(R.id.password_field);
        String loginText = login.getText().toString();
        String passwordText = password.getText().toString();
        Cursor res = db.loginAsJobSeeker(loginText, passwordText);
        Integer id = null;
        if (res.moveToFirst()) {
            id = res.getInt(res.getColumnIndex("employeeId"));
        }
        intent = new Intent(this, AdminMenuActivity.class);
        intent.putExtra("isAdmin", true);
        intent.putExtra("id", id);
        if (res.getCount() == 0) {
            dialog.showMessage("Error!", "The login or password is invalid!");
        } else {
            dialog.showMessage("Success!", "You logged in as Admin!");
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            startActivity(intent);
                        }
                    }, 3000);
        }
    }
}
