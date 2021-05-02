package com.a20210501_victorlekweuwa_nycschools.utilities

import android.content.Context
import android.content.SharedPreferences
import com.a20210501_victorlekweuwa_nycschools.ITEM_TYPE_KEY

class PrefsHelper2 {

    companion object {

        private fun preferences(context: Context): SharedPreferences =
            context.getSharedPreferences("default", 0)

        fun setItemType(context: Context, type: String) {
            preferences(context).edit()
                .putString(ITEM_TYPE_KEY, type)
                .apply()
        }

        fun getItemType(context: Context): String =
            preferences(context).getString(ITEM_TYPE_KEY, "list")!!

    }

}