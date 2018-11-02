package com.example.vadimgarkusha.vadym_victor_assignment4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class JobSeekerRegisterActivity extends AppCompatActivity {
    DataBaseHelper db;
    private Intent intent;
    DialogBuilder dialog;
    AlertDialog.Builder builder;
    private EditText etUserName, etPassword, etFirstName, etLastName,
            etAddress, etCity, etPostalCode, etQualification, etExperience,
            etCardNumber, etExpirtyDate;
    private String userName, password, firstName, lastName,
      address, city, postalCode, qualification, experience,
      cardNumber, expirtyDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_register);
        db = new DataBaseHelper(this);
        dialog = new DialogBuilder(this);
    }

    public void getData() {
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etPostalCode = findViewById(R.id.etPostal);
        etQualification = findViewById(R.id.etQualification);
        etExperience = findViewById(R.id.etExperience);
        etCardNumber = findViewById(R.id.etCardNumber);
        etExpirtyDate = findViewById(R.id.etExprirationDate);

        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        address = etAddress.getText().toString();
        city = etCity.getText().toString();
        postalCode = etPostalCode.getText().toString();
        qualification = etQualification.getText().toString();
        experience = etExperience.getText().toString();
        cardNumber = etCardNumber.getText().toString();
        expirtyDate = etExpirtyDate.getText().toString();
    }

    public boolean validateInfo() {
        boolean isInvalid = userName.isEmpty() || lastName.isEmpty()
                || password.isEmpty() || qualification.isEmpty() ||
                experience.isEmpty();
        if (isInvalid) {
            if (userName.isEmpty()) etUserName.setError("Required");
            if (lastName.isEmpty()) etLastName.setError("Required");
            if (password.isEmpty()) etPassword.setError("Required");
            if (qualification.isEmpty()) etQualification.setError("Required");
            if (experience.isEmpty()) etExperience.setError("Required");
        }
        return isInvalid;
    }

    public void registerUser(View view) {
        this.getData();
        builder = new AlertDialog.Builder(this);
        intent = new Intent(this, MainActivity.class);
        boolean isInvalid = validateInfo();
        if (!isInvalid) {
            boolean isInserted = db.createJobSeekerProfile(
                    userName, password, firstName, lastName, address, city, postalCode,
                    qualification, experience, cardNumber, expirtyDate
            );

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                          startActivity(intent);
                        }
                    }, 3000);

            if (isInserted) {
                dialog.showMessage("Success!", "You were registered!");
            } else {
                dialog.showMessage("Error!", "The error was occurred, try again");
            }
        }
    }
}
