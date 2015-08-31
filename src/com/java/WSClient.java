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
		// ����WebService  
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
		  
		// ����WebService  
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
		* ��������
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
                    // ���ԣ���ʾ���
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
		
        // ����������
        String driver = "com.mysql.jdbc.Driver";

        // URLָ��Ҫ���ʵ����ݿ���scutcs
        String url = "jdbc:mysql://127.0.0.1:3306/mytest";

        // MySQL����ʱ���û���
        String user = "dbTest"; 

        // MySQL����ʱ������
        String password = "123456";

        try { 
         // ������������
         Class.forName(driver1);

         // �������ݿ�
         Connection conn = DriverManager.getConnection(url1, username1, passwd1);

         if(!conn.isClosed()) 
          System.out.println("Succeeded connecting to the Database!");

         // statement����ִ��SQL���
         Statement statement = conn.createStatement();

         // Ҫִ�е�SQL���
         String sql = "select * from student";

         // �����
         ResultSet rs = statement.executeQuery(sql);

         System.out.println("-----------------");
         System.out.println("ִ�н��������ʾ:");
         System.out.println("-----------------");
         System.out.println(" ѧ��" + "\t" + " ����");
         System.out.println("-----------------");

         String name = null;

         while(rs.next()) {
 
          // ѡ��sname��������
          name = rs.getString("sname");
 
          // ����ʹ��ISO-8859-1�ַ�����name����Ϊ�ֽ����в�������洢�µ��ֽ������С�
          // Ȼ��ʹ��GB2312�ַ�������ָ�����ֽ�����
          name = new String(name.getBytes("ISO-8859-1"),"GB2312");

          // ������
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
