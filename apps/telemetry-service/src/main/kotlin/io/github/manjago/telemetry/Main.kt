package io.github.manjago.telemetry

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.manjago.telemetry.handler.TelemetryHandler
import io.github.manjago.telemetry.service.KafkaProducerService
import io.javalin.Javalin

fun main() {
    // Настраиваем ObjectMapper
    val objectMapper = ObjectMapper().apply {
        registerKotlinModule()
        registerModule(JavaTimeModule())
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    // Создаем сервисы
    val kafkaProducerService = KafkaProducerService(objectMapper)
    val telemetryHandler = TelemetryHandler(kafkaProducerService)

    // Настраиваем Javalin
    val app = Javalin.create { config ->
        config.http.defaultContentType = "application/json"
        config.jsonMapper(JavalinJackson(objectMapper))
    }.start(7070)

    // Роуты
    app.post("/telemetry") { ctx -> telemetryHandler.handleTelemetry(ctx) }

    // Graceful shutdown
    Runtime.getRuntime().addShutdownHook(Thread {
        kafkaProducerService.close()
        app.stop()
    })

    println("Telemetry Service started on http://localhost:7070")
}

// Кастомный Jackson mapper для Javalin
class JavalinJackson(private val objectMapper: ObjectMapper) : io.javalin.json.JsonMapper {
    override fun toJsonString(obj: Any, type: java.lang.reflect.Type): String {
        return objectMapper.writeValueAsString(obj)
    }

    override fun <T : Any> fromJsonString(json: String, targetType: java.lang.reflect.Type): T {
        val javaType = objectMapper.typeFactory.constructType(targetType)
        return objectMapper.readValue(json, javaType)
    }
}