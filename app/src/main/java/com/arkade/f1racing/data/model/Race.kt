package com.arkade.f1racing.data.model

import kotlinx.serialization.Serializable


@Serializable
data class ScheduleResponse(
    val schedule: List<Race>
)

@Serializable
data class Race(
    val raceId: String,
    val round: Int,
    val raceName: String,
    val circuitId: String,
    val circuitName: String? = null,
    val locality: String? = null,
    val country: String? = null,
    val raceStartTime: Long,
    val raceEndTime: Long,
    val raceState: String,
    val isSprint: Boolean,
    val sessions: List<Session>,
    val podium: List<String>? = null
)

@Serializable
data class Session(
    val sessionId: String,
    val sessionType: String,
    val sessionName: String,
    val startTime: Long,
    val endTime: Long,
    val sessionState: String,
    val _id: String
)
