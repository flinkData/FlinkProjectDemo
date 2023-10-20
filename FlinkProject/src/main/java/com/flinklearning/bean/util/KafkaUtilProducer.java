package bean.util;

import bean.input.Hotel;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class KafkaUtilProducer {

    public static  final String brokerList = "hadoop101:9092" ;
    public static  final String topic = "userLog"  ; //通过topic将kafka和flink程序捆绑

    public static void write2Kafka() throws InterruptedException {
        //配置kafka
        Properties props = new Properties();
        props.put("bootstrap.servers",brokerList) ;
       /* props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer") ;
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer") ;*/
        props.put("key.serializer","org.springframework.kafka.support.serializer.JsonSerializer") ;
        props.put("value.serializer","org.springframework.kafka.support.serializer.JsonSerializer") ;


        KafkaProducer<String, Hotel> hotelKafkaProducer = new KafkaProducer<>(props);
        ArrayList<Hotel> list = readFromMysqlDB();
        Iterator<Hotel> iterator = list.iterator();
        int count = 0 ;
        while (iterator.hasNext()){
            count +=1 ;
            Hotel hotel = iterator.next();
            ProducerRecord<String, Hotel> hotelProducerRecord = new ProducerRecord<String, Hotel>(topic,null,null, hotel);
            hotelKafkaProducer.send(hotelProducerRecord) ;
            System.out.println("发送第" + count + "条酒店数据： " + JSON.toJSONString(hotel));
            Thread.sleep(1000);
        }


    }
    //从数据库中读取数据
    public static ArrayList<Hotel> readFromMysqlDB(){
        ArrayList<Hotel> list = new ArrayList<>();
        //打标记
        boolean successFlag = false ;

        Connection conn = null ;
        PreparedStatement ps = null ;
        ResultSet rs = null ;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://hadoop101:3306/bigdata?useSSL=false&serverTimezone=UTC";
            String userName = "root";
            String passWord = "123456";
            String sql = "select `id`, `NAME`, `type`, `city`, `area`, `location`, `point`, `people`, `price`, `PriceGrade`, `pupGrade` from hotel_data_source" ;
            conn = DriverManager.getConnection(url,userName,passWord) ;
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String typ = rs.getString(3);
                String city = rs.getString(4);
                String area = rs.getString(5);
                String location = rs.getString(6);
                String point = rs.getString(7);
                String people = rs.getString(8);
                String price = rs.getString(9);
                String priceGrade = rs.getString(10);
                String pupGrade = rs.getString(11);
                Hotel hotel = new Hotel(id, name, typ, city, area, location, point, people, price, priceGrade, pupGrade);
                list.add(hotel) ;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list ;

    }





}
