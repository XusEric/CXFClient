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
     * 客户端调用的实例对象  通过wsdl生成的接口对象 
     */  
    private static IUserService service = null;  
    
    /** 
     * 提供给外部使用的获取服务获取实例的方法 
     * @return 
     */  
    public synchronized static IUserService getServiceInstance(){  
  
        if(service == null){  
            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();  
            //注册WebService接口  
            factory.setServiceClass(IUserService.class);  
            //设置WebService地址  
            //factory.setAddress("http://10.0.31.210:10005/jm_jobservice");  
            factory.setAddress("http://localhost:8080/CXFWebservice/Users");  
            /*如果server端设置了验证，还需要 
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
            /*设置服务器和客户端的超时时间*/  
            httpClientPolicy.setConnectionTimeout(1000*60*7);  
            httpClientPolicy.setReceiveTimeout(1000*60*7);  
            httpClientPolicy.setAllowChunking(false);  
            http.setClient(httpClientPolicy);  
        }  
        return service;  
    }  
}
