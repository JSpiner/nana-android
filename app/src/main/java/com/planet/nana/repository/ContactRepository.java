package com.planet.nana.repository;

public class ContactRepository {

    private static ContactRepository contactRepository;

    public static ContactRepository getInstance() {
        if (contactRepository == null) {
            contactRepository = new ContactRepository();
        }
        return contactRepository;
    }

}
