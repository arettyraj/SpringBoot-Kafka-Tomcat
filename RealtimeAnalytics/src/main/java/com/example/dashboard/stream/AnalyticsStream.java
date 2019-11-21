package com.example.dashboard.stream;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.common.serialization.Deserializer;
// Kafka imports
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
// Kafka Streams related imports
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.dashboard.model.Customer;

@Component
public class AnalyticsStream {

	@KafkaListener(topics = "topic1")
	public void streamData() {
		// Create an instance of StreamsConfig from the Properties instance
		StreamsConfig config = new StreamsConfig(getProperties());
		final Serde<String> stringSerde = Serdes.String();
		final Serde<Long> longSerde = Serdes.Long();

		// define countryMessageSerde
		Map<String, Object> serdeProps = new HashMap<>();
		final Serializer<Customer> countryMessageSerializer = new JsonPOJOSerializer<>();
		serdeProps.put("JsonPOJOClass", Customer.class);
		countryMessageSerializer.configure(serdeProps, false);

		final Deserializer<Customer> countryMessageDeserializer = new JsonPOJODeserializer<>();
		serdeProps.put("JsonPOJOClass", Customer.class);
		countryMessageDeserializer.configure(serdeProps, false);
		final Serde<Customer> countryMessageSerde = Serdes.serdeFrom(countryMessageSerializer,
				countryMessageDeserializer);

		// building Kafka Streams Model
		KStreamBuilder kStreamBuilder = new KStreamBuilder();
		// the source of the streaming analysis is the topic with country messages
		KStream<String, Customer> countriesStream = kStreamBuilder.stream(stringSerde, countryMessageSerde, "topic1");

		KTable<String, Long> runningCountriesCountPerContinent = countriesStream
				.selectKey((k, customer) -> customer.getFirstName()).countByKey("Counts");
		runningCountriesCountPerContinent.to(stringSerde, longSerde, "RunningCountryCountPerContinent");
		System.out.println("Listned....");
		runningCountriesCountPerContinent.print(stringSerde, longSerde);

		System.out.println("Starting Kafka Streams Countries Example");
		KafkaStreams kafkaStreams = new KafkaStreams(kStreamBuilder, config);
		kafkaStreams.start();

	}

	private static Properties getProperties() {
		Properties settings = new Properties();
		settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "APP_ID");
		settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
		settings.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		settings.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		settings.put(StreamsConfig.STATE_DIR_CONFIG, "C:\\temp");
		settings.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
		return settings;
	}
}
