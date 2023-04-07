package com.jpndev.patients.data.db

import androidx.room.*
import com.jpndev.patients.data.model.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPItem(pitem: Patient): Long

    @Update
    suspend fun updatePItem(pitem: Patient): Int

    /*    @Query("DELETE  FROM patient_table")
        suspend fun deleteAllPItem():Unit*/
    @Delete
    suspend fun deletePItem(item: Patient)


    @Query("SELECT * FROM patient_table ORDER BY lastConsulatedDate DESC")
    fun getPItems(): Flow<List<Patient>>

    @Query("SELECT * FROM patient_table ORDER BY lastConsulatedDate DESC")
    suspend fun getPatientList(): List<Patient>
}