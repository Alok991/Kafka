
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


import java.util.Arrays;
import java.util.Properties;


public class Consumer {

	public static void main(String[] args) {

		String topicName = "EnglishTopic";
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
					String result = doSomeProcessing(record.value());
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

	private static String doSomeProcessing(String value) {
		return value.toUpperCase();
	}
}
