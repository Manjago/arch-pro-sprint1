package io.github.manjago.telemetry.handler

import com.github.f4b6a3.uuid.UuidCreator
import io.github.manjago.telemetry.dto.KafkaEvent
import io.github.manjago.telemetry.dto.TelemetryRequest
import io.github.manjago.telemetry.service.KafkaProducerService
import io.javalin.http.Context

class TelemetryHandler(
    private val kafkaProducerService: KafkaProducerService
) {
    fun handleTelemetry(ctx: Context) {
        val request = ctx.bodyAsClass(TelemetryRequest::class.java)

        // Создаем событие для Kafka
        val event = KafkaEvent(
            eventId = UuidCreator.getTimeOrderedEpoch().toString(),
            eventTime = request.timestamp,
            deviceId = request.deviceId,
            payload = request.measurements
        )

        // Отправляем в Kafka
        kafkaProducerService.sendEvent(event)

        // Возвращаем 202 Accepted
        ctx.status(202)
    }
}