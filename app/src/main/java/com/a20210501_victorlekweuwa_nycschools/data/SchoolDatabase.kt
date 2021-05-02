package com.a20210501_victorlekweuwa_nycschools.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [School::class], version = 1, exportSchema = false)
abstract class SchoolDatabase: RoomDatabase() {

    abstract fun schoolDao(): SchoolDao

    companion object {

        @Volatile
        private var INSTANCE: SchoolDatabase? = null

        // Makes sure that only one instance is being returned from the school room database.
        fun getDatabase(context: Context): SchoolDatabase {

            if (INSTANCE == null) {

                synchronized(this) {

                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SchoolDatabase::class.java,
                        "school.db"
                    ).build()

                }

            }

            return INSTANCE!!

        }

    }

}