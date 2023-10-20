import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class CustomKafkaProducer {

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties() ;
        props.put("bootstrap.servers","hadoop101:9092") ;

        /**
         * ack是判断请求是否为完整条件的（判断是不是发送成功）
         * ack有3个值：
         * 我们指定为all将会阻塞消息，这种设置性能最低，但是消息发送最可靠
         *
         */
        props.put("acks","all") ;
        /**
         * retries
         * 如果请求失败，生产者会自动重试
         * 我们指定是0次，如果启用重试，则会有重复消息的可能性
         */
        props.put("retries",0);
        /**
         * producer缓存每个分区未发送的消息，缓存的大小是通过batch.size配置指定的值的
         * 值较大的话将会产生更大的批，并需要更多的内存（因为每个活跃的分区都有一个缓冲区）
         */
        props.put("batch.size",16384);

        /**
         * 默认缓存可立即发送，即便缓存空间还没有满
         * 但是，如果我们想减少请求的数量，可以设置linger.ms大于0
         * 这将指示生产者发送请求之前等待一段时间，希望更多的消息填补到未满的批中
         *
         * 需要注意的是，在高负荷的情况下，相近的时间一般也会组成批，即便是linger.ms=0
         * 在不处于高负载的情况下，如果设置比0大，以减少延迟代价换取更少的，更有效的请求
         */
        props.put("linger.ms",1);

        /**
         * 控制生产者可用的缓存总量，如果消息发送速度比其传输到服务器的快，将会耗尽这个缓存空间
         * 当缓存空间耗尽，其他发送调用将被阻塞，阻塞时间的阔值通过max.block.ms设定，之后他将抛出一个超时异常TimeoutException
         */
        props.put("buffer.memory",33554432);

        /**
         * 序列化
         */
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        Producer<String,String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000);
            producer.send(new ProducerRecord<String,String>("kafka-topic-test","partition0",Integer.toString(i)));
        }
        producer.close();


    }




}
