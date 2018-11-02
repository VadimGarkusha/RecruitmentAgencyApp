package com.example.vadimgarkusha.vadym_victor_assignment4;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class ViewJobSeekerInfo extends AppCompatActivity {
    DataBaseHelper db;
    private EditText etUserName, etPassword, etFirstName, etLastName,
            etAddress, etCity, etPostalCode, etQualification, etExperience,
            etCardNumber, etExpirtyDate;
    private String userName, password, firstName, lastName,
            address, city, postalCode, qualification, experience,
            cardNumber, expirtyDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job_seeker_info);
        db = new DataBaseHelper(this);
        getData();
    }

    public void getData() {
        String id = getIntent().getStringExtra("id");
        boolean isAdmin = Boolean.parseBoolean(getIntent().getStringExtra("isAdmin"));
        String[] data = db.getUserData(id, isAdmin);
        for (String d : data) {
            Log.i("res", d);
        }
    }

    public void setData() {
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

        etUserName.setText("dsf");
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
}
