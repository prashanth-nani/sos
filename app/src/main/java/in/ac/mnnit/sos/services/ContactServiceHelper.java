package in.ac.mnnit.sos.services;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.ac.mnnit.sos.extras.Utils;
import in.ac.mnnit.sos.models.Address;
import in.ac.mnnit.sos.models.Contact;
import in.ac.mnnit.sos.models.Email;
import in.ac.mnnit.sos.models.Phone;

/**
 * Created by Banda Prashanth Yadav on 28/2/17.
 */

public class ContactServiceHelper {

    private Uri contactUri;
    private String name = null;
    private List<Phone> phone = new ArrayList<>();
    private List<Email> email = new ArrayList<>();
    private List<Address> address = new ArrayList<>();
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
            Set<String> tempPhone = new HashSet<>();
            while (pCursor.moveToNext()) {
                int phoneType = pCursor.getInt(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                String phoneNo = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                name = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phoneNo = phoneNo.replaceAll(" ", "");
                if(!tempPhone.contains(phoneNo)) {
                    tempPhone.add(phoneNo);
                    switch (phoneType) {
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                            phone.add(new Phone(phoneNo, "Mobile"));
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                            phone.add(new Phone(phoneNo, "Home"));
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                            phone.add(new Phone(phoneNo, "Work"));
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
                            phone.add(new Phone(phoneNo, "Work Mobile"));
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                            phone.add(new Phone(phoneNo, "Other"));
                            break;
                        case ContactsContract.CommonDataKinds.BaseTypes.TYPE_CUSTOM:
                            phone.add(new Phone(phoneNo, "Custom"));
                            break;
                        default:
                            break;
                    }
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
            Set<String> tempEmail = new HashSet<>();
            while (eCursor.moveToNext()) {
                int emailType = eCursor.getInt(eCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                String emailID = eCursor.getString(eCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                if(!tempEmail.contains(emailID)) {
                    tempEmail.add(emailID);
                    switch (emailType) {
                        case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                            email.add(new Email(emailID, "Home"));
                            break;
                        case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                            email.add(new Email(emailID, "Work"));
                            break;
                        case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                            email.add(new Email(emailID, "Other"));
                            break;
                        case ContactsContract.CommonDataKinds.BaseTypes.TYPE_CUSTOM:
                            email.add(new Email(emailID, "Custom"));
                            break;
                        default:
                            break;
                    }
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
            Set<String> tempAddress = new HashSet<>();
            while (aCursor.moveToNext()) {
                int addressType = aCursor.getInt(aCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                String personAddress = aCursor.getString(aCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
                if(!tempAddress.contains(personAddress)) {
                    tempAddress.add(personAddress);
                    switch (addressType) {
                        case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME:
                            address.add(new Address(personAddress, "Home"));
                            break;
                        case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK:
                            address.add(new Address(personAddress, "Work"));
                            break;
                        case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER:
                            address.add(new Address(personAddress, "Other"));
                            break;
                        case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM:
                            address.add(new Address(personAddress, "CUSTOM"));
                            break;
                        default:
                            break;
                    }
                }
            }
            aCursor.close();

            cursor.close();
        }
    }


    public Contact getContact() throws IOException {
        Utils utils = new Utils();
        Contact contact = new Contact(name, phone, email, address);
        Bitmap photo = utils.getPhotoBitmap(contentResolver, photoUri);

        if(photo != null)
            contact.setHighResPhoto(utils.getBytesFromBitmap(photo));

        return contact;
    }
}
