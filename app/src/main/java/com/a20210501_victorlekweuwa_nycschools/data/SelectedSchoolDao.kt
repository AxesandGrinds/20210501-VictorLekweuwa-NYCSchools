package com.a20210501_victorlekweuwa_nycschools.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// Basic DAO implementation for organizing school data
// for all NYC Schools that have SAT scores
@Dao
interface SelectedSchoolDao {

    @Query("SELECT * from selectedschools")
    fun getAll(): List<SelectedSchool>

    @Insert
    suspend fun insertSelectedSchool(school: SelectedSchool)

    @Insert
    suspend fun insertSelectedSchools(schools: List<SelectedSchool>)

    @Query("DELETE from selectedschools")
    suspend fun deleteAll()

}
