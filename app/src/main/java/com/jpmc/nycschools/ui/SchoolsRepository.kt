package com.jpmc.nycschools.ui

import com.jpmc.nycschools.data.ApiService
import com.jpmc.nycschools.data.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SchoolsRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchNYCHighSchools() = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.fetchNYCHighSchools()
       emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }

    suspend fun fetchSchoolDetails(dbnId: String) = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.fetchSchoolDetails(dbnId)
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}