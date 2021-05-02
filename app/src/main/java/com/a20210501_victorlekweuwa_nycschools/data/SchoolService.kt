package com.a20210501_victorlekweuwa_nycschools.data

import retrofit2.Response
import retrofit2.http.GET

// Web API for getting school list
interface SchoolService {

    @GET("/resource/s3k6-pzi2.json")

    suspend fun getSchoolData(): Response<List<School>>

}