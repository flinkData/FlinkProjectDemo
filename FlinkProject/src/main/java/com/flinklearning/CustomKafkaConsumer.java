import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class CustomKafkaConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        /**
         * kafka服务器地址
         */
        props.setProperty("bootstrap.servers","hadoop101:9092");
        /**
         * 消费者组，可以随意指定，多个消费者根据此值确定是否为同一组
         */
        props.setProperty("group.id","custom-auto-consumer") ;
        /**
         * 设置enable.auto.commit,偏移量由auto.commit.interval.ms控制自动提交的频率。
         */
        props.setProperty("enable.auto.commit", "true");
        /**
         * 序列化
         */
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        /**
         * 根据配置创建消费者
         */
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(props) ;
        /**
         * 订阅主题，不指定分区，由kafka自动分配
         *
         */
        consumer.subscribe(Arrays.asList("kafka-topic-test"));

        while (true){
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100)) ;
            for (ConsumerRecord<String,String> record : records){
                System.out.printf("partition = %d offset = %d, key = %s, value = %s%n",record.partition(),record.offset(), record.key(), record.value());
            }
        }

    }
}
