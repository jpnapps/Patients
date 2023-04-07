package com.jpndev.patients.data.model


import com.google.gson.annotations.SerializedName

data class PListResponse(
    @SerializedName("pitem")
    val pitem_list: List<Patient>,
)
