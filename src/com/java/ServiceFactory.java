package com.java;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.java.iservice.IUserService;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Client;  
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;  
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;  
public class ServiceFactory {
	/** 
     * �ͻ��˵��õ�ʵ������  ͨ��wsdl���ɵĽӿڶ��� 
     */  
    private static IUserService service = null;  
    
    /** 
     * �ṩ���ⲿʹ�õĻ�ȡ�����ȡʵ���ķ��� 
     * @return 
     */  
    public synchronized static IUserService getServiceInstance(){  
  
        if(service == null){  
            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();  
            //ע��WebService�ӿ�  
            factory.setServiceClass(IUserService.class);  
            //����WebService��ַ  
            //factory.setAddress("http://10.0.31.210:10005/jm_jobservice");  
            factory.setAddress("http://localhost:8080/CXFWebservice/Users");  
            /*���server����������֤������Ҫ 
                 factory.setUserName(""); 
                 factory.setPasswd(""); 
            */  
            Map<String, Object> properties = new HashMap<String, Object>();  
            properties.put("mtom-enabled", Boolean.TRUE);  
            factory.setProperties(properties);    
            service = (IUserService)factory.create();  
            Client client =ClientProxy.getClient(service);  
            HTTPConduit  http = (HTTPConduit) client.getConduit();  
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();  
            /*���÷������Ϳͻ��˵ĳ�ʱʱ��*/  
            httpClientPolicy.setConnectionTimeout(1000*60*7);  
            httpClientPolicy.setReceiveTimeout(1000*60*7);  
            httpClientPolicy.setAllowChunking(false);  
            http.setClient(httpClientPolicy);  
        }  
        return service;  
    }  
}
