package com.a20210501_victorlekweuwa_nycschools.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schools")

// School model containing all the possible values
// of all NYC schools that exist in a JSON response
data class School (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val dbn: String,
    val school_name: String,
    val boro: String?,
    val overview_paragraph: String?,
    val school_10th_seats: String?,
    val academicopportunities1: String?,
    val academicopportunities2: String?,
    val ell_programs: String?,
    val neighborhood: String?,
    val building_code: String?,
    val location: String?,
    val phone_number: String?,
    val fax_number: String?,
    val school_email: String?,
    val website: String?,
    val subway: String?,
    val bus: String?,
    val grades2018: String?,
    val finalgrades: String?,
    val total_students: String?,
    val extracurricular_activities: String?,
    val school_sports: String?,
    val attendance_rate: String?,
    val pct_stu_enough_variety: String?,
    val pct_stu_safe: String?,
    val school_accessibility_description: String?,
    val directions1: String?,
    val requirement1_1: String?,
    val requirement2_1: String?,
    val requirement3_1: String?,
    val requirement4_1: String?,
    val requirement5_1: String?,
    val offer_rate1: String?,
    val program1: String?,
    val code1: String?,
    val interest1: String?,
    val method1: String?,
    val seats9ge1: String?,
    val grade9gefilledflag1: String?,
    val grade9geapplicants1: String?,
    val seats9swd1: String?,
    val grade9swdfilledflag1: String?,
    val grade9swdapplicants1: String?,
    val seats101: String?,
    val admissionspriority11: String?,
    val admissionspriority21: String?,
    val admissionspriority31: String?,
    val grade9geapplicantsperseat1: String?,
    val grade9swdapplicantsperseat1: String?,
    val primary_address_line_1: String?,
    val city: String?,
    val zip: String?,
    val state_code: String?,
    val latitude: String?,
    val longitude: String?,
    val community_board: String?,
    val council_district: String?,
    val census_tract: String?,
    val bin: String?,
    val bbl: String?,
    val nta: String?,
    val borough: String?,

) {


}
