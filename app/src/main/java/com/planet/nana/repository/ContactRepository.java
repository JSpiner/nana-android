package com.planet.nana.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.planet.nana.BaseApplication;
import com.planet.nana.model.Contact;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

public class ContactRepository {

    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };
    private static final long CACHE_TIME = 1000 * 60 * 5; //5 min

    private static ContactRepository contactRepository;

    private long lastCachedTime = -1;
    private ArrayList<Contact> cachedContactList;

    public static ContactRepository getInstance() {
        if (contactRepository == null) {
            contactRepository = new ContactRepository();
        }
        return contactRepository;
    }

    public Observable<Contact> loadContacts() {
        if (cachedContactList == null || isCacheExpired()) {
            return fetchContacts();
        }
        return Observable.fromIterable(cachedContactList);
    }

    private boolean isCacheExpired() {
        long currentTime = System.currentTimeMillis();
        return  (currentTime - lastCachedTime > CACHE_TIME);
    }

    private Observable<Contact> fetchContacts() {
        Observable<Contact> contactObservable = Observable.create(
                emitter -> {
                    ContentResolver resolver = BaseApplication.application.getContentResolver();
                    Cursor cursor = resolver.query(CONTENT_URI, PROJECTION, null, null, null);
                    final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                    cachedContactList = new ArrayList<>();
                    String name;
                    String number;
                    while (cursor.moveToNext()) {
                        name = cursor.getString(nameIndex);
                        number = cursor.getString(numberIndex)
                                .replace("-", "")
                                .replace("+82", "");

                        Contact contact = new Contact(name, number);
                        emitter.onNext(contact);
                        cachedContactList.add(contact);
                    }
                    cursor.close();
                    emitter.onComplete();
                }
        );
        return contactObservable.observeOn(Schedulers.io());
    }
}
