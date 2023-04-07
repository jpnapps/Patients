package com.jpndev.patients.data.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date
import kotlin.collections.ArrayList


@Entity(
    tableName = "patient_table"
)
 class Patient(
   @PrimaryKey(autoGenerate = true)
   var id:Int?=null,
   @SerializedName("name")
   var name: String="",
   @SerializedName("phone")
   var phone: String="",
   @SerializedName("age")
   var age: Int=30,
   @SerializedName("email")
   var email: String="",
   @SerializedName("notes")
   var notes:  String="",
   @SerializedName("imageList")
   var imageList: ArrayList<String>? = null,
   @SerializedName("prescription")
   var prescription: String="",
   @SerializedName("lastConsulatedDate")
   @ColumnInfo(name = "lastConsulatedDate")
   var lastConsulatedDate: Date=Date(),
  @SerializedName("updatedAt")
   var updatedAt: Date=Date() ,
   @SerializedName("createdAt")
   var createdAt: Date=Date()
   ):Serializable

/*, BaseObservable()
   //, defaultValue = "CURRENT_TIMESTAMP"
{

    @get:Bindable
    @SerializedName("key1")
    var key1: String = "Username"
        set(value) {
            field = value
            notifyPropertyChanged(BR.key1)
        }
    @get:Bindable
    @SerializedName("key2")
    var key2: String = "password"
        set(value) {
            field = value
            notifyPropertyChanged(BR.key2)
        }
}
*/


/*
@Entity(
    tableName = "pitem_table"
)
data class Patient   (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,

    @SerializedName("key1")
    var key1: String="Username",
    */
/*@SerializedName("key1")
    var key1: ObservableField<String> = ObservableField<String>("Username"),*//*


    @SerializedName("name")
    var name: String="",
    @SerializedName("key2")

    var key2: String="password",
    @SerializedName("notes")
    var notes:  String="",

    @SerializedName("key3")
    var key3:  String="",
    @SerializedName("value3")
    var value3: String="",

):Serializable, BaseObservable()*/
/*

    @SerializedName("text1_color")
    var text1_color: String?,
    @SerializedName("text2_color")
    var answer1_color: String?,
    @SerializedName("web_url")
    var web_url: String?,
    @SerializedName("icon")
    var icon: String?
*/