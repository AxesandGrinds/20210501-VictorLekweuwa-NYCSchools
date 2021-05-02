package com.a20210501_victorlekweuwa_nycschools.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [SelectedSchool::class], version = 1, exportSchema = false)
abstract class SelectedSchoolDatabase: RoomDatabase() {

    abstract fun selectedSchoolDao(): SelectedSchoolDao

    companion object {

        @Volatile
        private var INSTANCE: SelectedSchoolDatabase? = null

        // Makes sure that only one instance is being returned from
        // the selected school room database.
        fun getDatabase(context: Context): SelectedSchoolDatabase {

            if (INSTANCE == null) {

                synchronized(this) {

                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SelectedSchoolDatabase::class.java,
                        "selectedschool.db"
                    ).build()

                }

            }

            return INSTANCE!!

        }

    }

}