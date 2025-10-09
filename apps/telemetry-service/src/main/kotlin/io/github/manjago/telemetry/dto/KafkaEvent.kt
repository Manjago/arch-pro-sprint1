package io.github.manjago.telemetry.dto

data class KafkaEvent(
    val eventId: String,
    val eventTime: String,
    val deviceId: String,
    val payload: List<Measurement>
)