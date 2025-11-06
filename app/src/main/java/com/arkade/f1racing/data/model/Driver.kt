package com.arkade.f1racing.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DriverResponse(
    val drivers: List<Driver>
)

@Serializable
data class Driver(
    val driverId: String,
    val podiums: Int,
    val points: Int,
    val poles: Int,
    val position: Int,
    val teamId: String,
    val wins: Int,
    val firstName: String,
    val lastName: String,
    val driverCode: String,
    val teamName: String,
    val racingNumber: Int
)
