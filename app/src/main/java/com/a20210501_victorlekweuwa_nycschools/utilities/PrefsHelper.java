package com.a20210501_victorlekweuwa_nycschools.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.a20210501_victorlekweuwa_nycschools.Global;

public class PrefsHelper {

    private SharedPreferences preferences(Context context) {

        return context.getSharedPreferences("default", 0);

    }

    public void setItemType(Context context, String type) {

        preferences(context).edit()
                .putString(Global.ITEM_TYPE_KEY, type)
                .apply();

    }

    public String getItemType(Context context) {

        return preferences(context).getString(Global.ITEM_TYPE_KEY, "list");

    }


}
