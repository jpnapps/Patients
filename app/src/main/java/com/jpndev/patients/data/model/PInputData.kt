package com.jpndev.patients.data.model

data class PInputData(
    var key_1: String ="",
    var value_1: String ="",
    var key_2: String ="",
    var value_2: String =""
)

data class Status(
    var id: EStatus,
    var message: String
)