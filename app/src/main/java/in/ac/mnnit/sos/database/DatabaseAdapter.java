package in.ac.mnnit.sos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import in.ac.mnnit.sos.database.entity.DataType;
import in.ac.mnnit.sos.database.entity.EcontactAddress;
import in.ac.mnnit.sos.database.entity.EcontactEmail;
import in.ac.mnnit.sos.database.entity.EcontactPhone;
import in.ac.mnnit.sos.database.entity.EmergencyContact;

/**
 * Created by prashanth on 1/3/17.
 */

public class DatabaseAdapter {

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Context context;

    public DatabaseAdapter(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
        this.db = databaseHelper.getWritableDatabase();
        this.context = context;
    }

    public void insertEmergencyContact(EmergencyContact eContact, List<EcontactPhone> econtactPhone){
        insertEmergencyContact(eContact, econtactPhone, null, null);
    }

    public long insertEmergencyContact(EmergencyContact eContact, List<EcontactPhone> econtactPhone, List<EcontactEmail> econtactEmail, EcontactAddress[] econtactAddress){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ECONTACT_NAME, eContact.getName());

        long CONTACT_ID = db.insert(DatabaseHelper.EMERGENCY_CONTACT_TABLE, null, contentValues);

        if(econtactPhone != null) {
            for (EcontactPhone ecPhone : econtactPhone) {
                String insertPhoneQuery = "INSERT INTO "
                        + DatabaseHelper.PHONE_TABLE + "("
                        + DatabaseHelper.PHONE_CONTACT_ID + ", "
                        + DatabaseHelper.PHONE + ", "
                        + DatabaseHelper.PHONE_TYPE_ID + ") "
                        + "SELECT \"" + CONTACT_ID + "\", \"" + ecPhone.getPhoneNumber() + "\", "
                        + DatabaseHelper.DATATYPE_ID+" FROM "+DatabaseHelper.DATATYPE_TABLE+" WHERE "
                        + DatabaseHelper.DATA_TYPE+" IS \""+ecPhone.getType()+"\";";

                db.rawQuery(insertPhoneQuery, new String[]{});
            }
        }

        if(econtactEmail != null) {
            for (EcontactEmail ecEmail : econtactEmail) {
                String insertEmailQuery = "INSERT INTO "
                        + DatabaseHelper.EMAIL_TABLE + "("
                        + DatabaseHelper.EMAIL_CONTACT_ID + ", "
                        + DatabaseHelper.EMAIL + ", "
                        + DatabaseHelper.EMAIL_TYPE_ID + ") "
                        + "SELECT \"" + CONTACT_ID + "\", \"" + ecEmail.getEmailID() + "\", "
                        + DatabaseHelper.DATATYPE_ID+" FROM "+DatabaseHelper.DATATYPE_TABLE+" WHERE "
                        + DatabaseHelper.DATA_TYPE+" IS \""+ecEmail.getType()+"\";";

                db.rawQuery(insertEmailQuery, new String[]{});
            }
        }

