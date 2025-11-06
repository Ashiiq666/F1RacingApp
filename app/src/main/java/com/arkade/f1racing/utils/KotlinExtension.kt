package com.arkade.f1racing.utils

object KotlinExtension {

    fun formatCircuitId(circuitId: String): String {
        return circuitId
            .split("_")
            .joinToString(" ") { word ->
                word.lowercase().replaceFirstChar { 
                    if (it.isLowerCase()) it.titlecase() else it.toString() 
                }
            }
    }
}

