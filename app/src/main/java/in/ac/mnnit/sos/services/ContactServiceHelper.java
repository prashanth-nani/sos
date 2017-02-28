package in.ac.mnnit.sos.services;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import in.ac.mnnit.sos.extras.Utils;
import in.ac.mnnit.sos.models.Contact;

/**
 * Created by prashanth on 28/2/17.
 */

public class ContactServiceHelper {

    private Uri contactUri;
    private String name = null;
    private Map<String, String> phone = new HashMap<>();
    private Map<String, String> email = new HashMap<>();
    private Map<String, String> address = new HashMap<>();
    private ContentResolver contentResolver;
    private String contactID;
    private String photoUri;

    public ContactServiceHelper(Context context, Uri contactUri) {
        this.contactUri = contactUri;
        this.contentResolver = context.getContentResolver();
        init();
    }

    private void init() {

        Cursor cursorID = contentResolver.query(contactUri,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.PHOTO_URI},
                null, null, null);

        /*
        * Retrieving Contact ID, Name and PhotoURI
        */
        assert cursorID != null;
        if (cursorID.moveToFirst()) {
            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
//            name = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            photoUri = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
        }
        cursorID.close();


        /*
        * Retrieving phone numbers
        */
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{contactID}, null);

        assert cursor != null;
        if (cursor.getCount() > 0) {
            Cursor pCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{contactID}, null);

            assert pCursor != null;
            while (pCursor.moveToNext()) {
                int phoneType = pCursor.getInt(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                String phoneNo = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                name = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                switch (phoneType) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        Log.e(name + ": TYPE_MOBILE", " " + phoneNo);
                        phone.put("Mobile", phoneNo);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        Log.e(name + ": TYPE_HOME", " " + phoneNo);
                        phone.put("Home", phoneNo);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                        Log.e(name + ": TYPE_WORK", " " + phoneNo);
                        phone.put("Work", phoneNo);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
                        Log.e(name + ": TYPE_WORK_MOBILE", " " + phoneNo);
                        phone.put("Work Mobile", phoneNo);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                        Log.e(name + ": TYPE_OTHER", " " + phoneNo);
                        phone.put("Other", phoneNo);
                        break;
                    case ContactsContract.CommonDataKinds.BaseTypes.TYPE_CUSTOM:
                        Log.e(name + ": TYPE_CUSTOM", " " + phoneNo);
                        phone.put("Custom", phoneNo);
                        break;
                    default:
                        break;
                }
            }
            pCursor.close();


            /*
            * Retrieving email
            */
            Cursor eCursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{contactID}, null);

            assert eCursor != null;
            while (eCursor.moveToNext()) {
                int emailType = eCursor.getInt(eCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                String emailID = eCursor.getString(eCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                switch (emailType) {
                    case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                        Log.e(name + ": TYPE_HOME", " " + emailID);
                        email.put("Home", emailID);
                        break;
                    case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                        Log.e(name + ": TYPE_WORK", " " + emailID);
                        email.put("Work", emailID);
                        break;
                    case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                        Log.e(name + ": TYPE_OTHER", " " + emailID);
                        email.put("Other", emailID);
                        break;
                    case ContactsContract.CommonDataKinds.BaseTypes.TYPE_CUSTOM:
                        Log.e(name + ": TYPE_CUSTOM", " " + emailID);
                        email.put("Custom", emailID);
                        break;
                    default:
                        break;
                }
            }
            eCursor.close();


            /*
            * Retrieving address
            */
            Cursor aCursor = contentResolver.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{contactID}, null);

            assert aCursor != null;
            while (aCursor.moveToNext()) {
                int addressType = aCursor.getInt(aCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                String personAddress = aCursor.getString(aCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
                switch (addressType) {
                    case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME:
                        Log.e(name + ": TYPE_HOME", " " + personAddress);
                        address.put("Home", personAddress);
                        break;
                    case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK:
                        Log.e(name + ": TYPE_HOME", " " + personAddress);
                        address.put("Work", personAddress);
                        break;
                    case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER:
                        Log.e(name + ": TYPE_HOME", " " + personAddress);
                        address.put("Other", personAddress);
                        break;
                    case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM:
                        Log.e(name + ": TYPE_HOME", " " + personAddress);
                        address.put("Custom", personAddress);
                        break;
                    default:
                        break;
                }
            }
            aCursor.close();

            cursor.close();
        }
    }


    public Contact getContact() throws IOException {
        Utils utils = new Utils();
        Contact contact = new Contact(name, phone, email, address);
        contact.setHighResPhoto(utils.getPhotoBitmap(contentResolver, photoUri));
        return contact;
    }
}
