package com.jpmc.nycschools.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpmc.nycschools.data.SchoolModel
import com.jpmc.nycschools.data.NetworkResult
import com.jpmc.nycschools.data.SchoolDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(
    private val mainRepository: SchoolsRepository
) : ViewModel() {

    private var _nYCHighSchoolsResponse = MutableLiveData<NetworkResult<List<SchoolModel>>>()
    val nYCHighSchoolsResponse: LiveData<NetworkResult<List<SchoolModel>>> = _nYCHighSchoolsResponse

    private var _schoolsDetailsResponse = MutableLiveData<NetworkResult<List<SchoolDetails>>>()
    val schoolsDetailsResponse: LiveData<NetworkResult<List<SchoolDetails>>> = _schoolsDetailsResponse

    fun fetchNYCHighSchools() {
        viewModelScope.launch {
            mainRepository.fetchNYCHighSchools().collect {
                _nYCHighSchoolsResponse.postValue(it)
            }
        }
    }

    fun fetchSchoolDetails(dbnId: String) {
        viewModelScope.launch {
            mainRepository.fetchSchoolDetails(dbnId).collect {
                _schoolsDetailsResponse.postValue(it)
            }
        }
    }
}

