package com.a20210501_victorlekweuwa_nycschools.data

import retrofit2.Response
import retrofit2.http.GET

// Web API for getting school list of schools with SAT scores
interface SelectedSchoolService {

    @GET("/resource/f9bf-2cp4.json")

    suspend fun getSelectedSchoolData(): Response<List<SelectedSchool>>

}