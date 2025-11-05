package com.arkade.f1racing.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Race(
    val round: String,
    val raceName: String,
    val circuitId: String,
    val circuitName: String,
    val locality: String,
    val country: String,
    val raceStartTime: String,
    val raceEndTime: String,
    val sessions: List<Session>
)

@Serializable
data class RaceResponse(
    val races: List<Race>
)

@Serializable
data class Session(
    val sessionName: String,
    val startTime: String,
    val endTime: String
)
