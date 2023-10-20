import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class CustomFlink {


    public static void main(String[] args) throws Exception {


        //执行环境env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1) ;






        //5执行
        env.execute() ;


    }



}