        if(econtactAddress != null) {
            for (EcontactAddress ecAddress : econtactAddress) {
                String insertAddressQuery = "INSERT INTO "
                        + DatabaseHelper.ADDRESS_TABLE + "("
                        + DatabaseHelper.ADDRESS_CONTACT_ID + ", "
                        + DatabaseHelper.ADDRESS + ", "
                        + DatabaseHelper.ADDRESS_TYPE_ID + ") "
                        + "SELECT \"" + CONTACT_ID + "\", \"" + ecAddress.getAddress() + "\", "
                        + DatabaseHelper.DATATYPE_ID+" FROM "+DatabaseHelper.DATATYPE_TABLE+" WHERE "
                        + DatabaseHelper.DATA_TYPE+" IS \""+ecAddress.getType()+"\";";

                db.rawQuery(insertAddressQuery, new String[]{});
            }
        }

//        if(econtactPhone != null) {
//            for (EcontactPhone ecPhone : econtactPhone) {
//                contentValues.clear();
//                contentValues.put(DatabaseHelper.PHONE_CONTACT_ID, CONTACT_ID);
//                contentValues.put(DatabaseHelper.PHONE, ecPhone.getPhoneNumber());
//                contentValues.put(DatabaseHelper.PHONE_TYPE_ID, ecPhone.getTypeID());
//
//                db.insert(DatabaseHelper.PHONE_TABLE, null, contentValues);
//            }
//        }
//
//        if(econtactEmail != null){
//            for(EcontactEmail ecEmail: econtactEmail){
//                contentValues.clear();
//                contentValues.put(DatabaseHelper.EMAIL_CONTACT_ID, CONTACT_ID);
//                contentValues.put(DatabaseHelper.EMAIL, ecEmail.getEmailID());
//                contentValues.put(DatabaseHelper.EMAIL_TYPE_ID, ecEmail.getTypeID());
//
//                db.insert(DatabaseHelper.EMAIL_TABLE, null, contentValues);
//            }
//        }
//
//        if(econtactAddress != null){
//            for(EcontactAddress ecAddress: econtactAddress){
//                contentValues.clear();
//                contentValues.put(DatabaseHelper.ADDRESS_CONTACT_ID, CONTACT_ID);
//                contentValues.put(DatabaseHelper.ADDRESS, ecAddress.getAddress());
//                contentValues.put(DatabaseHelper.ADDRESS_TYPE_ID, ecAddress.getTypeID());
//
//                db.insert(DatabaseHelper.ADDRESS_TABLE, null, contentValues);
//            }
//        }

