package com.java;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.java.entity.User;  
import com.java.iservice.IUserService;

//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WSClient {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//connectTest();
		soap();
	}
	
	public static void normal()
	{
		// 调用WebService  
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();  
        factory.setServiceClass(IUserService.class);  
        factory.setAddress("http://localhost:8889/CXFWebservice/Users");  
  
        IUserService service = (IUserService) factory.create();  
  
        System.out.println("#############Client getUserByName normal ##############");  
        User user = service.getUserByName("hoojo");  
        System.out.println(user.getName());  
  
        user.setAddress("China-Guangzhou");  
        service.setUser(user);  
	}
	
	public static void withspring()
	{
		Stweek s=new Stweek();
		  
		// 调用WebService  
//        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();  
//        factory.setServiceClass(IUserService.class);  
//        factory.setAddress(s.getTotalweek());  
  
        IUserService service = ServiceFactory.getServiceInstance(); 
  
        System.out.println("#############Client getUserByName withspring ##############");  
        User user = service.getUserByName("hoojo");  
        System.out.println(user.getName());  
  
        user.setAddress("China-Guangzhou");  
        service.setUser(user);  
        
//		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");  
//		IUserService service = (IUserService) context.getBean("client", IUserService.class);  
//        System.out.println("#############Client getUserByName withspring ##############"); 
//        User user = service.getUserByName("hoojo");  
//        System.out.println(user.getName());  
	}
	
	public static void soap()
	{
		SoapObject rpc =new SoapObject("http://iservice.java.com/", "getUserList");
		rpc.addProperty("name", "253640957");
		SoapSerializationEnvelope envelope =new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		/*
		* 发送请求
		*/
		envelope.bodyOut = rpc;
		envelope.dotNet =false;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE ht =  new HttpTransportSE("http://localhost:8080/CXFWebservice/Users?wsdl");  
		try {
				ht.call(null, envelope);
				if (envelope.getResponse() != null) {
                    Object result = envelope.getResponse();
                    //JSONObject jsonobject = new JSONObject("{list:" + envelope.getResponse().toString() + "}");
                    // 测试：显示结果
                    System.out.println("result:"+result.toString());
                }
			} catch (IOException e) {
				System.out.println("IOException:"+e.getMessage());
			} catch (XmlPullParserException e) {
				System.out.println("XmlPullParserException:"+e.getMessage());
			} 
//		catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	}
	
	public static void connectTest() throws IOException
	{
		Properties  pp= new Properties();
		InputStream fis=WSClient.class.getClassLoader().getResourceAsStream("Config.properties");  
        pp.load(fis);  
        String url1 = pp.getProperty("dbUrl");  
        String driver1 = pp.getProperty("dbDriver");  
        String username1 = pp.getProperty("dbUserName");  
        String passwd1 = pp.getProperty("dbPassword");  
		
        // 驱动程序名
        String driver = "com.mysql.jdbc.Driver";

        // URL指向要访问的数据库名scutcs
        String url = "jdbc:mysql://127.0.0.1:3306/mytest";

        // MySQL配置时的用户名
        String user = "dbTest"; 

        // MySQL配置时的密码
        String password = "123456";

        try { 
         // 加载驱动程序
         Class.forName(driver1);

         // 连续数据库
         Connection conn = DriverManager.getConnection(url1, username1, passwd1);

         if(!conn.isClosed()) 
          System.out.println("Succeeded connecting to the Database!");

         // statement用来执行SQL语句
         Statement statement = conn.createStatement();

         // 要执行的SQL语句
         String sql = "select * from student";

         // 结果集
         ResultSet rs = statement.executeQuery(sql);

         System.out.println("-----------------");
         System.out.println("执行结果如下所示:");
         System.out.println("-----------------");
         System.out.println(" 学号" + "\t" + " 姓名");
         System.out.println("-----------------");

         String name = null;

         while(rs.next()) {
 
          // 选择sname这列数据
          name = rs.getString("sname");
 
          // 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
          // 然后使用GB2312字符集解码指定的字节数组
          name = new String(name.getBytes("ISO-8859-1"),"GB2312");

          // 输出结果
          System.out.println(rs.getString("sno") + "\t" + name);
         }

         rs.close();
         conn.close();

        } catch(ClassNotFoundException e) {


         System.out.println("Sorry,can`t find the Driver!"); 
         e.printStackTrace();


        } catch(SQLException e) {


         e.printStackTrace();


        } catch(Exception e) {


         e.printStackTrace();


        }
	}

}
