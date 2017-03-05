package in.ac.mnnit.sos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import in.ac.mnnit.sos.database.entity.DataType;
import in.ac.mnnit.sos.database.entity.EcontactAddress;
import in.ac.mnnit.sos.database.entity.EcontactEmail;
import in.ac.mnnit.sos.database.entity.EcontactPhone;
import in.ac.mnnit.sos.database.entity.EmergencyContact;
import in.ac.mnnit.sos.models.Address;
import in.ac.mnnit.sos.models.Contact;
import in.ac.mnnit.sos.models.Email;
import in.ac.mnnit.sos.models.Phone;

/**
 * Created by prashanth on 1/3/17.
 */

public class LocalDatabaseAdapter {

    private SQLiteDatabase db;
    private Context context;
    public static OnDatabaseChangeListener contactsViewAdapter;

    public LocalDatabaseAdapter(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.db = databaseHelper.getWritableDatabase();
        this.context = context;
    }

    public void insertEmergencyContact(EmergencyContact eContact, List<EcontactPhone> econtactPhone){
        insertEmergencyContact(eContact, econtactPhone, null, null);
    }

    public void insertEmergencyContact(EmergencyContact eContact, List<EcontactPhone> econtactPhone, List<EcontactEmail> econtactEmail){
        insertEmergencyContact(eContact, econtactPhone, econtactEmail, null);
    }

    public long insertEmergencyContact(EmergencyContact eContact, List<EcontactPhone> econtactPhone, List<EcontactEmail> econtactEmail, List<EcontactAddress> econtactAddress){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ECONTACT_NAME, eContact.getName());
        contentValues.put(DatabaseHelper.ECONTACT_PHOTO, eContact.getPhotoBytes());

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

                db.execSQL(insertPhoneQuery);
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

                db.execSQL(insertEmailQuery);
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
                        + DatabaseHelper.DATATYPE_ID + " FROM " + DatabaseHelper.DATATYPE_TABLE + " WHERE "
                        + DatabaseHelper.DATA_TYPE + " IS \"" + ecAddress.getType() + "\";";

