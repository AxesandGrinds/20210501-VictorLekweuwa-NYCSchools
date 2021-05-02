package com.a20210501_victorlekweuwa_nycschools.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selectedschools")

// School model containing all the possible values
// of all NYC schools tha have SAT scores that exist in a JSON response
data class SelectedSchool (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val dbn: String,
    val school_name: String,
    val num_of_sat_test_takers: String,
    val sat_critical_reading_avg_score: String,
    val sat_math_avg_score: String,
    val sat_writing_avg_score: String,

) {


}
