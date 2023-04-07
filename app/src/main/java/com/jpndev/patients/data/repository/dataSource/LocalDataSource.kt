package com.jpndev.patients.data.repository.dataSource

import com.jpndev.patients.data.model.Patient
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
     suspend fun savePatientoDb(item : Patient): Long
     fun getPatientsFromDB(): Flow<List<Patient>>
     suspend fun getPatientListFromDB(): List<Patient>
     suspend fun updatePatient(pitem: Patient) : Int
     suspend fun deletePatient(item : Patient)
     suspend fun clearAllPatients()
}