                db.execSQL(insertAddressQuery);
            }
        }
        if(contactsViewAdapter != null)
            contactsViewAdapter.onContactAdd(getAllEmergencyContacts());

        return CONTACT_ID;
    }

    public List<Contact> getAllEmergencyContacts(){
        List<Contact> contacts = new ArrayList<>();
        Cursor contactCursor = db.query(DatabaseHelper.EMERGENCY_CONTACT_TABLE, new String[]{DatabaseHelper.ECONTACT_ID, DatabaseHelper.ECONTACT_NAME, DatabaseHelper.ECONTACT_PHOTO}, null, null, null, null, null);

        int ID_INDEX = contactCursor.getColumnIndex(DatabaseHelper.ECONTACT_ID);
        int NAME_INDEX = contactCursor.getColumnIndex(DatabaseHelper.ECONTACT_NAME);
        int PHOTO_INDEX = contactCursor.getColumnIndex(DatabaseHelper.ECONTACT_PHOTO);

        while (contactCursor.moveToNext()){
            Contact contact = new Contact();

            int contactID = contactCursor.getInt(ID_INDEX);
            contact.setId(contactID);
            contact.setName(contactCursor.getString(NAME_INDEX));
            contact.setHighResPhoto(contactCursor.getBlob(PHOTO_INDEX));
            contact.setPhones(getPhoneListByContactID(contactID));
            contact.setEmails(getEmailListByContactID(contactID));
            contact.setAddresses(getAddressListByContactID(contactID));

            contacts.add(contact);
        }

        contactCursor.close();
        return contacts;
    }

    public List<Phone> getPhoneListByContactID(int contactID){
        List<Phone> phones = new ArrayList<>();
        String selectPhoneQuery = "SELECT "
                +DatabaseHelper.PHONE_TABLE+"."+DatabaseHelper.PHONE+", "
                +DatabaseHelper.DATATYPE_TABLE+"."+DatabaseHelper.DATA_TYPE+" "
                +"FROM "
                +DatabaseHelper.PHONE_TABLE+" LEFT JOIN "+DatabaseHelper.DATATYPE_TABLE+" "
                +"ON "
                +DatabaseHelper.PHONE_TABLE+"."+DatabaseHelper.PHONE_TYPE_ID+" = "
                +DatabaseHelper.DATATYPE_TABLE+"."+DatabaseHelper.DATATYPE_ID+" "
                +"WHERE "
                +DatabaseHelper.PHONE_TABLE+"."+DatabaseHelper.PHONE_CONTACT_ID+" = ?;";

        Cursor phoneCursor = db.rawQuery(selectPhoneQuery, new String[]{String.valueOf(contactID)});

        int NUMBER_INDEX = phoneCursor.getColumnIndex(DatabaseHelper.PHONE);
        int DATATYPE_INDEX = phoneCursor.getColumnIndex(DatabaseHelper.DATA_TYPE);

        while(phoneCursor.moveToNext()){
            Phone phone = new Phone();
            phone.setNumber(phoneCursor.getString(NUMBER_INDEX));
            phone.setType(phoneCursor.getString(DATATYPE_INDEX));
            phones.add(phone);
        }

        phoneCursor.close();
        return phones;
    }

    public List<Email> getEmailListByContactID(int contactID){
        List<Email> emails = new ArrayList<>();
        String selectEmailQuery = "SELECT "
                +DatabaseHelper.EMAIL_TABLE+"."+DatabaseHelper.EMAIL+", "
                +DatabaseHelper.DATATYPE_TABLE+"."+DatabaseHelper.DATA_TYPE+" "
                +"FROM "
                +DatabaseHelper.EMAIL_TABLE+" LEFT JOIN "+DatabaseHelper.DATATYPE_TABLE+" "
                +"ON "
                +DatabaseHelper.EMAIL_TABLE+"."+DatabaseHelper.EMAIL_TYPE_ID+" = "
                +DatabaseHelper.DATATYPE_TABLE+"."+DatabaseHelper.DATATYPE_ID+" "
                +"WHERE "
                +DatabaseHelper.EMAIL_TABLE+"."+DatabaseHelper.EMAIL_CONTACT_ID+" = ?;";

        Cursor emailCursor = db.rawQuery(selectEmailQuery, new String[]{String.valueOf(contactID)});

        int EMAIL_INDEX = emailCursor.getColumnIndex(DatabaseHelper.EMAIL);
        int DATATYPE_INDEX = emailCursor.getColumnIndex(DatabaseHelper.DATA_TYPE);

        while(emailCursor.moveToNext()){
            Email email = new Email();
            email.setEmailID(emailCursor.getString(EMAIL_INDEX));
            email.setType(emailCursor.getString(DATATYPE_INDEX));
            emails.add(email);
        }

        emailCursor.close();
        return emails;
    }

    public List<Address> getAddressListByContactID(int contactID){
        List<Address> addresses = new ArrayList<>();
        String selectAddressQuery = "SELECT "
                +DatabaseHelper.ADDRESS_TABLE+"."+DatabaseHelper.ADDRESS+", "
                +DatabaseHelper.DATATYPE_TABLE+"."+DatabaseHelper.DATA_TYPE+" "
                +"FROM "
                +DatabaseHelper.ADDRESS_TABLE+" LEFT JOIN "+DatabaseHelper.DATATYPE_TABLE+" "
                +"ON "
                +DatabaseHelper.ADDRESS_TABLE+"."+DatabaseHelper.ADDRESS_TYPE_ID+" = "
                +DatabaseHelper.DATATYPE_TABLE+"."+DatabaseHelper.DATATYPE_ID+" "
                +"WHERE "
                +DatabaseHelper.ADDRESS_TABLE+"."+DatabaseHelper.ADDRESS_CONTACT_ID+" = ?;";

        Cursor addressCursor = db.rawQuery(selectAddressQuery, new String[]{String.valueOf(contactID)});

        int ADDRESS_INDEX = addressCursor.getColumnIndex(DatabaseHelper.ADDRESS);
        int DATATYPE_INDEX = addressCursor.getColumnIndex(DatabaseHelper.DATA_TYPE);

        while(addressCursor.moveToNext()){
            Address address = new Address();
            address.setAddress(addressCursor.getString(ADDRESS_INDEX));
            address.setType(addressCursor.getString(DATATYPE_INDEX));
            addresses.add(address);
        }

        addressCursor.close();
        return addresses;
    }

    public boolean deleteContactByID(int CONTACT_ID){
        boolean result = db.delete(DatabaseHelper.EMERGENCY_CONTACT_TABLE, DatabaseHelper.ECONTACT_ID+"=?", new String[]{String.valueOf(CONTACT_ID)}) > 0;
        return result;
    }

    public void deleteDatabase(){
        context.deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }


    public interface OnDatabaseChangeListener{
        void onDatabaseListenerInit();
        void onContactAdd(List<Contact> contacts);
        void onContactDelete(int position);
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
                + "FOREIGN KEY (" + PHONE_TYPE_ID + ") REFERENCES " + DATATYPE_TABLE + "(" + DATATYPE_ID + ") ON DELETE CASCADE, "
                + "FOREIGN KEY (" + PHONE_CONTACT_ID + ") REFERENCES " + EMERGENCY_CONTACT_TABLE + "(" + ECONTACT_ID + ") ON DELETE CASCADE);";


        //CREATE EMAIL TABLE
        private static final String CREATE_TABLE_EMAIL = "CREATE TABLE "
                + EMAIL_TABLE + " ("
                + EMAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_CONTACT_ID + " INTEGER, "
                + EMAIL + " VARCHAR(255), "
                + EMAIL_TYPE_ID + " INTEGER, "
                + "FOREIGN KEY (" + EMAIL_TYPE_ID + ") REFERENCES " + DATATYPE_TABLE + "(" + DATATYPE_ID + ") ON DELETE CASCADE, "
                + "FOREIGN KEY (" + EMAIL_CONTACT_ID + ") REFERENCES " + EMERGENCY_CONTACT_TABLE + "(" + ECONTACT_ID + ") ON DELETE CASCADE);";

        //CREATE ADDRESS TABLE
        private static final String CREATE_TABLE_ADDRESS = "CREATE TABLE "
                + ADDRESS_TABLE + " ("
                + ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ADDRESS_CONTACT_ID + " INTEGER, "
                + ADDRESS + " VARCHAR(255), "
                + ADDRESS_TYPE_ID + " INTEGER, "
                + "FOREIGN KEY (" + ADDRESS_TYPE_ID + ") REFERENCES " + DATATYPE_TABLE + "(" + DATATYPE_ID + ") ON DELETE CASCADE, "
                + "FOREIGN KEY (" + ADDRESS_CONTACT_ID + ") REFERENCES " + EMERGENCY_CONTACT_TABLE + "(" + ECONTACT_ID + ") ON DELETE CASCADE);";


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

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
        }

        private void populateDatatypeTable(SQLiteDatabase db){
            ContentValues contentValues = new ContentValues();
            for(String type: DataType.type) {
                contentValues.clear();
                contentValues.put(DatabaseHelper.DATA_TYPE, type);
                db.insert(DatabaseHelper.DATATYPE_TABLE, null, contentValues);
            }
        }
    }
}