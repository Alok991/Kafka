import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.streams.processor.internals.RecordCollector;

import java.util.Arrays;
import java.util.Properties;


public class Consumer {
	public static void main(String[] args) {

		String topicName = "MathTopic";
		String groupName = "Group";

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093");
		props.put("group.id", groupName);
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("enable.auto.commit", "false");

		KafkaConsumer<String, String> consumer = null;

		try {
			consumer = new KafkaConsumer<>(props);
			consumer.subscribe(Arrays.asList(topicName));

			while (true){
				ConsumerRecords<String,String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records){
					double result = doSomeProcessing(record.value());
					System.out.println(result);
				}
				consumer.commitAsync();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			consumer.commitSync();
			consumer.close();
		}
	}

	private static double doSomeProcessing(String value) {
		double result=0;

		for (String s : value.split(",")) {
			result+= Double.valueOf(s);
		}

		return result;
	}


}
