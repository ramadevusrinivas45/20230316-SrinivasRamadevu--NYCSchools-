package com.jpmc.nycschools.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("resource/s3k6-pzi2.json")
    suspend fun fetchNYCHighSchools(): List<SchoolModel>

    @GET("resource/f9bf-2cp4.json")
    suspend fun fetchSchoolDetails(@Query("dbn") dbnId: String): List<SchoolDetails>
}