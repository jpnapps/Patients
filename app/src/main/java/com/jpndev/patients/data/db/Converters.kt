package com.jpndev.patients.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {
/*
    @TypeConverter
    fun fromSource(source:Source):String{
        return source.name
    }
    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }*/

    @TypeConverter
    open fun stringsToJson(list: ArrayList<String>?): String? {
        if (list == null) return null
        //  val lang: String? = list[0]
        return if (list.isEmpty()) null else Gson().toJson(list)
    }

    @TypeConverter
    fun storedStringToStrings(data: String?): ArrayList<String>? {
        val gson = Gson()
        if (data == null) {
            return ArrayList()
        }
        val listType: Type = object : TypeToken<ArrayList<String>>() {}.getType()
        return gson.fromJson<ArrayList<String>>(data, listType)
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
//Expectedd
//   TableInfo{name='patient_table', columns={notes=Column{name='notes', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, phone=Column{name='phone', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, prescription=Column{name='prescription', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, name=Column{name='name', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, lastConsulatedDate=Column{name='lastConsulatedDate', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='CURRENT_TIMESTAMP'}, id=Column{name='id', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=1, defaultValue='null'}, imageList=Column{name='imageList', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, age=Column{name='age', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, email=Column{name='email', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}}, foreignKeys=[], indices=[]}
//Found
//    TableInfo{name='patient_table', columns={notes=Column{name='notes', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, phone=Column{name='phone', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, prescription=Column{name='prescription', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, name=Column{name='name', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, lastConsulatedDate=Column{name='lastConsulatedDate', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='1678394682616'}, id=Column{name='id', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=1, defaultValue='null'}, imageList=Column{name='imageList', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, age=Column{name='age', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, email=Column{name='email', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}}, foreignKeys=[], indices=[]}