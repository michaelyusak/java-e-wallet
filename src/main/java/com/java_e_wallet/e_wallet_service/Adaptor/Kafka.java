package com.java_e_wallet.e_wallet_service.adaptor;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.java_e_wallet.e_wallet_service.config.Config;

import io.confluent.kafka.serializers.KafkaJsonSerializer;

public class Kafka {
    private static Producer<String, Object> producer;

    public static void Init() {
        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.getKafkaAddress());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "e-wallet-producer");

        producer = new KafkaProducer<>(properties);
    }

    public static void publish(String topic, String key, Object message) {
        ProducerRecord<String, Object> record = new ProducerRecord<String, Object>(topic, key, message);

        producer.send(record);

        producer.flush();
    }
}
