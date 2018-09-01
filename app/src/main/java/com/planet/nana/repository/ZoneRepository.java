package com.planet.nana.repository;

import com.planet.nana.api.Api;
import com.planet.nana.model.Zone;
import com.planet.nana.util.Prefer;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ZoneRepository {

    private static ZoneRepository instance;
    private static final long CACHE_TIME = 1000 * 60 * 5; //5 min

    public static ZoneRepository getInstance() {
        if (instance == null) {
            instance = new ZoneRepository();
        }
        return instance;
    }

    private long lastCachedTime = -1;
    private ArrayList<Zone> cachedZoneList;

    public Single<ArrayList<Zone>> getFilterZoneList() {
        if (cachedZoneList == null || isCacheExpired()) {
            return fetchFilterZoneList();
        }
        return Single.just(cachedZoneList);
    }

    private Single<ArrayList<Zone>> fetchFilterZoneList() {
        return Api.getInstance().getZoneList(
                Prefer.getString(Prefer.KEY_LOGINED_ID)
        ).subscribeOn(Schedulers.io());
    }

    private boolean isCacheExpired() {
        long currentTime = System.currentTimeMillis();
        return  (currentTime - lastCachedTime > CACHE_TIME);
    }

}
