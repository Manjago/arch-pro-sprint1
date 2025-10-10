package io.github.manjago.telemetry.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.manjago.telemetry.dto.KafkaEvent
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import java.util.Properties

class KafkaProducerService(
    private val objectMapper: ObjectMapper,
    private val topic: String = "telemetry-events"
) {
    private val producer: KafkaProducer<String, String>
    private val logger = LoggerFactory.getLogger(KafkaProducerService::class.java)

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

        producer.send(record) { metadata, exception ->
            if (exception != null) {
                logger.error("Failed to send event: eventId=${event.eventId}, deviceId=${event.deviceId}", exception)
            } else {
                logger.info("Event sent: eventId=${event.eventId}, deviceId=${event.deviceId}, topic=${metadata.topic()}, partition=${metadata.partition()}, offset=${metadata.offset()}")
            }
        }
    }

    fun close() {
        logger.info("Closing Kafka producer")
        producer.close()
    }
}