package bean.util;

import bean.input.Hotel;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelUtil {

    public static void main(String[] args) {
        String path = "C:\\Users\\14138\\Desktop\\FlinkJava\\酒店数据.xls" ;
        Iterator<Hotel> iterator = readExcel2Object(path).iterator();
        while (iterator.hasNext()){
            Hotel hotel = iterator.next();
            boolean flag = write2MysqlDB(hotel);
            System.out.println(flag);
            //System.out.println(hotel);
        }

    }

    public static void readExcelAllSheet(String path){
        InputStream is = null ;
        HSSFWorkbook wb = null ;
        try {
            //文件地址
            File file = new File(path) ;
            //读取文件
            is = new FileInputStream(file) ;
            wb = new HSSFWorkbook(is) ;
            //获取所有的sheet页
            int sheetsNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetsNum; i++) {
                System.out.println("********************sheet" + (i + 1)+"********************");
                //获取sheet对象
                HSSFSheet sheet = wb.getSheetAt(i);
                //获取该sheet的所有行数
                int rowsNum = sheet.getLastRowNum();
                for (int j = 1; j < rowsNum; j++) {
                    //获取该行对象
                    HSSFRow rows = sheet.getRow(j);
                    //获取每行的总单元格数（列数）
                    short cellNum = rows.getLastCellNum();
                    for (int k = 0; k < cellNum; k++) {
                        //获取每个单元格的值
                        HSSFCell cell = rows.getCell(k);
                        System.out.print(cell + "  ");
                        //获取单元格的数据类型
                        int cellType = cell.getCellType();
                    }
                    System.out.println();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ( wb != null){
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    public static boolean write2MysqlDB(Hotel hotel){

        //打标记
        boolean successFlag = false ;

        Connection conn = null ;
        PreparedStatement ps = null ;
        ResultSet resultSet = null ;

        try {

            /*Properties props = new Properties();
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties") ;
            props.load(input);

            String driver = props.getProperty("driver");
            String url = props.getProperty("url");
            String userName = props.getProperty("userName");
            String passWord = props.getProperty("passWord");
*/
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://hadoop101:3306/bigdata?useSSL=false&serverTimezone=UTC";
            String userName = "root";
            String passWord = "123456";


            //创建链接
            conn = DriverManager.getConnection(url, userName, passWord);

            //开启事务，将自动提交改为手动提交
            conn.setAutoCommit(false);

            //获取数据库操作对象
            //sql语句的框架，问号代表一个占位符，增加过来接收一个值
            String sql = "INSERT INTO `bigdata`.`hotel_data_source` (`id`, `NAME`, `type`, `city`, `area`, `location`, `point`, `people`, `price`, `PriceGrade`, `pupGrade`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " ;

            //将sql语句框架发送给DBMS，DBMS进行sql预编译
            ps = conn.prepareStatement(sql);

            //给占位符传值，第一个占位符是1，以此类推
            ps.setInt(1,hotel.getId());
            ps.setString(2,hotel.getName());
            ps.setString(3,hotel.getType());
            ps.setString(4,hotel.getCity());
            ps.setString(5,hotel.getArea());
            ps.setString(6,hotel.getLocation());
            ps.setString(7,hotel.getPoint());
            ps.setString(8,hotel.getPeople());
            ps.setString(9,hotel.getPrice());
            ps.setString(10,hotel.getPriceGrade());
            ps.setString(11,hotel.getPupGrade());

            //执行sql
            int rows = ps.executeUpdate();
            //处理结果集
            if (rows > 0 ){
                successFlag = true ;
            }
            //手动提交入库
            conn.commit();


        }catch (Exception e) {
            //遇到错误，回滚
            if (conn !=null){
                try {
                    conn.rollback();
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();

        }finally {
            //释放资源
            try {
                if (resultSet != null){
                    resultSet.close();
                }
                if (ps != null){
                    ps.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }


        }
        return successFlag;
    }

    public static ArrayList<Hotel> readExcel2Object(String path ){

        ArrayList<Hotel> list = new ArrayList<>();
        try {
            //解析路径
            Workbook workbook = Workbook.getWorkbook(new File(path)) ;
            //获取第一张工作表
            Sheet sheet = workbook.getSheet(0) ;
            //循环获取每一行数据，因为默认第一行是标题，我们从1开始便利，如果要获取标题，则从0即可
            for (int i = 1; i < sheet.getRows(); i++) {  //sheet.getRows()获取总行数

                //获取第一列的第i行信息
                String id         = sheet.getCell(0, i).getContents();
                String name       = sheet.getCell(1, i).getContents();
                String type       = sheet.getCell(2, i).getContents();
                String city       = sheet.getCell(3, i).getContents();
                String area       = sheet.getCell(4, i).getContents();
                String location   = sheet.getCell(5, i).getContents();
                String point      = sheet.getCell(6, i).getContents();
                String peopleNum  = sheet.getCell(7, i).getContents();
                String price      = sheet.getCell(8, i).getContents();
                String priceGrade = sheet.getCell(9, i).getContents();
                String pupGrade   = sheet.getCell(10, i).getContents();
                //System.out.println(id + " " + name + " " + type + " " + city + " " + area + " " + location + " " + point + " " + peopleNum + " " + price + " " + priceGrade + " " + pupGrade);
               //通过上面的提取的Excel数据创建对象
                Hotel hotel = new Hotel(Integer.valueOf(id),name,type,city,area,location,point,peopleNum,price,priceGrade,pupGrade);
                list.add(hotel);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    return list ;

    }






}
