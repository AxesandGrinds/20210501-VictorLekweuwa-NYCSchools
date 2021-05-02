package com.a20210501_victorlekweuwa_nycschools.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// Basic DAO implementation for organizing school data
// for all NYC Schools
@Dao
interface SchoolDao {

    @Query("SELECT * from schools")
    fun getAll(): List<School>

    @Insert
    suspend fun insertSchool(school: School)

    @Insert
    suspend fun insertSchools(schools: List<School>)

    @Query("DELETE from schools")
    suspend fun deleteAll()

}
