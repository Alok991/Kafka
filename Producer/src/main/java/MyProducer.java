import org.apache.commons.text.RandomStringGenerator;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;



public class MyProducer {
	public static void main(String[] args) {

		String MATH_TOPIC = "MathTopic";
		String ENG_TOPIC = "EnglishTopic";


		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093");
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);


		for(long i=0;i<100000000;i++){
			System.out.println("Batch No: "+i);
			for (long j = 0; j < 1000; j++) {
				double num1 =  1000*Math.random();
				double num2 =  1000*Math.random();
				ProducerRecord<String, String> record = new ProducerRecord<String, String>(MATH_TOPIC, num1+","+num2);
				producer.send(record);
			}

			for (long j = 0; j < 1000; j++) {
				String randomeString = new RandomStringGenerator.Builder()
						.withinRange('a', 'z').build().generate(20);
				ProducerRecord<String, String> record = new ProducerRecord<String, String>(ENG_TOPIC, randomeString);
				producer.send(record);
			}
		}


		producer.close();

		System.out.println("MyProducer closed.");
	}
}
