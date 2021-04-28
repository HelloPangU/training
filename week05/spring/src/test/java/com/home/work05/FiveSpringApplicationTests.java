package com.home.work05;

import com.home.work05.erti.UserService;
import com.home.work05.erti.User3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
class FiveSpringApplicationTests {

    @Autowired
    User3 user3;

    @Autowired
    UserService userService;

    @Autowired
    private DataSource dataSource;

    public static final String URL = "jdbc:mysql://47.102.203.94:3306/test?serverTimezone=UTC&characterEncoding=utf-8&serverTimezone=UTC&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";

   @Test
    void contextLoads() {
    }

    @Test
    void erti() {
        String xmlPath = "bean5.xml";
        //ClassPathXmlApplicationContext在加载配置文件时，对Bean进行实例化
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        System.out.println(applicationContext.getBean("user1"));
        System.out.println(applicationContext.getBean("user2"));
        System.out.println(user3);
        userService.send();
    }

    @Test
    void bati() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, name FROM user");
        while(rs.next()){
            System.out.println(" 姓名：" + rs.getString("name")+". id："+rs.getInt("id"));
        }
    }

    @Test
    void prepareStatement() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement stmt = conn.prepareStatement("update user set name = ? ,password = ? where  id = ?");
        stmt.setString(1, "zhuuhu");
        stmt.setString(2, "121212");
        stmt.setInt(3, 1102);
        stmt.executeUpdate();
    }

    @Test
    void testHikarCp(){
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try{

            //创建connection
            conn = dataSource.getConnection();
            statement = conn.createStatement();

            //执行sql
            rs = statement.executeQuery("SELECT id, name FROM user");

            //取数据
            while (rs.next()){
                System.out.println(" 姓名：" + rs.getString("name")+". id："+rs.getInt("id"));
            }
            //关闭connection
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

}
