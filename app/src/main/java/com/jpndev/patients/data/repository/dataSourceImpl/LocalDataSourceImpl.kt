package com.jpndev.patients.data.repository.dataSourceImpl

import com.jpndev.patients.data.db.DAO
import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.data.repository.dataSource.LocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LocalDataSourceImpl(
    private val pitemDAO: DAO
):LocalDataSource {


    override suspend fun savePatientoDb(item: Patient) : Long{
      return  pitemDAO.insertPItem(item)
    }

    override fun getPatientsFromDB(): Flow<List<Patient>> {
        return pitemDAO.getPItems()
    }

    override suspend fun getPatientListFromDB(): List<Patient> {
        return pitemDAO.getPatientList()
    }


    override suspend fun updatePatient(pitem: Patient):Int {
        return pitemDAO.updatePItem(pitem)
    }

    override suspend fun deletePatient(item: Patient) {
        pitemDAO.deletePItem(item)
    }

    override suspend fun clearAllPatients() {
        CoroutineScope(Dispatchers.IO).launch {
            //pitemDAO.deleteAllPItem()
        }
    }

}