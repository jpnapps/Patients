package com.jpndev.patients.ui.dashboard

import android.app.Application
import androidx.lifecycle.*
import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.data.util.Resource
import com.jpndev.patients.ui.patient.PatientRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel (
    private val app: Application,
    public val repository: PatientRepository
) : AndroidViewModel(app) {

    val mld_Progress: MutableLiveData<Resource<Patient>> = MutableLiveData()
    fun getSavedPItems(): LiveData<List<Patient>> = liveData<List<Patient>> {
        mld_Progress.postValue(Resource.Loading())
        repository.getPatients().collect {
            emit(it)
        }
    }

    fun deletePatient(item: Patient) = viewModelScope.launch {
        repository.deletePatient(item)
    }
}