package io.github.manjago.telemetry.dto

data class TelemetryRequest(
    val deviceId: String,
    val timestamp: String,
    val measurements: List<Measurement>
)

data class Measurement(
    val attribute: String,
    val value: Double
)