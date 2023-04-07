package com.jpndev.patients.utils

fun generateRandomName(): String {
    val firstNameList = listOf("Alice", "Bob", "Charlie", "David", "Emily", "Frank", "Grace")
    val lastNameList = listOf("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis")

    val firstName = firstNameList.random()
    val lastName = lastNameList.random()

    return "$firstName $lastName"
}

fun generateRandomAge(minAge: Int, maxAge: Int): Int {
    require(minAge < maxAge) { "Invalid age range: minAge must be less than maxAge" }
    return (minAge..maxAge).random()
}