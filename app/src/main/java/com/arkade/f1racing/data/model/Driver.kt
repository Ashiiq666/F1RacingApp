package com.arkade.f1racing.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    val driverId: String,
    val permanentNumber: String,
    val code: String,
    val url: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String,
    val position: Int,
    val points: String,
    val wins: String,
    val team: String,
    val teamColor: String,
    val profilePicture: String
)

@Serializable
data class DriverResponse(
    val drivers: List<Driver>
)
