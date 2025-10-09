package io.github.manjago.telemetry.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.manjago.telemetry.dto.KafkaEvent
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

class KafkaProducerService(
    private val objectMapper: ObjectMapper,
    private val topic: String = "telemetry-events"
) {
    private val producer: KafkaProducer<String, String>

    init {
        val props = Properties().apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("KAFKA_BOOTSTRAP_SERVERS") ?: "localhost:9092")
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
            put(ProducerConfig.ACKS_CONFIG, "all")
            put(ProducerConfig.RETRIES_CONFIG, 3)
        }
        producer = KafkaProducer(props)
    }

    fun sendEvent(event: KafkaEvent) {
        val json = objectMapper.writeValueAsString(event)
        val record = ProducerRecord(topic, event.deviceId, json)
        producer.send(record)
    }

    fun close() {
        producer.close()
    }
}