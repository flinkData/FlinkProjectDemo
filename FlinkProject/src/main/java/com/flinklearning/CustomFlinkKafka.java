import bean.input.Sensor;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

public class CustomFlinkKafka {


    public static void main(String[] args) throws Exception {


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //kafka配置
        Properties props = new Properties();
        props.setProperty("bootstrap.servers","hadoop101:9092");
        props.setProperty("group.id","consumer-group");
        props.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer") ;
        props.setProperty("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer") ;
        props.setProperty("auto.offset.reset","latest") ;

        //从kafka读取数据
        //DataStreamSource<String> dataStreamSource = env.addSource(new FlinkKafkaConsumer<String>("sensor", new SimpleStringSchema(), props));
        DataStreamSource<Sensor> dataStreamSource = env.addSource(new MySensor());

        //打印输出
        dataStreamSource.print().setParallelism(1) ;

        env.execute() ;


    }

    public static  class MySensor implements SourceFunction<Sensor>{
        private boolean flag = true ;

        @Override
        public void run(SourceContext<Sensor> ctx) throws Exception {
            Random random = new Random();
            HashMap<String, Double> sensorMap = new HashMap<>();
            for (int i = 0; i < 100; i++) {
                sensorMap.put("sensor_" + (i+1),random.nextGaussian() * 20 ) ;
            }
            while (flag){
                for (String sensorId : sensorMap.keySet()) {
                    Double newTemp = sensorMap.get(sensorId) + random.nextGaussian();
                    sensorMap.put(sensorId,newTemp) ;
                    ctx.collect(new Sensor(sensorId,newTemp));
                    Thread.sleep(1000);
                }

            }

        }

        @Override
        public void cancel() {
            this.flag = false ;
        }
    }
}