        return CONTACT_ID;
    }

    public String getAllEmergencyContacts(){
        Cursor c = db.query(DatabaseHelper.EMERGENCY_CONTACT_TABLE, new String[]{DatabaseHelper.ECONTACT_ID, DatabaseHelper.ECONTACT_NAME}, null, null, null, null, null);
        String result = "";
        while (c.moveToNext()){
            result += c.getInt(c.getColumnIndex(DatabaseHelper.ECONTACT_ID))+" ";
            result += c.getString(c.getColumnIndex(DatabaseHelper.ECONTACT_NAME))+"\n";
        }
        return result;
    }

    public void deleteDatabase(){
//        db.rawQuery("DROP DATABASE "+DatabaseHelper.DATABASE_NAME+";", null);
        context.deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "sos";
        private static final int DATABASE_VERSION = 1;

        private static final String EMERGENCY_CONTACT_TABLE = "emergency_contact";
        private static final String PHONE_TABLE = "econtact_phone";
        private static final String EMAIL_TABLE = "econtact_email";
        private static final String ADDRESS_TABLE = "econtact_address";
        private static final String DATATYPE_TABLE = "data_type";

        // EMERGENCY CONTACT TABLE COLUMNS
        private static final String ECONTACT_ID = "_id";
        private static final String ECONTACT_NAME = "name";
        private static final String ECONTACT_PHOTO = "photo";

        //PHONE TABLE COLUMNS
        private static final String PHONE_ID = "_pid";
        private static final String PHONE_CONTACT_ID = "_cid";
        private static final String PHONE = "number";
        private static final String PHONE_TYPE_ID = "phone_type_id";

        //EMAIL TABLE COLUMNS
        private static final String EMAIL_ID = "_eid";
        private static final String EMAIL_CONTACT_ID = "_cid";
        private static final String EMAIL = "email";
        private static final String EMAIL_TYPE_ID = "email_type_id";

        //ADDRESS TABLE COLUMNS
        private static final String ADDRESS_ID = "_aid";
        private static final String ADDRESS_CONTACT_ID = "_cid";
        private static final String ADDRESS = "address";
        private static final String ADDRESS_TYPE_ID = "address_type_id";

        //DATATYPE TABLE COLUMNS
        private static final String DATATYPE_ID = "_tid";
        private static final String DATA_TYPE = "type";


        //CREATE EMERGENCY CONTACT TABLE
        private static final String CREATE_TABLE_ECONTACT = "CREATE TABLE "
                + EMERGENCY_CONTACT_TABLE + " ("
                + ECONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ECONTACT_NAME + " VARCHAR(255), "
                + ECONTACT_PHOTO +" BLOB);";


        //CREATE PHONE TABLE
        private static final String CREATE_TABLE_PHONE = "CREATE TABLE "
                + PHONE_TABLE + " ("
                + PHONE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PHONE_CONTACT_ID + " INTEGER, "
                + PHONE + " VARCHAR(255), "
                + PHONE_TYPE_ID + " INTEGER, "
                + "FOREIGN KEY (" + PHONE_TYPE_ID + ") REFERENCES " + DATATYPE_TABLE + "(" + DATATYPE_ID + "), "
                + "FOREIGN KEY (" + PHONE_CONTACT_ID + ") REFERENCES " + EMERGENCY_CONTACT_TABLE + "(" + ECONTACT_ID + "));";


        //CREATE EMAIL TABLE
        private static final String CREATE_TABLE_EMAIL = "CREATE TABLE "
                + EMAIL_TABLE + " ("
                + EMAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_CONTACT_ID + " INTEGER, "
                + EMAIL + " VARCHAR(255), "
                + EMAIL_TYPE_ID + " INTEGER, "
                + "FOREIGN KEY (" + EMAIL_TYPE_ID + ") REFERENCES " + DATATYPE_TABLE + "(" + DATATYPE_ID + "), "
                + "FOREIGN KEY (" + EMAIL_CONTACT_ID + ") REFERENCES " + EMERGENCY_CONTACT_TABLE + "(" + ECONTACT_ID + "));";

        //CREATE ADDRESS TABLE
        private static final String CREATE_TABLE_ADDRESS = "CREATE TABLE "
                + ADDRESS_TABLE + " ("
                + ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ADDRESS_CONTACT_ID + " INTEGER, "
                + ADDRESS + " VARCHAR(255), "
                + ADDRESS_TYPE_ID + " INTEGER, "
                + "FOREIGN KEY (" + ADDRESS_TYPE_ID + ") REFERENCES " + DATATYPE_TABLE + "(" + DATATYPE_ID + "), "
                + "FOREIGN KEY (" + ADDRESS_CONTACT_ID + ") REFERENCES " + EMERGENCY_CONTACT_TABLE + "(" + ECONTACT_ID + "));";


        //CREATE DATATYPE TABLE
        private static final String CREATE_TABLE_DATATYPE = "CREATE TABLE "
                + DATATYPE_TABLE + " ("
                + DATATYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATA_TYPE + " VARCHAR(255))";


        private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";


        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("PRAGMA foreign_keys=ON");
            db.execSQL(CREATE_TABLE_ECONTACT);
            db.execSQL(CREATE_TABLE_DATATYPE);
            db.execSQL(CREATE_TABLE_PHONE);
            db.execSQL(CREATE_TABLE_EMAIL);
            db.execSQL(CREATE_TABLE_ADDRESS);
            populateDatatypeTable(db);

            Log.e("TAG", "OnCreate Finished in DB");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE_IF_EXISTS + EMAIL_TABLE);
            db.execSQL(DROP_TABLE_IF_EXISTS + ADDRESS_TABLE);
            db.execSQL(DROP_TABLE_IF_EXISTS + PHONE_TABLE);
            db.execSQL(DROP_TABLE_IF_EXISTS + DATATYPE_TABLE);
            db.execSQL(DROP_TABLE_IF_EXISTS + EMERGENCY_CONTACT_TABLE);

            onCreate(db);
        }

        private long populateDatatypeTable(SQLiteDatabase db){
            ContentValues contentValues = new ContentValues();
            for(String type: DataType.type)
                contentValues.put(DatabaseHelper.DATA_TYPE, type);

            return db.insert(DatabaseHelper.DATATYPE_TABLE, null, contentValues);
        }
    }
}