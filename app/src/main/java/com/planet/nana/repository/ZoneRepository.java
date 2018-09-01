package com.planet.nana.repository;

public class ZoneRepository {

    private static ZoneRepository instance;

    public static ZoneRepository getInstance() {
        if (instance == null) {
            instance = new ZoneRepository();
        }
        return instance;
    }

}
