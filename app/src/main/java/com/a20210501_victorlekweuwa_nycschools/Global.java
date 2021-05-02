package com.a20210501_victorlekweuwa_nycschools;

public class Global {

    public static final String ITEM_TYPE_KEY = "item_type_key";

    private static Global self = new Global();

    public static Global get() { return self; }

    private static Global instance = null;
    public static Global getInstance() {
        if (instance == null) {
            instance = new Global();
        }
        return instance;
    }

    public static final String LOG_TAG = "SchoolLogging";

    public static final String WEB_SERVICE_URL = "https://data.cityofnewyork.us";

}
