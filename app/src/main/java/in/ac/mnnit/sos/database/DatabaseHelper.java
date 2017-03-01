package in.ac.mnnit.sos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prashanth on 1/3/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sos";
    private static final int DATABASE_VERSION = 1;

    private static final String USER_TABLE = "user";
    private static final String PHONE_TABLE = "user_phone";
    private static final String EMAIL_TABLE = "user_email";
    private static final String ADDRESS_TABLE = "user_address";
    private static final String TYPE_TABLE = "data_type";

    // USER TABLE COLUMNS
    private  static final String USER_ID = "_uid";
    private  static final String USER_NAME = "name";

    //PHONE TABLE COLUMNS
    private static final String PHONE_ID = "_pid";
    private static final String PHONE = "number";
    private  static final String PHONE_TYPE_ID = "phone_type";

    //EMAIL TABLE COLUMNS
    private static final String EMAIL_ID = "_eid";
    private static final String EMAIL = "email";
    private  static final String EMAIL_TYPE_ID = "email_type";

    //ADDRESS TABLE COLUMNS
    private static final String ADDRESS_ID = "_aid";
    private static final String ADDRESS = "address";
    private  static final String ADDRESS_TYPE_ID = "address_type";

    //DATA TYPE TABLE COLUMNS
    private static final String DATATYPE_ID = "_tid";
    private static final String DATA_TYPE = "type";


    //CREATE USER TABLE
    private static final String CREATE_TABLE_USER = "CREATE TABLE "+USER_TABLE+
            " ("+USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_NAME+" VARCHAR(255) )";


    //CREATE PHONE TABLE
    private static final String CREATE_TABLE_PHONE = "CREATE TABLE "+PHONE_TABLE+
            " ("+PHONE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+PHONE+" VARCHAR(255), "+PHONE_TYPE_ID+" INTEGER)";

    //CREATE EMAIL TABLE
    private static final String CREATE_TABLE_EMAIL = "CREATE TABLE "+EMAIL_TABLE+
            " ("+EMAIL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+EMAIL+" VARCHAR(255), "+EMAIL_TYPE_ID+" INTEGER )";

    //CREATE ADDRESS TABLE
    private static final String CREATE_TABLE_ADDRESS = "CREATE TABLE "+ADDRESS_TABLE+
            " ("+ADDRESS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ADDRESS+" VARCHAR(255), "+ADDRESS_TYPE_ID+" INTEGER )";

    //CREATE DATATYPE TABLE
    private static final String CREATE_TABLE_DATATYPE = "CREATE TABLE "+TYPE_TABLE+
            " ("+DATATYPE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DATA_TYPE+" VARCHAR(255) )";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
