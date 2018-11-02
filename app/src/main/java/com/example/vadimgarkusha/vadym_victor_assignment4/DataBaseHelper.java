package com.example.vadimgarkusha.vadym_victor_assignment4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.net.ConnectException;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "RECRUITMENT_AGENCY";
    public static final String CANDIDATE_TABLE = "CANDIDATE";
    public static final String[] CANDIDATE_COLUMNS = new String[] {
            "userName", "password", "firstName", "lastName", "address", "city",
            "postalCode", "qualification", "experience"
    };
    public static final String[] ADMIN_COLUMNS = new String[] {
            "userName", "password", "firstName", "lastName"
    };
    public static final String[] JOB_OFFER_COLUMNS = new String[] {
            "interviewDate", "interviewStatus", "companyName", "positionName", "recruitmentStatus"
    };
    public static final String[] PAYMENT_COLUMNS = new String[] {
            "candidateID", "paymentDate", "amountPaid", "creditCardNo", "expiryDate"
    };

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + CANDIDATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS ADMIN");
        db.execSQL("DROP TABLE IF EXISTS JOB_OFFER");
        db.execSQL("DROP TABLE IF EXISTS PAYMENT");

        String createCandidateTable = "CREATE TABLE CANDIDATE ( " +
                "candidateID INTEGER NOT NULL UNIQUE, " +
                "userName INTEGER NOT NULL UNIQUE, " +
                "password TEXT NOT NULL, " +
                "firstName TEXT NOT NULL, " +
                "lastName TEXT NOT NULL, " +
                "address TEXT DEFAULT 'N/A', " +
                "city TEXT DEFAULT 'N/A', " +
                "postalCode TEXT DEFAULT 'N/A', " +
                "qualification TEXT NOT NULL, " +
                "experience TEXT NOT NULL, " +
                "PRIMARY KEY(candidateID) );";

        String createAdminTable = "CREATE TABLE `ADMIN` ( " +
                "`employeeId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "`userName` TEXT NOT NULL UNIQUE, " +
                "`password` TEXT NOT NULL, " +
                "`firstName` TEXT NOT NULL, " +
                "`lastName` TEXT NOT NULL )";

        String createJobOfferTable = "CREATE TABLE `JOB_OFFER` ( " +
                "`interviewDate` DATE NOT NULL, " +
                "`interviewStatus` TEXT NOT NULL, " +
                "`companyName` TEXT NOT NULL, " +
                "`positionName` TEXT NOT NULL, " +
                "`recruitmentStatus` TEXT NOT NULL, " +
                "FOREIGN KEY(`employeeId`) REFERENCES `ADMIN`(`employeeId`), " +
                "FOREIGN KEY(`candidateId`) REFERENCES `CANDIDATE`(`candidateID`) )";

        String createPaymentTable = "CREATE TABLE `PAYMENT` ( " +
                "`candidateId` INTEGER NOT NULL, " +
                "`paymentDate` DATE DEFAULT NULL, " +
                "`amountPaid` INTEGER DEFAULT NULL, " +
                "`creditCardNo` INTEGER DEFAULT NULL, " +
                "`expiryDate` TEXT DEFAULT NULL, " +
                "FOREIGN KEY(`candidateId`) REFERENCES `CANDIDATE`(`candidateId`), " +
                "PRIMARY KEY(`candidateId`) )";

        db.execSQL(createCandidateTable);
        db.execSQL(createAdminTable);
        db.execSQL(createJobOfferTable);
        db.execSQL(createPaymentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CANDIDATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS ADMIN");
        db.execSQL("DROP TABLE IF EXISTS JOB_OFFER");
        db.execSQL("DROP TABLE IF EXISTS PAYMENT");
        onCreate(db);
    }
    public boolean createJobSeekerProfile(
            String userName, String password, String firstName, String lastName, String address,
            String city, String postalCode, String qualification, String experience, String creditCardNo,
            String expiryDate
            ) {
        SQLiteDatabase writeDB = this.getWritableDatabase();
        SQLiteDatabase readDB = this.getReadableDatabase();

        ContentValues JobSeekerTableValues = new ContentValues();
        ContentValues PaymentTableValues = new ContentValues();

        JobSeekerTableValues.put(CANDIDATE_COLUMNS[0], userName);
        JobSeekerTableValues.put(CANDIDATE_COLUMNS[1], password);
        JobSeekerTableValues.put(CANDIDATE_COLUMNS[2], firstName);
        JobSeekerTableValues.put(CANDIDATE_COLUMNS[3], lastName);
        if (!address.isEmpty()) JobSeekerTableValues.put(CANDIDATE_COLUMNS[4], address);
        if (!city.isEmpty()) JobSeekerTableValues.put(CANDIDATE_COLUMNS[5], city);
        if (!postalCode.isEmpty()) JobSeekerTableValues.put(CANDIDATE_COLUMNS[6], postalCode);
        if (!qualification.isEmpty()) JobSeekerTableValues.put(CANDIDATE_COLUMNS[7], qualification);
        if (!experience.isEmpty()) JobSeekerTableValues.put(CANDIDATE_COLUMNS[8], experience);

        long candidateID = writeDB.insert("CANDIDATE", null, JobSeekerTableValues);

        PaymentTableValues.put(PAYMENT_COLUMNS[0], candidateID);
        if (!creditCardNo.isEmpty()) PaymentTableValues.put(PAYMENT_COLUMNS[3], creditCardNo);
        if (!expiryDate.isEmpty()) PaymentTableValues.put(PAYMENT_COLUMNS[4], expiryDate);

        long ex = writeDB.insert("PAYMENT", null, PaymentTableValues);
        return ex != -1;
    }

    public Cursor loginAsJobSeeker(String js, String ps) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT candidateId FROM CANDIDATE WHERE userName = ? AND password = ?", new String[]{ js, ps });
        Log.i("RES CURSOR" ,res.toString());
        return res;
    }

    public Cursor loginAsAdmin(String js, String ps) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT employeeId FROM ADMIN WHERE userName = ? AND password = ?", new String[]{ js, ps });
        Log.i("RES CURSOR" , res.toString());
        return res;
    }

    public String[] getUserData(String id, boolean isAdmin) {
        SQLiteDatabase db = this.getWritableDatabase();
        String table_name = isAdmin ? "ADMIN" : "CANDIDATE";
        String PK = isAdmin ? "eployeeId" : "candidateId";
        Cursor userGenInfo = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + PK + " = ?", new String[]{id});
        String[] userData = new String[]{};

        if (!isAdmin) {
            while (userGenInfo.moveToNext()) {
                userData[0] = userGenInfo.getString(2);
                userData[1] = userGenInfo.getString(3);
                userData[2] = userGenInfo.getString(4);
                userData[3] = userGenInfo.getString(5);
                userData[4] = userGenInfo.getString(6);
            }
//            Cursor userPaymentInfo = db.rawQuery("SELECT * FROM PAYMENT WHERE candidateId = ?", new String[]{id});
        }

        return userData;
      }
}
