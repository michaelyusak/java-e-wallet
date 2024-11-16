package com.java_e_wallet.e_wallet_service.Adaptor;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.java_e_wallet.e_wallet_service.config.Config;

public class Kafka {
    private static Producer<String, String> producer;
    private static Kafka kafkaInstance;

    public static void Init() {
        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.getKafkaAddress());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "e-wallet-producer");

        if (kafkaInstance == null) {
            kafkaInstance = new Kafka();
        }

        producer = new KafkaProducer<>(properties);
    }

    public Kafka getKafkaInstance() {
        return kafkaInstance;
    }

    public static void publish(String topic, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);

        producer.send(record);

        producer.flush();
    }
}
